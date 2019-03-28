package com.qexz.service.impl;

/* 				    
 **********************************************
 *      DATE           PERSON       REASON
 *    2019/3/19          FXY        Created
 **********************************************
 */


import com.qexz.dao.ContestContentMapper;
import com.qexz.dao.QuestionMapper;
import com.qexz.dto.ContestContentDto;
import com.qexz.model.ContestContent;
import com.qexz.model.Question;
import com.qexz.service.ContestContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContestContentServiceImpl implements ContestContentService {

    @Autowired
    ContestContentMapper contestContentMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Override
    public List<ContestContentDto> getContentByContestId(int contestId) {

        List<ContestContentDto> contestContentDtos = new ArrayList<>();
        List<ContestContent> contestContents = contestContentMapper.getContentByContestId(contestId);
        List<Integer> questionIds = new ArrayList<>();
        Map<Integer, ContestContent> map = new HashMap<>();
        for (ContestContent contestContent : contestContents) {
            questionIds.add(contestContent.getQuestionId());
            map.put(contestContent.getQuestionId(), contestContent);
        }
        if (!questionIds.isEmpty()) {

            List<Question> questions = questionMapper.getQuestionByIds(questionIds);
            for (Question question : questions) {
                ContestContentDto contestContentDto = new ContestContentDto();
                contestContentDto.setQuestion(question);
                contestContentDto.setContestContent(map.get(question.getId()));
                contestContentDtos.add(contestContentDto);
            }
        }
        return contestContentDtos;
    }

    @Override
    public boolean deleteContent(int contestId, int questionId) {
        int row = contestContentMapper.deleteContent(contestId, questionId);
        if (row > 0)
            return true;
        else
            return false;
    }

    @Override
    public boolean updateContent(ContestContent contestContent) {
        int row = contestContentMapper.updateContent(contestContent);
        if (row > 0)
            return true;
        else
            return false;
    }

    @Override
    public int getTotalScoreByContestId(int contestId) {
        try {
            return contestContentMapper.getTotalScoreByContestId(contestId);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public boolean insertContestContent(ContestContent contestContent) {
        int i = contestContentMapper.insertContestContent(contestContent);
        if (i > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteAllQuestionById(int questionId) {
        int row = contestContentMapper.deleteAllQuestionById(questionId);
        if (row > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateContestContentState(int contestId, int questionId, int state) {
        int row = contestContentMapper.updateContestContentState(contestId, questionId, state);
        if (row > 0) {
            return true;
        }
        return false;
    }
}
