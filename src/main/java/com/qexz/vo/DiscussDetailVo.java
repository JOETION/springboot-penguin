package com.qexz.vo;

/* 				    
 **********************************************
 *      DATE           PERSON       REASON
 *    2019/4/4          FXY        Created
 **********************************************
 */


import com.qexz.model.Account;
import com.qexz.model.Comment;

import java.util.List;

public class DiscussDetailVo {
    private Comment comment;
    Account user;
    List<ReplyVo> replies;

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Account getUser() {
        return user;
    }

    public void setUser(Account user) {
        this.user = user;
    }

    public List<ReplyVo> getReplies() {
        return replies;
    }

    public void setReplies(List<ReplyVo> replies) {
        this.replies = replies;
    }
}
