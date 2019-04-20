package com.qexz.controller;

import com.qexz.dto.AjaxResultDto;
import com.qexz.exception.QexzWebError;
import com.qexz.model.Comment;
import com.qexz.service.CommentService;
import com.qexz.service.PostService;
import com.qexz.service.ReplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/comment")
public class CommentController {

    private static Logger LOG = LoggerFactory.getLogger(CommentController.class);
    @Autowired
    private CommentService commentService;

    //添加评论
    @RequestMapping(value = "/api/addComment", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultDto addComment(@RequestBody Comment comment) {
        try {
            int commentId = commentService.addComment(comment);
            return new AjaxResultDto().setData(commentId);
        } catch (Exception e) {
            LOG.error("添加评论失败，原因：" + e);
            return AjaxResultDto.fixedError(QexzWebError.COMMON);
        }

    }

    //删除评论
    @DeleteMapping("/api/deleteComment/{id}")
    public AjaxResultDto deleteComment(@PathVariable int id) {
        try {
            commentService.deleteCommentById(id);
            return new AjaxResultDto().setData(true);
        } catch (Exception e) {
            LOG.error("删除评论失败，原因：" + e);
            return new AjaxResultDto().fixedError(QexzWebError.COMMON);
        }

    }
}
