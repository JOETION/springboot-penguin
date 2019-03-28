package com.qexz.service.impl;

import com.github.pagehelper.PageHelper;
import com.qexz.dao.AnswerMapper;
import com.qexz.dao.ContestContentMapper;
import com.qexz.dao.ContestMapper;
import com.qexz.dao.GradeMapper;
import com.qexz.model.Grade;
import com.qexz.service.GradeService;
import com.qexz.service.QuestionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("gradeService")
public class GradeServiceImpl implements GradeService {

    private static Log LOG = LogFactory.getLog(GradeServiceImpl.class);

    @Autowired
    private GradeMapper gradeMapper;

    @Autowired
    QuestionService questionService;


    @Override
    public int addGrade(Grade grade) {
        return gradeMapper.insertGrade(grade);
    }

    @Override
    public boolean updateGrade(Grade grade) {
        return gradeMapper.updateGradeById(grade) > 0;
    }

    @Override
    public boolean deleteGrade(int id) {
        return gradeMapper.deleteGrade(id) > 0;
    }

    @Override
    public Grade getGradeById(int id) {
        return gradeMapper.getGradeById(id);
    }

    @Override
    public Map<String, Object> getGradesByStudentId(int pageNum, int pageSize, int studentId) {
        Map<String, Object> data = new HashMap<>();
        int count = gradeMapper.getCountByStudentId(studentId);
        if (count == 0) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", 1);
            data.put("totalPageSize", 0);
            data.put("grades", new ArrayList<>());
            return data;
        }
        int totalPageNum = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        if (pageNum > totalPageNum) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", totalPageNum);
            data.put("totalPageSize", 0);
            data.put("grades", new ArrayList<>());
            return data;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Grade> grades = gradeMapper.getGradesByStudentId(studentId);
        data.put("pageNum", pageNum);
        data.put("pageSize", pageSize);
        data.put("totalPageNum", totalPageNum);
        data.put("totalPageSize", count);
        data.put("grades", grades);
        return data;
    }

    @Override
    public Map<String, Object> getGradesByContestId(int contestId, int pageNum, int pageSize) {

        Map<String, Object> data = new HashMap<>();
        int count = gradeMapper.getCountByContestId(contestId);
        if (count == 0) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", 1);
            data.put("totalPageSize", 0);
            data.put("grades", new ArrayList<>());
            return data;
        }
        int totalPageNum = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        if (pageNum > totalPageNum) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", totalPageNum);
            data.put("totalPageSize", 0);
            data.put("grades", new ArrayList<>());
            return data;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Grade> grades = gradeMapper.getGradesByContestId(contestId);
        data.put("pageNum", pageNum);
        data.put("pageSize", pageSize);
        data.put("totalPageNum", totalPageNum);
        data.put("totalPageSize", count);
        data.put("grades", grades);
        return data;
    }

}
