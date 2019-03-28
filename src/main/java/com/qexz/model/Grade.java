package com.qexz.model;

import java.util.Date;
import java.util.List;

public class Grade {

    private int studentId;
    private int contestId;
    private int result;
    private int autoResult;
    private int manulResult;
    private String manulReason;
    private Date createTime;
    private Date updateTime;

    public String getManulReason() {
        return manulReason;
    }

    public void setManulReason(String manulReason) {
        this.manulReason = manulReason;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getContestId() {
        return contestId;
    }

    public void setContestId(int contestId) {
        this.contestId = contestId;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getAutoResult() {
        return autoResult;
    }

    public void setAutoResult(int autoResult) {
        this.autoResult = autoResult;
    }

    public int getManulResult() {
        return manulResult;
    }

    public void setManulResult(int manulResult) {
        this.manulResult = manulResult;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
