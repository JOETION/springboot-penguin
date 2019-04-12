package com.qexz.controller;

import com.qexz.common.QexzConst;
import com.qexz.dto.*;
import com.qexz.model.*;
import com.qexz.service.*;
import com.qexz.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/")
public class DefaultController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private ContestService contestService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ReplyService replyService;
    @Autowired
    private GradeService gradeService;
    @Autowired
    private ContestContentService contestContentService;
    @Autowired
    private AnswerService answerService;

    /**
     * 首页
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(HttpServletRequest request, Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        return "/home";
    }

    /**
     * 在线考试列表页
     */
    @RequestMapping(value = "/contest/index", method = RequestMethod.GET)
    public String contestIndex(HttpServletRequest request,
                               @RequestParam(value = "page", defaultValue = "1") int page,
                               Model model) {
        contestService.updateStateToStart();
        contestService.updateStateToEnd();
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        if (currentAccount == null) {
            return "/error/404";
        }
        Map<String, Object> data = contestService.getContests(page, QexzConst.contestPageSize);
        //得到考试信息
        List<Subject> subjects = subjectService.getSubjects();
        Map<Integer, String> subjectId2name = subjects.stream().
                collect(Collectors.toMap(Subject::getId, Subject::getName));
        List<Contest> contests = (List<Contest>) data.remove("contests");
        List<ContestVo> contestVos = new ArrayList<>();
        for (Contest contest : contests) {
            ContestVo contestVo = new ContestVo();
            contestVo.setContest(contest);
            contestVo.setSubjectName(subjectId2name.getOrDefault(contest.getSubjectId(), "未知科目"));
            contestVos.add(contestVo);
        }
        //某堂考试是否完成
        Map<Integer, Boolean> state = new HashMap<>();
        for (Contest contest : contests) {
            Answer answer = answerService.getAnswer(contest.getId(), currentAccount.getId(), -1);
            if (answer != null) {
                state.put(contest.getId(), true);
            } else {
                state.put(contest.getId(), false);
            }
        }

        data.put("contestVos", contestVos);
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        model.addAttribute("state", state);
        model.addAttribute(QexzConst.DATA, data);
        return "/contest/index";
    }

    /**
     * 在线考试页
     */
    @RequestMapping(value = "/contest/{contestId}", method = RequestMethod.GET)
    public String contestDetail(HttpServletRequest request,
                                @PathVariable("contestId") int contestId,
                                Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        Contest contest = contestService.getContestById(contestId);
        if (currentAccount == null || contest.getStartTime().getTime() > System.currentTimeMillis()
                || contest.getEndTime().getTime() < System.currentTimeMillis()) {
            return "redirect:/contest/index";
        }
        List<ContestDetailVo> contestDetailVos = new ArrayList<>();

        List<ContestContent> contestContents = contestContentService.getContentByContestId(contestId);
        Map<Integer, ContestContent> map = contestContents.stream().collect(Collectors.toMap(ContestContent::getQuestionId, a -> a));
        List<Integer> questionIds = contestContents.stream().map(ContestContent::getQuestionId).collect(Collectors.toList());

        if (!questionIds.isEmpty()) {

            List<Question> questions = questionService.getQuestionByIds(questionIds);
            for (Question question : questions) {

                //答案置为空
                question.setAnswer("");
                ContestDetailVo contestDetailVo = new ContestDetailVo();
                contestDetailVo.setQuestion(question);
                contestDetailVo.setContestContent(map.get(question.getId()));
                contestDetailVos.add(contestDetailVo);
            }
        }
        Map<String, Object> data = new HashMap<>();
        data.put("contest", contest);
        data.put("contestDetailVos", contestDetailVos);
        model.addAttribute(QexzConst.DATA, data);
        return "/contest/detail";
    }


    /**
     * 得到考试排名
     *
     * @param request
     * @param contestId
     * @param model
     * @return
     */
    @RequestMapping(value = "/contest/{contestId}/rank")
    public String contestRank(HttpServletRequest request, @PathVariable int contestId, Model model) {
        List<ContestRankVo> contestRankVos = new ArrayList<>();
        List<Grade> grades = gradeService.getGradesByContestId(contestId);
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);

        Set<Integer> studentIds = grades.stream().map(Grade::getStudentId).collect(Collectors.toSet());
        List<Account> accounts = accountService.getAccountsByIds(studentIds);
        Map<Integer, Account> id2Account = accounts.stream().collect(Collectors.toMap(Account::getId, account -> account));

        for (Grade grade : grades) {
            ContestRankVo contestRankVo = new ContestRankVo();
            contestRankVo.setAccount(id2Account.get(grade.getStudentId()));
            contestRankVo.setGrade(grade);
            contestRankVos.add(contestRankVo);
        }
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        model.addAttribute(QexzConst.DATA, contestRankVos);
        return "/contest/contest-rank";
    }

    /**
     * 题库中心页
     */
    @RequestMapping(value = "/problemset/list", method = RequestMethod.GET)
    public String problemSet(HttpServletRequest request, @RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        Map<String, Object> data = subjectService.getSubjects(page, QexzConst.subjectPageSize);

        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        model.addAttribute(QexzConst.DATA, data);
        return "/problem/problemset";
    }

    /**
     * 题目列表页
     */
    @RequestMapping(value = "/problemset/{problemsetId}/problems", method = RequestMethod.GET)
    public String problemList(HttpServletRequest request,
                              @PathVariable("problemsetId") Integer problemsetId,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              @RequestParam(value = "content", defaultValue = "") String content,
                              @RequestParam(value = "type", defaultValue = "-1") int type,
                              Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        Map<String, Object> data = questionService.getQuestionsByProblemsetIdAndContentAndType(page, QexzConst.questionPageSize,
                problemsetId, content, type);
        Subject subject = subjectService.getSubjectById(problemsetId);
        data.put("subject", subject);
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        model.addAttribute(QexzConst.DATA, data);
        return "/problem/problemlist";
    }

    /**
     * 题目详情页
     */
    @RequestMapping(value = "/problemset/{problemsetId}/problem/{problemId}", method = RequestMethod.GET)
    public String problemDetail(HttpServletRequest request,
                                @PathVariable("problemsetId") Integer problemsetId,
                                @PathVariable("problemId") Integer problemId,
                                Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        Map<String, Object> data = new HashMap<>();
        Question question = questionService.getQuestionById(problemId);
        Subject subject = subjectService.getSubjectById(problemsetId);
        data.put("question", question);
        data.put("subject", subject);
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        model.addAttribute(QexzConst.DATA, data);
        return "/problem/problemdetail";
    }

    /**
     * 讨论区首页
     */
    @RequestMapping(value = "/discuss", method = RequestMethod.GET)
    public String discuss(HttpServletRequest request, @RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);

        Map<String, Object> data = postService.getPosts(page, QexzConst.postPageSize, 0);
        List<Post> posts = (List<Post>) data.remove("posts");
        Set<Integer> authorIds = posts.stream().map(Post::getAuthorId).collect(Collectors.toCollection(HashSet::new));
        List<Account> authors = accountService.getAccountsByIds(authorIds);
        Map<Integer, Account> id2author = authors.stream().
                collect(Collectors.toMap(Account::getId, account -> account));
        List<DiscussVo> discussVos = new ArrayList<>();
        for (Post post : posts) {
            DiscussVo discussVo = new DiscussVo();
            discussVo.setAuthor(id2author.get(post.getAuthorId()));
            discussVo.setPost(post);
            discussVos.add(discussVo);
        }

        data.put("discussVos", discussVos);
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        model.addAttribute(QexzConst.DATA, data);

        return "/discuss/discuss";
    }

    /**
     * 帖子详情页
     */
    @RequestMapping(value = "/discuss/{postId}", method = RequestMethod.GET)
    public String discussDetail(HttpServletRequest request, @PathVariable("postId") Integer postId, Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);

        Map<String, Object> data = new HashMap<>();
        Post post = postService.getPostById(postId);
        Account author = accountService.getAccountById(post.getAuthorId());
        DiscussVo discussVo = new DiscussVo();
        discussVo.setPost(post);
        discussVo.setAuthor(author);

        List<Comment> comments = commentService.getCommentsByPostId(postId);
        List<Reply> replies = replyService.getReliesByPostId(postId);
        List<DiscussDetailVo> discussDetailVos = new ArrayList<>();

        Set<Integer> userIds = new HashSet<>();
        for (Comment comment : comments) {
            DiscussDetailVo discussDetailVo = new DiscussDetailVo();
            discussDetailVo.setComment(comment);
            discussDetailVo.setReplies(new ArrayList<>());
            userIds.add(comment.getUserId());
            discussDetailVos.add(discussDetailVo);
        }
        for (Reply reply : replies) {
            userIds.add(reply.getUserId());
            userIds.add(reply.getAtuserId());
        }

        //设置评论的用户信息
        List<Account> users = accountService.getAccountsByIds(userIds);
        Map<Integer, Account> id2user = users.stream().
                collect(Collectors.toMap(Account::getId, account -> account));
        for (DiscussDetailVo discussDetailVo : discussDetailVos) {
            discussDetailVo.setUser(id2user.get(discussDetailVo.getComment().getUserId()));
        }

        //设置评论回复的信息
        List<ReplyVo> replyVos = new ArrayList<>();
        for (Reply reply : replies) {
            ReplyVo replyVo = new ReplyVo();
            replyVo.setReply(reply);
            replyVo.setUser(id2user.get(reply.getUserId()));
            if (reply.getAtuserId() != 0) {
                replyVo.setAtuser(id2user.get(reply.getAtuserId()));
            }
            replyVos.add(replyVo);
        }

        //添加回复信息到评论信息中
        Map<Integer, DiscussDetailVo> id2comment = discussDetailVos.stream().
                collect(Collectors.toMap(a -> a.getComment().getId(), a -> a));

        for (ReplyVo replyVo : replyVos) {
            DiscussDetailVo discussDetailVo = id2comment.get(replyVo.getReply().getCommentId());
            if (discussDetailVo != null)
                discussDetailVo.getReplies().add(replyVo);
        }
        data.put("discussVo", discussVo);
        data.put("discussDetailVos", discussDetailVos);
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        model.addAttribute(QexzConst.DATA, data);
        return "/discuss/discussDetail";
    }

    /**
     * 发布帖子页
     */
    @RequestMapping(value = "/discuss/post", method = RequestMethod.GET)
    public String postDiscuss(HttpServletRequest request, Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        Map<String, Object> data = new HashMap<>();
        data.put("authorId", currentAccount.getId());
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        model.addAttribute(QexzConst.DATA, data);
        return "/discuss/postDiscuss";
    }

    /**
     * 编辑帖子
     */
    @RequestMapping(value = "/discuss/editPost/{postId}")
    public String editDiscuss(HttpServletRequest request, Model model, @PathVariable int postId) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        Post post = postService.getPostById(postId);
        Map<String, Object> data = new HashMap<>();
        data.put("post", post);
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        model.addAttribute(QexzConst.DATA, data);
        return "/discuss/editDiscuss";
    }

    /**
     * 获取服务器端时间,防止用户篡改客户端时间提前参与考试
     *
     * @return 时间的json数据
     */
    @RequestMapping(value = "/time/now", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResultDto time() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return new AjaxResultDto().setData(localDateTime);
    }

    /**
     * 测试分布式一致性session
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/uid", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResultDto uid(HttpSession session) {
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid == null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return new AjaxResultDto().setData(session.getId());
    }
}
