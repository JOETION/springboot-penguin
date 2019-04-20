package com.qexz.service.impl;

import com.github.pagehelper.PageHelper;
import com.qexz.dao.CommentMapper;
import com.qexz.dao.PostMapper;
import com.qexz.dao.ReplyMapper;
import com.qexz.model.Comment;
import com.qexz.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service("commentService")
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    PostMapper postMapper;

    @Autowired
    ReplyMapper replyMapper;

    @Override
    @Transactional(rollbackFor = {Exception.class} )
    public int addComment(Comment comment) {
        postMapper.updateReplyNumById(comment.getPostId(), new Date());
        return commentMapper.insertComment(comment);
    }

    @Override
    public List<Comment> getCommentsByPostId(int postId) {
        return commentMapper.getCommentsByPostId(postId);
    }

    @Override
    public Map<String, Object> getComments(int pageNum, int pageSize) {
        Map<String, Object> data = new HashMap<>();
        int count = commentMapper.getCount();
        if (count == 0) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", 1);
            data.put("totalPageSize", 0);
            data.put("comments", new ArrayList<>());
            return data;
        }
        int totalPageNum = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        if (pageNum > totalPageNum) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", totalPageNum);
            data.put("totalPageSize", 0);
            data.put("comments", new ArrayList<>());
            return data;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> comments = commentMapper.getComments();
        data.put("pageNum", pageNum);
        data.put("pageSize", pageSize);
        data.put("totalPageNum", totalPageNum);
        data.put("totalPageSize", count);
        data.put("comments", comments);
        return data;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class} )
    public boolean deleteCommentById(int id) {
        replyMapper.deleteRepliesByCommentId(id);
        commentMapper.deleteCommentById(id);
        return true;
    }

    @Override
    public boolean deleteCommentsByPostId(int postId) {

        return commentMapper.deleteCommentsByPostId(postId) > 0;
    }

    @Override
    public List<Comment> getCommentsByIds(Set<Integer> ids) {
        return commentMapper.getCommentsByIds(ids);
    }

    @Override
    public Comment getCommentById(int id) {
        return commentMapper.getCommentById(id);
    }
}
