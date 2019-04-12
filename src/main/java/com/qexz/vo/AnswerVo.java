package com.qexz.vo;

/* 				    
 **********************************************
 *      DATE           PERSON       REASON
 *    2019/3/25          FXY        Created
 **********************************************
 */


import com.qexz.model.Answer;

import java.util.List;

public class AnswerVo {
    private Answer answer;
    private List<AnswerContent> answerContents;

    public static class AnswerContent {
        private int questionType;
        private int questionId;
        private String answer;

        public int getQuestionType() {
            return questionType;
        }

        public void setQuestionType(int questionType) {
            this.questionType = questionType;
        }

        public int getQuestionId() {
            return questionId;
        }

        public void setQuestionId(int questionId) {
            this.questionId = questionId;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public List<AnswerContent> getAnswerContents() {
        return answerContents;
    }

    public void setAnswerContents(List<AnswerContent> answerContents) {
        this.answerContents = answerContents;
    }
}
