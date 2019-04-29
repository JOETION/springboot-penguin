package com.qexz.controller;

import com.qexz.dto.AjaxResultDto;
import com.qexz.exception.QexzWebError;
import com.qexz.model.Question;
import com.qexz.service.ContestContentService;
import com.qexz.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/question")
public class QuestionController {

    private static Logger LOG = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private QuestionService questionService;

    //添加题目
    @RequestMapping(value = "/api/addQuestion", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultDto addQuestion(@RequestBody Question question) {
        try {
            int row = questionService.addQuestion(question);
            return new AjaxResultDto().setData(row);
        } catch (Exception e) {
            LOG.error("添加试题信息失败，原因：" + e);
            return AjaxResultDto.fixedError(QexzWebError.COMMON);
        }
    }

    //更新题目信息
    @RequestMapping(value = "/api/updateQuestion", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultDto updateQuestion(@RequestBody Question question) {
        try {
            questionService.updateQuestion(question);
            return new AjaxResultDto().setData(true);
        } catch (Exception e) {
            LOG.error("更新试题信息失败，原因：" + e);
            return AjaxResultDto.fixedError(QexzWebError.COMMON);
        }
    }

    //删除题目信息
    @DeleteMapping("/api/deleteQuestion/{id}")
    public AjaxResultDto deleteContest(@PathVariable int id) {
        try {
            questionService.deleteQuestion(id);
            return new AjaxResultDto().setData(true);
        } catch (Exception e) {
            LOG.error("删除考试中所有试题信息失败，原因：" + e);
            return AjaxResultDto.fixedError(QexzWebError.COMMON);
        }
    }

    //添加试题到考试中
    @RequestMapping(value = "/api/addQuestionToContest", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResultDto addQuestionToContest(@RequestParam("contestId") int contestId, @RequestParam("id") int id) {
        try {
            int row = questionService.addQuestionToContest(contestId, id);
            if (row > 0) {
                return new AjaxResultDto().setData(row);
            }
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException)
                return new AjaxResultDto().setMessage("考试中已存在该问题，请勿重复提交");
            else
                return new AjaxResultDto().setMessage(e.getMessage());
        }
        return new AjaxResultDto().setState(QexzWebError.COMMON);
    }
}
