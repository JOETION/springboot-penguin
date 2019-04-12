package com.qexz.service.impl;

import com.github.pagehelper.PageHelper;
import com.qexz.dao.ContestMapper;
import com.qexz.dao.SubjectMapper;
import com.qexz.model.Contest;
import com.qexz.service.ContestService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("contestService")
public class ContestServiceImpl implements ContestService {

    private static Log LOG = LogFactory.getLog(ContestServiceImpl.class);

    @Autowired
    private ContestMapper contestMapper;
    @Autowired
    private SubjectMapper subjectMapper;

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

    @Override
    public boolean deleteContest(int id) {
        return contestMapper.deleteContest(id) > 0;
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
    public boolean updateContestScore(int contestId, int score) {
        int i = contestMapper.updateContestScore(contestId, score);
        if (i > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateContestStateById(int id) {
        int row = contestMapper.updateContestStateById(id);
        if (row > 0) {
            return true;
        }
        return false;
    }

}
