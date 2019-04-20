package com.qexz.service.impl;

/* 				    
 **********************************************
 *      DATE           PERSON       REASON
 *    2019/3/23          FXY        Created
 **********************************************
 */


import com.github.pagehelper.PageHelper;
import com.qexz.dao.AnswerMapper;
import com.qexz.model.Answer;
import com.qexz.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("answerService")
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    AnswerMapper answerMapper;

    @Override
    public boolean addAnswer(Answer answer) {
        int row = answerMapper.insertAnswer(answer);
        if (row > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateAnswerState(int studentId, int contestId, int state) {
        int row = answerMapper.updateAnswerState(studentId, contestId, state);
        if (row > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Answer getAnswer(int contestId, int studentId, int state) {
        return answerMapper.queryAnswer(contestId, studentId, state);
    }

    @Override
    public List<Answer> getAnswerByContestId(int contestId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return answerMapper.getAllAnswerByContestId(contestId);
    }
}
