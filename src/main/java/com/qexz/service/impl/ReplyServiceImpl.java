package com.qexz.service.impl;

import com.qexz.dao.ReplyMapper;
import com.qexz.model.Reply;
import com.qexz.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service("replyService")
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    private ReplyMapper replyMapper;

    @Override
    public int addReply(Reply reply) {
        return replyMapper.insertReply(reply);
    }

    @Override
    public List<Reply> getReliesByPostId(int postId) {
        return replyMapper.getReliesByPostId(postId);
    }

    @Override
    public boolean deleteRepliesByPostId(int postId) {
        int row = replyMapper.deleteRepliesByPostId(postId);
        if (row > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteRepliesByCommentId(int commentId) {
        int row = replyMapper.deleteRepliesByCommentId(commentId);
        if (row > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteReplyById(int id) {
        int row = replyMapper.deleteReplyById(id);
        if (row > 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<Reply> getRepliesByIds(Set<Integer> ids) {
        return replyMapper.getRepliesByIds(ids);
    }

    @Override
    public Reply getReplyById(int id) {
        return replyMapper.getReplyById(id);
    }
}
