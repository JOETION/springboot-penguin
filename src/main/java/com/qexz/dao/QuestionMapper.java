package com.qexz.dao;

import com.qexz.model.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface QuestionMapper {

    int insertQuestion(@Param("question") Question question);

    int insertQuestionByList(@Param("list") List<Question> questions);

    int deleteQuestion(@Param("id") int id);

    int updateQuestionById(@Param("question") Question question);

    Question getQuestionById(@Param("id") int id);

    int getCountByContent(@Param("content") String content);

    List<Question> getQuestionsByContent(@Param("content") String content);

    List<Question> getQuestionByContestId(@Param("contestId") int contestId);

    int getCountByProblemsetIdAndContentAndDiffculty(@Param("problemsetId") int problemsetId,
                                                     @Param("content") String content,
                                                     @Param("difficulty") int diffculty);

    List<Question> getQuestionsByProblemsetIdAndContentAndDiffculty(@Param("problemsetId") int problemsetId,
                                                                    @Param("content") String content,
                                                                    @Param("difficulty") int diffculty);

    int updateQuestionsStateByContestId(@Param("contestId") int contestId,
                                        @Param("state") int state);

    //添加考试内容
    //TODO 此处添加考试内容方法是否欠妥
    int addQuestionToContest(@Param("contestId") int contestId, @Param("id") int id);

    //根据编号得到信息
    List<Question> getQuestionByIds(@Param("questionIds")List<Integer> questionIds);
}
