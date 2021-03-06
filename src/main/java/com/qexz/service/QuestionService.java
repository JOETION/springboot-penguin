package com.qexz.service;

import com.qexz.model.Question;

import java.util.List;
import java.util.Map;

public interface QuestionService {

    int addQuestion(Question question);

    int addQuestions(List<Question> questions);

    boolean updateQuestion(Question question);

    List<Question> getQuestionsByContestId(int contestId);

    Map<String, Object> getQuestionsByContent(int pageNum, int pageSize, String content);

    Map<String, Object> getQuestionsByProblemsetIdAndContentAndType(int pageNum, int pageSize,
                                                                         int problemsetId,
                                                                         String content,int type);

    boolean deleteQuestion(int id);

    Question getQuestionById(int id);

    boolean updateQuestionsStateByContestId(int contestId, int state);

    int addQuestionToContest(int contestId, int id);

    List<Question> getQuestionByIds(List<Integer> questionIds);
}
