package com.qexz.controller;

import com.qexz.common.QexzConst;
import com.qexz.dto.AjaxResultDto;
import com.qexz.exception.QexzWebError;
import com.qexz.model.Subject;
import com.qexz.service.SubjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/subject")
public class SubjectController {

    private static Logger LOG = LoggerFactory.getLogger(SubjectController.class);

    @Autowired
    private SubjectService subjectService;

    //添加课程
    @RequestMapping(value = "/api/addSubject", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultDto addSubject(@RequestBody Subject subject) {
        try {
            subject.setImgUrl(QexzConst.DEFAULT_SUBJECT_IMG_URL);
            subject.setQuestionNum(0);
            int subjectId = subjectService.addSubject(subject);
            return new AjaxResultDto().setData(subjectId);
        } catch (Exception e) {
            LOG.error("添加课程信息失败，原因：" + e);
            return AjaxResultDto.fixedError(QexzWebError.COMMON);
        }
    }

    //更新课程
    @RequestMapping(value = "/api/updateSubject", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultDto updateSubject(@RequestBody Subject subject) {
        try {
            boolean result = subjectService.updateSubject(subject);
            return new AjaxResultDto().setData(result);
        } catch (Exception e) {
            LOG.error("更新课程信息失败，原因：" + e);
            return AjaxResultDto.fixedError(QexzWebError.COMMON);
        }
    }

    //删除课程
    @DeleteMapping("/api/deleteSubject/{id}")
    public AjaxResultDto deleteSubject(@PathVariable int id) {
        try {
            boolean result = subjectService.deleteSubjectById(id);
            return new AjaxResultDto().setData(result);
        } catch (Exception e) {
            LOG.error("删除课程信息失败，原因：" + e);
            return AjaxResultDto.fixedError(QexzWebError.COMMON);
        }
    }

}
