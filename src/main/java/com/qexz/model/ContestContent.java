package com.qexz.model;

/* 				    
 **********************************************
 *      DATE           PERSON       REASON
 *    2019/3/19          FXY        Created
 **********************************************
 */


import java.util.Date;

public class ContestContent {
    private int contestId;
    private int questionId;
    private int score;
    private int diffculty;
    private int state;
    private Date createTime;
    private Date updateTime;

    public int getContestId() {
        return contestId;
    }

    public void setContestId(int contestId) {
        this.contestId = contestId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getDiffculty() {
        return diffculty;
    }

    public void setDiffculty(int diffculty) {
        this.diffculty = diffculty;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
