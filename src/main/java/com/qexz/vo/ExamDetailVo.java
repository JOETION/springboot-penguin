package com.qexz.vo;

/* 				    
 **********************************************
 *      DATE           PERSON       REASON
 *    2019/3/30          FXY        Created
 **********************************************
 */


public class ExamDetailVo {
    //问题分数
    private int questionScore;

    //问题类型
    private int questionType;

    //问题内容
    private String questionContent;

    //问题答案
    private String questionAnswer;

    //问题解析
    private String questionParse;

    //你的答案
    private String answer;

    //老师点评
    private String mark;

    //选项
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String contestName;

    public String getContestName() {
        return contestName;
    }

    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public int getQuestionScore() {
        return questionScore;
    }

    public void setQuestionScore(int questionScore) {
        this.questionScore = questionScore;
    }

    public int getQuestionType() {
        return questionType;
    }

    public void setQuestionType(int questionType) {
        this.questionType = questionType;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(String questionAnswer) {
        this.questionAnswer = questionAnswer;
    }

    public String getQuestionParse() {
        return questionParse;
    }

    public void setQuestionParse(String questionParse) {
        this.questionParse = questionParse;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
