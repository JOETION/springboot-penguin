package com.qexz.service;

import com.qexz.model.Answer;
import com.qexz.model.Grade;

import java.util.List;
import java.util.Map;

public interface GradeService {

    int addGrade(Answer answer,Grade grade);

    boolean updateGrade(Grade grade);

    boolean deleteGrade(int id);

    Grade getGradeById(int id);

    Map<String, Object> getGradesByStudentId(int pageNum, int pageSize, int studentId);

    Map<String, Object> getGradesByContestId(int contestId, int pageNum, int pageSize);

    int getRankByContestIdAndStudentId(int contestId, int studentId);

    Grade getGradeByContestIdAndStudentId(int contestId,int studentId);

    List<Grade> getGradesByContestId(int contestId);
}
