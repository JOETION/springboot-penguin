package com.qexz.dao;

/* 				    
 **********************************************
 *      DATE           PERSON       REASON
 *    2019/3/19          FXY        Created
 **********************************************
 */


import com.qexz.model.ContestContent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 考试内容
 */
@Component
@Mapper
public interface ContestContentMapper {

    //根据Id得到考试内容
    List<ContestContent> getContentByContestId(@Param("contestId") int contestId);

    //根据考试编号和问题编号删除考试内容
    int deleteContent(@Param("contestId") int contestId, @Param("questionId") int questionId);

    //根据考试编号和问题编号更新考试内容
    int updateContent(@Param("contestContent") ContestContent contestContent);

    //更新考试的分数
    int getTotalScoreByContestId(@Param("contestId") int contestId);

    //插入考试内容
    int insertContestContent(@Param("contestContent") ContestContent contestContent);

    //根据问题编号删除所有问题
    int deleteAllQuestionById(@Param("questionId")int questionId);

    //更新问题状态
    int updateContestContentState(@Param("contestId")int contestId,@Param("questionId")int questionId,@Param("state")int state);
}
