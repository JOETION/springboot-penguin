package com.qexz.dto;

/* 				    
 **********************************************
 *      DATE           PERSON       REASON
 *    2019/3/20          FXY        Created
 **********************************************
 */


import com.qexz.model.ContestContent;
import com.qexz.model.Question;

import java.io.Serializable;

public class ContestContentDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Question question;

    private ContestContent contestContent;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public ContestContent getContestContent() {
        return contestContent;
    }

    public void setContestContent(ContestContent contestContent) {
        this.contestContent = contestContent;
    }
}
