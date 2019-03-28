package com.qexz.dto;

/* 				    
 **********************************************
 *      DATE           PERSON       REASON
 *    2019/3/23          FXY        Created
 **********************************************
 */


import com.qexz.model.Account;
import com.qexz.model.Answer;
import com.qexz.model.Grade;

import java.io.Serializable;

public class ContestResultDto implements Serializable {
    private static final long serialVersionUID = 1L;

    //解答的答案
    private AnswerDto answerDto;

    //考试的成绩
    private Grade grade;

    //用户信息
    private Account account;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public AnswerDto getAnswerDto() {
        return answerDto;
    }

    public void setAnswerDto(AnswerDto answerDto) {
        this.answerDto = answerDto;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
}
