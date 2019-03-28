package com.qexz.controller;

import com.qexz.dto.AjaxResult;
import com.qexz.exception.QexzWebError;
import com.qexz.model.Question;
import com.qexz.service.ContestContentService;
import com.qexz.service.QuestionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/question")
public class QuestionController {

    private static Log LOG = LogFactory.getLog(QuestionController.class);

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ContestContentService contestContentService;

    //添加题目
    @RequestMapping(value = "/api/addQuestion", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult addQuestion(@RequestBody Question question) {
        int row = questionService.addQuestion(question);
        return new AjaxResult().setData(row);
    }

    //更新题目信息
    @RequestMapping(value = "/api/updateQuestion", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult updateQuestion(@RequestBody Question question) {
        boolean result = questionService.updateQuestion(question);
        return new AjaxResult().setData(result);
    }

    //删除题目信息
    @DeleteMapping("/api/deleteQuestion/{id}")
    public AjaxResult deleteContest(@PathVariable int id) {
        boolean result = questionService.deleteQuestion(id);
        boolean b = contestContentService.deleteAllQuestionById(id);
        if(b&&result){
            return new AjaxResult().setData(true);
        }
        return new AjaxResult().setMessage("删除失败");
    }

    //添加试题到考试中
    @RequestMapping(value = "/api/addQuestionToContest", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult addQuestionToContest(@RequestParam("contestId") int contestId, @RequestParam("id") int id) {
        try {
            int row = questionService.addQuestionToContest(contestId, id);
            if (row > 0) {
                return new AjaxResult().setData(row);
            }
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException)
                return new AjaxResult().setMessage("考试中已存在该问题，请勿重复提交");
            else
                return new AjaxResult().setMessage(e.getMessage());
        }
        return new AjaxResult().setState(QexzWebError.COMMON);

    }
}
