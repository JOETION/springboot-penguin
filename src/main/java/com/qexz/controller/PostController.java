package com.qexz.controller;

import com.qexz.dto.AjaxResultDto;
import com.qexz.exception.QexzWebError;
import com.qexz.service.CommentService;
import com.qexz.service.ReplyService;
import com.qexz.vo.DiscussVo;
import com.qexz.model.Account;
import com.qexz.model.Post;
import com.qexz.service.AccountService;
import com.qexz.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/post")
public class PostController {

    private static Logger LOG = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private PostService postService;
    @Autowired
    AccountService accountService;
    @Autowired
    CommentService commentService;
    @Autowired
    ReplyService replyService;

    //添加帖子
    @RequestMapping(value = "/api/addPost", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultDto addPost(@RequestBody Post post) {
        try {
            int postId = postService.addPost(post);
            return new AjaxResultDto().setData(postId);
        } catch (Exception e) {
            LOG.error("添加帖子信息失败，原因：" + e);
            return AjaxResultDto.fixedError(QexzWebError.COMMON);
        }
    }

    //更新帖子
    @RequestMapping(value = "/api/updatePost", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultDto updatePost(@RequestBody Post post) {
        try {
            postService.updatePostById(post);
            return new AjaxResultDto().setData(true);
        } catch (Exception e) {
            LOG.error("更新帖子信息失败，原因：" + e);
            return AjaxResultDto.fixedError(QexzWebError.COMMON);
        }
    }

    //删除帖子
    @DeleteMapping("/api/deletePost/{id}")
    @Transactional
    public AjaxResultDto deletePost(@PathVariable int id) {
        try {
            postService.deletePostById(id);
            commentService.deleteCommentsByPostId(id);
            replyService.deleteRepliesByPostId(id);
            return new AjaxResultDto().setData(true);
        } catch (Exception e) {
            LOG.error("删除帖子信息失败，原因：" + e);
            return AjaxResultDto.fixedError(QexzWebError.COMMON);
        }
    }

    //得到帖子信息
    @RequestMapping("/api/getPosts")
    @ResponseBody
    public AjaxResultDto getPosts(HttpServletRequest request, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "1") int pageSize) {

        try {
            //todo 此处可以考虑能否在前端区分类型
            String level = request.getParameter("level");
            String type = request.getParameter("type");
            if (level != null) {
                Map<String, Object> map = postService.getPosts(pageNum, pageSize, Integer.parseInt(level), Integer.parseInt(type));
                List<Post> posts = (List<Post>) map.remove("posts");
                Set<Integer> authorIds = posts.stream().map(Post::getAuthorId).collect(Collectors.toSet());
                List<Account> accounts = accountService.getAccountsByIds(authorIds);
                Map<Integer, Account> id2Account = accounts.stream().collect(Collectors.toMap(Account::getId, account -> account));
                List<DiscussVo> discussVos = new ArrayList<>();
                for (Post post : posts) {
                    DiscussVo discussVo = new DiscussVo();
                    discussVo.setPost(post);
                    discussVo.setAuthor(id2Account.get(post.getAuthorId()));
                    discussVos.add(discussVo);
                }
                map.put("discussVos", discussVos);
                return new AjaxResultDto().setData(map);
            } else {
                return new AjaxResultDto().setMessage("参数传递错误，请传入正确的类型！");
            }

        } catch (Exception e) {
            LOG.error("获取帖子信息失败，原因：" + e);
            return AjaxResultDto.fixedError(QexzWebError.COMMON);
        }
    }
}
