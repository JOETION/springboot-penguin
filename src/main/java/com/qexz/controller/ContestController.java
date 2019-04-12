package com.qexz.controller;

import com.qexz.dto.AjaxResultDto;
import com.qexz.vo.ContestDetailVo;
import com.qexz.exception.QexzWebError;
import com.qexz.model.Contest;
import com.qexz.model.ContestContent;
import com.qexz.service.ContestContentService;
import com.qexz.service.ContestService;
import com.qexz.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/contest")
public class ContestController {

    @Autowired
    private ContestService contestService;
    @Autowired
    private QuestionService questionService;

    @Autowired
    private ContestContentService contestContentService;

    //添加考试
    @RequestMapping(value = "/api/addContest", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultDto addContest(@RequestBody Contest contest) {
        int contestId = contestService.addContest(contest);
        return new AjaxResultDto().setData(contestId);
    }

    //更新考试信息
    @RequestMapping(value = "/api/updateContest", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultDto updateContest(@RequestBody Contest contest) {
        boolean result = contestService.updateContest(contest);
        return new AjaxResultDto().setData(result);
    }

    //删除考试信息
    @DeleteMapping("/api/deleteContest/{id}")
    public AjaxResultDto deleteContest(@PathVariable int id) {
        boolean result = contestService.deleteContest(id);
        return new AjaxResultDto().setData(result);
    }

    //完成考试批改，更新考试状态
    @RequestMapping(value = "/api/finishContest/{id}", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultDto finishContest(@PathVariable int id) {
        //此处是否需要更新考试内容的状态，后期需要考虑
        boolean result = contestService.updateContestStateById(id);
        return new AjaxResultDto().setData(result);
    }



    //更新考试内容
    @RequestMapping(value = "/api/updateContestContent", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultDto updateContestContent(@RequestBody ContestDetailVo contestDetailVo) {
        try {
            ContestContent contestContent = contestDetailVo.getContestContent();
            boolean a = questionService.updateQuestion(contestDetailVo.getQuestion());
            boolean b = contestContentService.updateContent(contestContent);
            //更新分数
            int totalScore = contestContentService.getTotalScoreByContestId(contestContent.getContestId());
            boolean b1 = contestService.updateContestScore(contestContent.getContestId(), totalScore);
            return new AjaxResultDto().setData(a && b && b1);
        } catch (Exception e) {
            return new AjaxResultDto().setState(QexzWebError.COMMON);
        }

    }

    //删除考试内容
    @RequestMapping(value = "/api/deleteContestContent", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResultDto deleteContestContent(@RequestParam("contestId") int contestId, @RequestParam("questionId") int questionId) {
        boolean b = contestContentService.deleteContent(contestId, questionId);
        return new AjaxResultDto().setData(b);
    }

    //新增试题
    @RequestMapping(value = "/api/addContestContent", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultDto addContestContent(@RequestBody ContestDetailVo contestDetailVo) {
        questionService.addQuestion(contestDetailVo.getQuestion());
        ContestContent contestContent = contestDetailVo.getContestContent();
        contestContent.setQuestionId(contestDetailVo.getQuestion().getId());
        contestContentService.insertContestContent(contestContent);
        //更新分数
        int totalScore = contestContentService.getTotalScoreByContestId(contestContent.getContestId());
        boolean b = contestService.updateContestScore(contestContent.getContestId(), totalScore);
        return new AjaxResultDto().setData(b);
    }

}
