package com.qexz.dao;

import com.qexz.model.Grade;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface GradeMapper {

    int insertGrade(@Param("grade") Grade grade);

    int deleteGrade(@Param("id") int id);

    int updateGradeById(@Param("grade") Grade grade);

    Grade getGradeById(@Param("id") int id);

    int getCountByStudentId(@Param("studentId") int studentId);

    List<Grade> getGradesByStudentId(@Param("studentId") int studentId);

    List<Grade> getGradesByContestId(@Param("contestId") int contestId);

    int getCountByContestId(@Param("contestId") int contestId);

    //获取某个学生某堂考试的排名
    int getRankByContestIdAndStudentId(@Param("contestId")int contestId,@Param("studentId")int studentId);

    Grade getGradeByStudentIdAndContestId(@Param("contestId")int contestId,@Param("studentId")int studentId);

    //删除某堂考试的所有分数
    int deleteGradeByContestId(@Param("contestId")int contestId);
}
