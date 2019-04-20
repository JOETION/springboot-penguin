package com.qexz.service;

import com.qexz.model.Reply;

import java.util.List;
import java.util.Set;

public interface ReplyService {

    int addReply(Reply reply);

    List<Reply> getReliesByPostId(int postId);

    boolean deleteRepliesByPostId(int postId);

    boolean deleteRepliesByCommentId(int commentId);

    boolean deleteReplyById(int id);

    List<Reply> getRepliesByIds(Set<Integer> ids);

    Reply getReplyById(int id);
}
