package com.qexz.service;

/* 				    
 **********************************************
 *      DATE           PERSON       REASON
 *    2019/3/23          FXY        Created
 **********************************************
 */


import com.qexz.model.Answer;

import java.util.List;

/**
 * 提交的答案服务类
 */
public interface AnswerService {

    //添加答案
    public boolean addAnswer(Answer answer);

    //更新答案
    public boolean updateAnswerState(int studentId, int contestId, int state);

    //得到答案
    public Answer getAnswer(int contestId, int studentId, int state);

    //得到某堂考试的答案
    public List<Answer> getAnswerByContestId(int contestId, int pageNum, int pageSize);

}
