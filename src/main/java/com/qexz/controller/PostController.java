package com.qexz.controller;

import com.qexz.dto.AjaxResultDto;
import com.qexz.service.CommentService;
import com.qexz.service.ReplyService;
import com.qexz.vo.DiscussVo;
import com.qexz.model.Account;
import com.qexz.model.Post;
import com.qexz.service.AccountService;
import com.qexz.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
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
        int postId = postService.addPost(post);
        return new AjaxResultDto().setData(postId);
    }

    //更新帖子
    @RequestMapping(value = "/api/updatePost", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultDto updatePost(@RequestBody Post post) {
        boolean result = postService.updatePostById(post);
        return new AjaxResultDto().setData(result);
    }

    //删除帖子
    @DeleteMapping("/api/deletePost/{id}")
    public AjaxResultDto deletePost(@PathVariable int id) {
        try {
            boolean postResult = postService.deletePostById(id);
            boolean commentResult = commentService.deleteCommentsByPostId(id);
            boolean replyResult = replyService.deleteRepliesByPostId(id);
            if (postResult && commentResult && replyResult) {
                return new AjaxResultDto().setData(true);
            } else {
                return new AjaxResultDto().setMessage("删除失败！");
            }

        } catch (Exception e) {
            return new AjaxResultDto().setMessage("删除失败，原因：" + e.getMessage());
        }
    }

    //得到帖子信息
    @RequestMapping("/api/getPosts")
    @ResponseBody
    public AjaxResultDto getPosts(HttpServletRequest request, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "1") int pageSize) {

        //todo 此处可以考虑能否在前端区分类型
        String type = request.getParameter("type");
        if (type != null) {
            Map<String, Object> map = postService.getPosts(pageNum, pageSize, Integer.parseInt(type));
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
    }
}
