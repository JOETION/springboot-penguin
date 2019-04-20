package com.qexz.service;

import com.qexz.model.Contest;
import com.qexz.vo.ContestDetailVo;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ContestService {

    int addContest(Contest contest);

    boolean updateContest(Contest contest);

    Contest getContestById(int id);

    Map<String, Object> getContests(int pageNum, int pageSize);

    boolean deleteContest(int id);

    boolean updateStateToStart();

    boolean updateStateToEnd();

    List<Contest> getContestsByContestIds(Set<Integer> contestIds);

    //得到所有考试内容
    List<Contest> getAllContests();

    //更新考试内容
    boolean updateContestContent(ContestDetailVo contestDetailVo);

    //更新考试状态
    boolean updateContestStateById(int id);

    //添加考试内容
    boolean addContestContent(ContestDetailVo contestDetailVo);

    boolean updateContestScore(int contestId);
}
