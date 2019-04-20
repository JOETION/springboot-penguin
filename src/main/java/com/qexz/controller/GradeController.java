package com.qexz.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qexz.common.QexzConst;
import com.qexz.dto.AjaxResultDto;
import com.qexz.exception.QexzWebError;
import com.qexz.vo.AnswerVo;
import com.qexz.vo.ContestRankVo;
import com.qexz.model.*;
import com.qexz.service.AnswerService;
import com.qexz.service.ContestContentService;
import com.qexz.service.GradeService;
import com.qexz.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/grade")
public class GradeController {

    private static Logger LOG = LoggerFactory.getLogger(GradeController.class);

    @Autowired
    private GradeService gradeService;

    @Autowired
    private ContestContentService contestContentService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private QuestionService questionService;

    //提交试卷，自动生成选择题分数
    /*保存答案json*/
    @RequestMapping(value = "/api/submitContest", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultDto submitContest(HttpServletRequest request, @RequestBody Answer answer) {
        try {

            Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);

            int autoResult = 0;
            //取得所有考试内容信息
            List<ContestContent> contestContents = contestContentService.getContentByContestId(answer.getContestId());
            Map<Integer, ContestContent> id2ContestContent = contestContents.stream().collect(Collectors.toMap(ContestContent::getQuestionId, a -> a));

            //获取问题
            List<Integer> questionIds = contestContents.stream().map(ContestContent::getQuestionId).collect(Collectors.toList());
            List<Question> questions = questionService.getQuestionByIds(questionIds);

            //解析答案
            List<AnswerVo.AnswerContent> contents = new Gson().fromJson(answer.getAnswerJson(), new TypeToken<List<AnswerVo.AnswerContent>>() {
            }.getType());
            Map<Integer, AnswerVo.AnswerContent> content = contents.stream().collect(Collectors.toMap(AnswerVo.AnswerContent::getQuestionId, answer1 -> answer1));

            for (Question question : questions) {
                if (question.getQuestionType() < 1) {
                    if (question.getAnswer().equals(content.get(question.getId()).getAnswer())) {
                        autoResult += id2ContestContent.get(question.getId()).getScore();
                    }
                }
            }
            answer.setStudentId(currentAccount.getId());
            Answer answer1 = answerService.getAnswer(answer.getContestId(), currentAccount.getId(), 0);
            if (answer1 != null) {
                return new AjaxResultDto().setMessage("试卷以提交，请勿重复提交");
            } else {
                Grade grade = new Grade();
                grade.setAutoResult(autoResult);
                grade.setContestId(answer.getContestId());
                grade.setManulReason(null);
                grade.setManulResult(0);
                grade.setResult(autoResult);
                grade.setStudentId(currentAccount.getId());
                int row = gradeService.addGrade(answer,grade);//要保证事务。。。
                if (row > 0) {
                    return new AjaxResultDto().setData("提交成功！");
                } else {
                    return new AjaxResultDto().setData("提交失败！");
                }
            }
        } catch (Exception e) {
            LOG.error("提交试卷失败，原因：" + e);
            return AjaxResultDto.fixedError(QexzWebError.COMMON);
        }
    }

    //完成批改试卷
    @RequestMapping(value = "/api/finishGrade", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultDto finishGrade(@RequestBody ContestRankVo contestRankVo) {
        try {
            //更新考试成绩
            Grade grade = new Grade();
            int contestId = contestRankVo.getGrade().getContestId();
            int studentId = contestRankVo.getGrade().getStudentId();
            grade.setContestId(contestId);
            grade.setStudentId(studentId);
            grade.setManulResult(contestRankVo.getGrade().getManulResult());
            grade.setManulReason(contestRankVo.getGrade().getManulReason());
            grade.setResult(contestRankVo.getGrade().getResult());
            gradeService.updateGrade(grade);
            return new AjaxResultDto().setData(true);

        } catch (Exception e) {
            LOG.error("完成批改单个考试信息发生错误，原因：" + e);
            return AjaxResultDto.fixedError(QexzWebError.COMMON);
        }
    }


}
