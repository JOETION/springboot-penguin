package com.qexz.service;

/* 				    
 **********************************************
 *      DATE           PERSON       REASON
 *    2019/3/19          FXY        Created
 **********************************************
 */


import com.qexz.dto.ContestContentDto;
import com.qexz.model.ContestContent;
import com.qexz.model.Question;

import java.util.List;

public interface ContestContentService {

    //根据考试编号得到考试内容
    List<ContestContentDto> getContentByContestId(int contestId);

    //根据考试编号和问题编号删除考试内容
    boolean deleteContent(int contestId, int questionId);

    //根据考试编号和问题编号更新考试内容
    boolean updateContent(ContestContent contestContent);

    //根据考试编号得到总分数
    int getTotalScoreByContestId(int contestId);

    //插入考试内容
    boolean insertContestContent(ContestContent contestContent);

    //根据问题编号删除所有的试题
    boolean deleteAllQuestionById(int questionId);

    //更新考试内容状态
    boolean updateContestContentState(int contestId, int questionId, int state);
}
