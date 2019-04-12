package com.qexz.controller;

import com.qexz.dto.AjaxResultDto;
import com.qexz.model.Comment;
import com.qexz.service.CommentService;
import com.qexz.service.PostService;
import com.qexz.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private PostService postService;
    @Autowired
    private ReplyService replyService;

    //添加评论
    @RequestMapping(value = "/api/addComment", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultDto addComment(@RequestBody Comment comment) {
        postService.updateReplyNumById(comment.getPostId());
        int commentId = commentService.addComment(comment);
        return new AjaxResultDto().setData(commentId);
    }

    //删除评论
    @DeleteMapping("/api/deleteComment/{id}")
    public AjaxResultDto deleteComment(@PathVariable int id) {
        boolean result = commentService.deleteCommentById(id);
        boolean b = replyService.deleteRepliesByCommentId(id);
        return new AjaxResultDto().setData(result && b);
    }
}
