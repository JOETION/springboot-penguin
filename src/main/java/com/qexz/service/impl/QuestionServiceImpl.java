package com.qexz.service.impl;

import com.github.pagehelper.PageHelper;
import com.qexz.dao.ContestContentMapper;
import com.qexz.dao.ContestMapper;
import com.qexz.dao.QuestionMapper;
import com.qexz.model.ContestContent;
import com.qexz.model.Question;
import com.qexz.service.QuestionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuestionServiceImpl implements QuestionService {

    private static Log LOG = LogFactory.getLog(QuestionServiceImpl.class);

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private ContestContentMapper contestContentMapper;

    @Override
    public int addQuestion(Question question) {
        return questionMapper.insertQuestion(question);
    }

    @Override
    public int addQuestions(List<Question> questions) {
        return questionMapper.insertQuestionByList(questions);
    }

    @Override
    public boolean updateQuestion(Question question) {
        return questionMapper.updateQuestionById(question) > 0;
    }

    @Override
    public List<Question> getQuestionsByContestId(int contestId) {
        List<ContestContent> contestContents = contestContentMapper.getContentByContestId(contestId);
        List<Integer> list = new ArrayList<>();
        for (ContestContent contestContent : contestContents) {
            list.add(contestContent.getQuestionId());
        }
        return questionMapper.getQuestionByIds(list);
    }

    @Override
    public List<Question> getQuestionByIds(List<Integer> questionIds) {
        return questionMapper.getQuestionByIds(questionIds);
    }

    @Override
    public Map<String, Object> getQuestionsByContent(int pageNum, int pageSize, String content) {
        Map<String, Object> data = new HashMap<>();
        int count = questionMapper.getCountByContent(content);
        if (count == 0) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", 1);
            data.put("totalPageSize", 0);
            data.put("questionsSize", 0);
            data.put("questions", new ArrayList<>());
            return data;
        }
        int totalPageNum = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        if (pageNum > totalPageNum) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", totalPageNum);
            data.put("totalPageSize", 0);
            data.put("questionsSize", 0);
            data.put("questions", new ArrayList<>());
            return data;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Question> questions = questionMapper.getQuestionsByContent(content);
        data.put("pageNum", pageNum);
        data.put("pageSize", pageSize);
        data.put("totalPageNum", totalPageNum);
        data.put("totalPageSize", count);
        data.put("questionsSize", questions.size());
        data.put("questions", questions);
        return data;
    }

    @Override
    public boolean deleteQuestion(int id) {
        return questionMapper.deleteQuestion(id) > 0;
    }

    @Override
    public Question getQuestionById(int id) {
        return questionMapper.getQuestionById(id);
    }

    @Override
    public Map<String, Object> getQuestionsByProblemsetIdAndContentAndType(int pageNum, int pageSize, int problemsetId, String content,int type) {
        Map<String, Object> data = new HashMap<>();
        int count = questionMapper.getCountByProblemsetIdAndContentAndType(problemsetId,
                content,type);
        if (count == 0) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", 1);
            data.put("totalPageSize", 0);
            data.put("questionsSize", 0);
            data.put("problemsetId", problemsetId);
            data.put("questions", new ArrayList<>());
            return data;
        }
        int totalPageNum = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        if (pageNum > totalPageNum) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", totalPageNum);
            data.put("totalPageSize", 0);
            data.put("questionsSize", 0);
            data.put("problemsetId", problemsetId);
            data.put("questions", new ArrayList<>());
            return data;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Question> questions = questionMapper.getQuestionsByProblemsetIdAndContentAndType(
                problemsetId, content,type);
        data.put("pageNum", pageNum);
        data.put("pageSize", pageSize);
        data.put("totalPageNum", totalPageNum);
        data.put("totalPageSize", count);
        data.put("questionsSize", questions.size());
        data.put("problemsetId", problemsetId);
        data.put("questions", questions);
        return data;
    }

    @Override
    public boolean updateQuestionsStateByContestId(int contestId, int state) {
        return questionMapper.updateQuestionsStateByContestId(contestId, state) > 0;
    }

    //添加问题到考试中
    @Override
    public int addQuestionToContest(int contestId, int id) {
        return questionMapper.addQuestionToContest(contestId, id);
    }
}
