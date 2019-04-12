package com.qexz.vo;

/* 				    
 **********************************************
 *      DATE           PERSON       REASON
 *    2019/4/4          FXY        Created
 **********************************************
 */


import com.qexz.model.Account;
import com.qexz.model.Post;

import java.io.Serializable;

public class DiscussVo {
    private Post post;
    private Account author;

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Account getAuthor() {
        return author;
    }

    public void setAuthor(Account author) {
        this.author = author;
    }
}
