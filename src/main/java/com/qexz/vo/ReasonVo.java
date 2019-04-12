package com.qexz.vo;

/* 				    
 **********************************************
 *      DATE           PERSON       REASON
 *    2019/3/30          FXY        Created
 **********************************************
 */


public class ReasonVo {
    private int questionId;
    private int questionType;
    private String manulReason;

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getQuestionType() {
        return questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }

    public String getManulReason() {
        return manulReason;
    }

    public void setManulReason(String manulReason) {
        this.manulReason = manulReason;
    }
}
