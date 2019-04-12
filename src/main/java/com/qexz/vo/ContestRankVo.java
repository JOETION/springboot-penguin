package com.qexz.vo;

/* 				    
 **********************************************
 *      DATE           PERSON       REASON
 *    2019/3/23          FXY        Created
 **********************************************
 */


import com.qexz.model.Account;
import com.qexz.model.Grade;

public class ContestRankVo {

    //解答的答案
    private AnswerVo answerVo;

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

    public AnswerVo getAnswerVo() {
        return answerVo;
    }

    public void setAnswerVo(AnswerVo answerVo) {
        this.answerVo = answerVo;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
}
