package com.qexz.controller;

import com.qexz.dto.AjaxResult;
import com.qexz.dto.ContestContentDto;
import com.qexz.exception.QexzWebError;
import com.qexz.model.Contest;
import com.qexz.model.ContestContent;
import com.qexz.model.Question;
import com.qexz.service.ContestContentService;
import com.qexz.service.ContestService;
import com.qexz.service.QuestionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/contest")
public class ContestController {

    private static Log LOG = LogFactory.getLog(ContestController.class);

    @Autowired
    private ContestService contestService;
    @Autowired
    private QuestionService questionService;

    @Autowired
    private ContestContentService contestContentService;

    //添加考试
    @RequestMapping(value = "/api/addContest", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult addContest(@RequestBody Contest contest) {
        int contestId = contestService.addContest(contest);
        return new AjaxResult().setData(contestId);
    }

    //更新考试信息
    @RequestMapping(value = "/api/updateContest", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateContest(@RequestBody Contest contest) {
        boolean result = contestService.updateContest(contest);
        return new AjaxResult().setData(result);
    }

    //删除考试信息
    @DeleteMapping("/api/deleteContest/{id}")
    public AjaxResult deleteContest(@PathVariable int id) {
        boolean result = contestService.deleteContest(id);
        return new AjaxResult().setData(result);
    }

    //完成考试批改，更新考试状态
    @RequestMapping(value = "/api/finishContest/{id}", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult finishContest(@PathVariable int id) {
        //此处是否需要更新考试内容的状态，后期需要考虑
        boolean result = contestService.updateContestStateById(id);
        return new AjaxResult().setData(result);
    }



    //更新考试内容
    @RequestMapping(value = "/api/updateContestContent", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateContestContent(@RequestBody ContestContentDto contestContentDto) {
        try {
            ContestContent contestContent = contestContentDto.getContestContent();
            boolean a = questionService.updateQuestion(contestContentDto.getQuestion());
            boolean b = contestContentService.updateContent(contestContent);
            //更新分数
            int totalScore = contestContentService.getTotalScoreByContestId(contestContent.getContestId());
            boolean b1 = contestService.updateContestScore(contestContent.getContestId(), totalScore);
            return new AjaxResult().setData(a && b && b1);
        } catch (Exception e) {
            return new AjaxResult().setState(QexzWebError.COMMON);
        }

    }

    //删除考试内容
    @RequestMapping(value = "/api/deleteContestContent", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult deleteContestContent(@RequestParam("contestId") int contestId, @RequestParam("questionId") int questionId) {
        boolean b = contestContentService.deleteContent(contestId, questionId);
        return new AjaxResult().setData(b);
    }

    //新增试题
    @RequestMapping(value = "/api/addContestContent", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult addContestContent(@RequestBody ContestContentDto contestContentDto) {
        questionService.addQuestion(contestContentDto.getQuestion());
        ContestContent contestContent = contestContentDto.getContestContent();
        contestContent.setQuestionId(contestContentDto.getQuestion().getId());
        contestContentService.insertContestContent(contestContent);
        //更新分数
        int totalScore = contestContentService.getTotalScoreByContestId(contestContent.getContestId());
        boolean b = contestService.updateContestScore(contestContent.getContestId(), totalScore);
        return new AjaxResult().setData(b);
    }

}
