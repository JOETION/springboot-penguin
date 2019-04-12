package com.qexz.vo;

/* 				    
 **********************************************
 *      DATE           PERSON       REASON
 *    2019/4/4          FXY        Created
 **********************************************
 */


import com.qexz.model.Account;
import com.qexz.model.Reply;

public class ReplyVo {
    private Reply reply;
    private Account user;
    private Account atuser;

    public Reply getReply() {
        return reply;
    }

    public void setReply(Reply reply) {
        this.reply = reply;
    }

    public Account getUser() {
        return user;
    }

    public void setUser(Account user) {
        this.user = user;
    }

    public Account getAtuser() {
        return atuser;
    }

    public void setAtuser(Account atuser) {
        this.atuser = atuser;
    }
}
