package com.qexz.vo;

/* 				    
 **********************************************
 *      DATE           PERSON       REASON
 *    2019/3/29          FXY        Created
 **********************************************
 */


import com.qexz.model.Contest;
import com.qexz.model.Grade;

public class ExamVo {
    private Contest contest;
    private String subjectName;
    private Grade grade;
    private int rank;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public Contest getContest() {
        return contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
}
