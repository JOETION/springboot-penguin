package com.qexz.controller;

import com.qexz.common.QexzConst;
import com.qexz.dto.AjaxResult;
import com.qexz.dto.AnswerDto;
import com.qexz.dto.ContestResultDto;
import com.qexz.dto.ContestContentDto;
import com.qexz.model.Account;
import com.qexz.model.Answer;
import com.qexz.model.ContestContent;
import com.qexz.model.Grade;
import com.qexz.service.AnswerService;
import com.qexz.service.ContestContentService;
import com.qexz.service.GradeService;
import com.qexz.service.QuestionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/grade")
public class GradeController {

    private static Log LOG = LogFactory.getLog(GradeController.class);

    @Autowired
    private GradeService gradeService;

    @Autowired
    private ContestContentService contestContentService;

    @Autowired
    private AnswerService answerService;

    //提交试卷，自动生成选择题分数
    /*保存答案json*/
    @RequestMapping(value = "/api/submitContest", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult submitContest(HttpServletRequest request, @RequestBody AnswerDto answerDto) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);


        int autoResult = 0;
        //取得所有考试内容信息，包括问题内容，每道题的分数信息等
        List<ContestContentDto> contestContentDtos = contestContentService.getContentByContestId(answerDto.getAnswer().getContestId());
        Map<Integer, AnswerDto.AnswerContent> content = answerDto.getAnswerContents().stream().collect(Collectors.toMap(AnswerDto.AnswerContent::getQuestionId, answer1 -> answer1));

        for (ContestContentDto contestContentDto : contestContentDtos) {
            if (contestContentDto.getQuestion().getQuestionType() < 1) {
                if (contestContentDto.getQuestion().getAnswer().equals(content.get(contestContentDto.getQuestion().getId()))) {
                    autoResult += contestContentDto.getContestContent().getScore();
                }
            }
        }
        answerDto.getAnswer().setStudentId(currentAccount.getId());
        Answer answer1 = answerService.getAnswer(answerDto.getAnswer().getContestId(), currentAccount.getId(), 0);
        if (answer1 != null) {
            return new AjaxResult().setMessage("试卷以提交，请勿重复提交");
        } else {
            answerService.addAnswer(answerDto.getAnswer());
            Grade grade = new Grade();
            grade.setAutoResult(autoResult);
            grade.setContestId(answerDto.getAnswer().getContestId());
            grade.setManulReason(null);
            grade.setManulResult(0);
            grade.setResult(autoResult);
            grade.setStudentId(currentAccount.getId());
            int row = gradeService.addGrade(grade);
            if (row > 0) {
                return new AjaxResult().setData("提交成功！");
            } else {
                return new AjaxResult().setData("提交失败！");
            }
        }
    }

    //完成批改试卷
    @RequestMapping(value = "/api/finishGrade", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult finishGrade(@RequestBody ContestResultDto contestResultDto) {
        //更新考试成绩
        Grade grade = new Grade();
        int contestId = contestResultDto.getGrade().getContestId();
        int studentId = contestResultDto.getGrade().getStudentId();
        grade.setContestId(contestId);
        grade.setStudentId(studentId);
        grade.setManulResult(contestResultDto.getGrade().getManulResult());
        grade.setManulReason(contestResultDto.getGrade().getManulReason());
        grade.setResult(contestResultDto.getGrade().getResult());
        boolean result = gradeService.updateGrade(grade);
        //更新考试答案
        boolean b = answerService.updateAnswerState(studentId, contestId, 1);
        return new AjaxResult().setData(result && b);
    }


}
