package com.qexz.controller;

import com.qexz.dto.AjaxResultDto;
import com.qexz.vo.ContestDetailVo;
import com.qexz.exception.QexzWebError;
import com.qexz.model.Contest;
import com.qexz.model.ContestContent;
import com.qexz.service.ContestContentService;
import com.qexz.service.ContestService;
import com.qexz.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/contest")
public class ContestController {

    private static Logger LOG = LoggerFactory.getLogger(ContestController.class);

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
        try {
            int contestId = contestService.addContest(contest);
            return new AjaxResultDto().setData(contestId);
        } catch (Exception e) {
            LOG.error("添加考试信息失败，原因：" + e);
            return AjaxResultDto.fixedError(QexzWebError.COMMON);
        }

    }

    //更新考试信息
    @RequestMapping(value = "/api/updateContest", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultDto updateContest(@RequestBody Contest contest) {
        try {
            boolean result = contestService.updateContest(contest);
            return new AjaxResultDto().setData(result);
        } catch (Exception e) {
            LOG.error("更新考试信息，原因：" + e);
            return AjaxResultDto.fixedError(QexzWebError.COMMON);
        }
    }

    //删除考试信息
    @DeleteMapping("/api/deleteContest/{id}")
    public AjaxResultDto deleteContest(@PathVariable int id) {
        try {
            boolean result = contestService.deleteContest(id);
            return new AjaxResultDto().setData(result);
        } catch (Exception e) {
            LOG.error("删除考试信息失败，信息：" + e);
            return AjaxResultDto.fixedError(QexzWebError.COMMON);
        }
    }

    //完成考试批改，更新考试状态
    @RequestMapping(value = "/api/finishContest/{id}", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultDto finishContest(@PathVariable int id) {
        try {
            //此处是否需要更新考试内容的状态，后期需要考虑
            boolean result = contestService.updateContestStateById(id);
            return new AjaxResultDto().setData(result);
        } catch (Exception e) {
            LOG.error("完成批改所有考试信息失败，原因：" + e);
            return AjaxResultDto.fixedError(QexzWebError.COMMON);

        }
    }


    //更新考试内容
    @RequestMapping(value = "/api/updateContestContent", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultDto updateContestContent(@RequestBody ContestDetailVo contestDetailVo) {
        try {
            contestService.updateContestContent(contestDetailVo);
            return new AjaxResultDto().setData(true);
        } catch (Exception e) {
            return new AjaxResultDto().setState(QexzWebError.COMMON);
        }

    }

    //删除考试内容
    @RequestMapping(value = "/api/deleteContestContent", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResultDto deleteContestContent(@RequestParam("contestId") int contestId, @RequestParam("questionId") int questionId) {
        try {
            boolean b = contestContentService.deleteContent(contestId, questionId);
            return new AjaxResultDto().setData(b);
        } catch (Exception e) {
            LOG.error("删除考试内容失败，原因：" + e);
            return AjaxResultDto.fixedError(QexzWebError.COMMON);
        }
    }

    //新增试题
    @RequestMapping(value = "/api/addContestContent", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultDto addContestContent(@RequestBody ContestDetailVo contestDetailVo) {
        try {
            contestService.addContestContent(contestDetailVo);
            return new AjaxResultDto().setData(true);
        } catch (Exception e) {
            LOG.error("为考试新增试题信息失败，原因：" + e);
            return AjaxResultDto.fixedError(QexzWebError.COMMON);
        }
    }

}
