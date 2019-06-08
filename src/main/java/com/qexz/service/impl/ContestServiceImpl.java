package com.qexz.service.impl;

import com.github.pagehelper.PageHelper;
import com.qexz.dao.*;
import com.qexz.model.Contest;
import com.qexz.model.ContestContent;
import com.qexz.model.Question;
import com.qexz.service.ContestService;
import com.qexz.vo.ContestDetailVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("contestService")
public class ContestServiceImpl implements ContestService {

    @Autowired
    private ContestMapper contestMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private ContestContentMapper contestContentMapper;

    @Autowired
    private AnswerMapper answerMapper;

    @Autowired
    private GradeMapper gradeMapper;

    @Override
    public int addContest(Contest contest) {
        contest.setTotalScore(0);
        contest.setState(0);
        return contestMapper.insertContest(contest);
    }

    @Override
    public boolean updateContest(Contest contest) {
        return contestMapper.updateContestById(contest) > 0;
    }

    @Override
    public Contest getContestById(int id) {
        return contestMapper.getContestById(id);
    }

    @Override
    public Map<String, Object> getContests(int pageNum, int pageSize) {
        Map<String, Object> data = new HashMap<>();
        int count = contestMapper.getCount();
        if (count == 0) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", 1);
            data.put("totalPageSize", 0);
            data.put("contests", new ArrayList<>());
            return data;
        }
        int totalPageNum = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        if (pageNum > totalPageNum) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", totalPageNum);
            data.put("totalPageSize", 0);
            data.put("contests", new ArrayList<>());
            return data;
        }

        PageHelper.startPage(pageNum, pageSize);
        List<Contest> contests = contestMapper.getContests();
        data.put("pageNum", pageNum);
        data.put("pageSize", pageSize);
        data.put("totalPageNum", totalPageNum);
        data.put("totalPageSize", count);
        data.put("contests", contests);
        return data;
    }

    //删除考试会删除所有考试答案，考试内容，考试分数等
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public boolean deleteContest(int id) {
        contestMapper.deleteContest(id);
        contestContentMapper.deleteContentByContestId(id);
        answerMapper.deleteAnswerByContestId(id);
        gradeMapper.deleteGradeByContestId(id);
        return true;
    }

    @Override
    public boolean updateStateToStart() {
        return contestMapper.updateStateToStart(new Date()) > 0;
    }

    @Override
    public boolean updateStateToEnd() {
        return contestMapper.updateStateToEnd(new Date()) > 0;
    }

    @Override
    public List<Contest> getContestsByContestIds(Set<Integer> contestIds) {
        return contestMapper.getContestsByContestIds(contestIds);
    }

    //得到所有考试内容
    @Override
    public List<Contest> getAllContests() {
        List<Contest> contests = contestMapper.getAllContests();
        if (contests != null) {
            return contests;
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean updateContestContent(ContestDetailVo contestDetailVo) {
        ContestContent contestContent = contestDetailVo.getContestContent();
        questionMapper.updateQuestionById(contestDetailVo.getQuestion());
        contestContentMapper.updateContent(contestContent);
        //更新分数
        this.updateContestScore(contestContent.getContestId());
        return true;
    }

    @Override
    public boolean updateContestStateById(int id) {
        int row = contestMapper.updateContestStateById(id);
        if (row > 0) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean addContestContent(ContestDetailVo contestDetailVo) {
        questionMapper.insertQuestion(contestDetailVo.getQuestion());
        ContestContent contestContent = contestDetailVo.getContestContent();
        contestContent.setQuestionId(contestDetailVo.getQuestion().getId());
        contestContentMapper.insertContestContent(contestContent);

        //更新分数
        this.updateContestScore(contestContent.getContestId());
        return false;
    }

    @Override
    public boolean updateContestScore(int contestId) {
        //更新分数
        int totalScore = contestContentMapper.getTotalScoreByContestId(contestId);
        int row = contestMapper.updateContestScore(contestId, totalScore);
        if (row > 0) {
            return true;
        }
        return false;
    }

}
