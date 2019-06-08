package com.qexz.dao;

/* 				    
 **********************************************
 *      DATE           PERSON       REASON
 *    2019/3/23          FXY        Created
 **********************************************
 */


import com.qexz.model.Answer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 答案映射类
 */
@Component
@Mapper
public interface AnswerMapper {

    //插入答案
    public int insertAnswer(@Param("answer") Answer answer);

    //更新答案
    public int updateAnswerState(@Param("studentId") int studentId, @Param("contestId") int contestId, @Param("state") int state);

    //查询是否已经添加了某条答案
    public Answer queryAnswer(@Param("contestId") int contestId, @Param("studentId") int studentId, @Param("state") int state);

    //得到某堂考试的学生答案
    public List<Answer> getAllAnswerByContestId(@Param("contestId") int contestId);

    //删除某堂考试的所有答案
    public int deleteAnswerByContestId(@Param("contestId") int contestId);
}
