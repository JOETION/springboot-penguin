package com.qexz.dao;

import com.qexz.model.Contest;
import com.qexz.model.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
@Mapper
public interface ContestMapper {

    int insertContest(@Param("contest") Contest contest);

    int updateContestById(@Param("contest") Contest contest);

    Contest getContestById(@Param("id") int id);

    int getCount();

    List<Contest> getContests();

    int deleteContest(@Param("id") int id);

    int updateStateToStart(@Param("currentTime") Date currentTime);

    int updateStateToEnd(@Param("currentTime") Date currentTime);

    List<Contest> getContestsByContestIds(@Param("contestIds") Set<Integer> contestIds);

    //得到所有考试
    List<Contest> getAllContests();

    //更新考试分数
    int updateContestScore(@Param("id")int contestId,@Param("score")int score);

    //更新考试状态
    int updateContestStateById(@Param("id") int id);
}
