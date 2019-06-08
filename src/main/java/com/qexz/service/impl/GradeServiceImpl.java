package com.qexz.service.impl;

import com.github.pagehelper.PageHelper;
import com.qexz.dao.*;
import com.qexz.model.Answer;
import com.qexz.model.Grade;
import com.qexz.service.GradeService;
import com.qexz.service.QuestionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("gradeService")
public class GradeServiceImpl implements GradeService {

    @Autowired
    private GradeMapper gradeMapper;

    @Autowired
    AnswerMapper answerMapper;


    @Override
    @Transactional(rollbackFor = {Exception.class})
    public int addGrade(Answer answer,Grade grade) {
        answerMapper.insertAnswer(answer);
        return gradeMapper.insertGrade(grade);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean updateGrade(Grade grade) {
        answerMapper.updateAnswerState(grade.getStudentId(), grade.getContestId(), 1);
        gradeMapper.updateGradeById(grade);
        return  true;
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

    @Override
    public int getRankByContestIdAndStudentId(int contestId, int studentId) {
        return gradeMapper.getRankByContestIdAndStudentId(contestId, studentId);
    }

    @Override
    public Grade getGradeByContestIdAndStudentId(int contestId, int studentId) {
        return gradeMapper.getGradeByStudentIdAndContestId(contestId, studentId);
    }

    @Override
    public List<Grade> getGradesByContestId(int contestId) {
        return gradeMapper.getGradesByContestId(contestId);
    }

    @Override
    public boolean deleteGradeByContestId(int contestId) {
        return gradeMapper.deleteGradeByContestId(contestId)>0;
    }

}
