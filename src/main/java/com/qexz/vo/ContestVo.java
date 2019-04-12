package com.qexz.vo;

/* 				    
 **********************************************
 *      DATE           PERSON       REASON
 *    2019/3/31          FXY        Created
 **********************************************
 */


import com.qexz.model.Contest;

import java.io.Serializable;

public class ContestVo {
    private Contest contest;
    private String subjectName;

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
}
