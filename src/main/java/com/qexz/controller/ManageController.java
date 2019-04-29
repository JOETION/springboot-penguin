package com.qexz.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qexz.common.QexzConst;
import com.qexz.dto.AjaxResultDto;
import com.qexz.exception.QexzWebError;
import com.qexz.exception.QexzWebException;
import com.qexz.model.*;
import com.qexz.service.*;
import com.qexz.util.CommonUtils;
import com.qexz.util.POIUtil;
import com.qexz.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/manage")
public class ManageController {

    private static Logger LOG = LoggerFactory.getLogger(ManageController.class);

    @Autowired
    private AccountService accountService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private ContestService contestService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private GradeService gradeService;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private ContestContentService contestContentService;
    @Autowired
    private ComplaintService complaintService;
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private ReplyService replyService;

    @Value("${upload.file.excel.path}")
    private String uploadExcelPath;

    /**
     * 管理员登录页
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request, Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);

        if (currentAccount == null) {
            return "/manage/manage-login";
        } else {
            return "redirect:/manage/contest/list";
        }
    }

    /**
     * 用户管理
     */
    @RequestMapping(value = "/account/list", method = RequestMethod.GET)
    public String accountList(HttpServletRequest request,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              Model model) {
        try {
            Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
            model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
            if (currentAccount == null || currentAccount.getLevel() < 1) {
                return "/error/404";
            } else {
                Map<String, Object> data = accountService.getAccounts(page, QexzConst.accountPageSize);
                model.addAttribute(QexzConst.DATA, data);
                return "/manage/manage-accountList";
            }
        } catch (Exception e) {
            LOG.error("后台用户列表管理页发生错误，原因：" + e);
            return "/error/404";
        }
    }

    /**
     * 考试管理
     */
    @RequestMapping(value = "/contest/list", method = RequestMethod.GET)
    public String contestList(HttpServletRequest request,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              Model model) {
        try {
            Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
            model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
            if (currentAccount == null || currentAccount.getLevel() < 1) {
                return "/error/404";
            } else {
                Map<String, Object> data = contestService.getContests(page, QexzConst.contestPageSize);
                List<Subject> subjects = subjectService.getSubjects();
                Map<Integer, String> subjectId2Name = subjects.stream().collect(Collectors.toMap(Subject::getId, Subject::getName));
                data.put("subjects", subjects);

                model.addAttribute(QexzConst.DATA, data);
                model.addAttribute("subjectId2Name", subjectId2Name);
                return "/manage/manage-contestBoard";
            }

        } catch (Exception e) {
            LOG.error("后台考试管理页发生错误，原因：" + e);
            return "/error/404";
        }
    }

    /**
     * 考试管理-查看试题
     */
    @RequestMapping(value = "/contest/{contestId}/problems", method = RequestMethod.GET)
    public String contestProblemList(HttpServletRequest request,
                                     @PathVariable("contestId") Integer contestId, Model model) {

        try {

            Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
            model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
            if (currentAccount == null || currentAccount.getLevel() < 1) {
                return "/error/404";
            } else {
                Map<String, Object> data = new HashMap<>();
                List<ContestDetailVo> contestDetailVos = new ArrayList<>();
                List<ContestContent> contestContents = contestContentService.getContentByContestId(contestId);

                if (contestContents != null && contestContents.size() != 0) {
                    Map<Integer, ContestContent> id2ContestContent = contestContents.stream().collect(Collectors.toMap(ContestContent::getQuestionId, a -> a));
                    //获取问题
                    List<Integer> questionIds = contestContents.stream().map(ContestContent::getQuestionId).collect(Collectors.toList());
                    List<Question> questions = questionService.getQuestionByIds(questionIds);
                    for (Question question : questions) {
                        ContestDetailVo contestDetailVo = new ContestDetailVo();
                        contestDetailVo.setContestContent(id2ContestContent.get(question.getId()));
                        contestDetailVo.setQuestion(question);
                        contestDetailVos.add(contestDetailVo);
                    }
                }

                Contest contest = contestService.getContestById(contestId);
                //更新分数，以免删除题库的题后分数显示不正确
                contestService.updateContestScore(contestId);
                data.put("contest", contest);
                data.put("contestDetailVos", contestDetailVos);
                model.addAttribute(QexzConst.DATA, data);
                return "/manage/manage-editContestProblem";
            }
        } catch (Exception e) {
            LOG.error("编辑考试题目管理页发生错误，原因：" + e);
            return "/error/404";
        }

    }

    /**
     * 題目管理
     */
    @RequestMapping(value = "/question/list", method = RequestMethod.GET)
    public String questionList(HttpServletRequest request,
                               @RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "content", defaultValue = "") String content,
                               Model model) {
        try {
            Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
            model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
            if (currentAccount == null || currentAccount.getLevel() < 1) {
                return "/error/404";
            } else {
                Map<String, Object> data = questionService.getQuestionsByContent(page,
                        QexzConst.questionPageSize, content);
                List<Question> questions = (List<Question>) data.get("questions");
                List<Subject> subjects = subjectService.getSubjects();
                List<Contest> contests = contestService.getAllContests();
                Map<Integer, String> subjectId2name = subjects.stream().
                        collect(Collectors.toMap(Subject::getId, Subject::getName));
                for (Question question : questions) {
                    question.setSubjectName(subjectId2name.
                            getOrDefault(question.getSubjectId(), "未知科目"));
                }
                data.put("subjects", subjects);
                data.put("content", content);
                data.put("contests", contests);
                model.addAttribute("data", data);
                return "/manage/manage-questionBoard";
            }

        } catch (Exception e) {
            LOG.error("后台试题管理页发生错误，原因：" + e);
            return "/error/404";

        }
    }



    //TODO 此处需要添加返回值，看能不能提取到service中
    /**
     * 上传试题excel文件，并处理后放进数据库
     * @param file
     * @throws QexzWebException
     */
    @RequestMapping(value = "/question/upload", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultDto fileUpload(@RequestParam("file") MultipartFile file) throws QexzWebException {

        // 判断文件是否为空
        String fileName = file.getOriginalFilename().toLowerCase();
        if (!file.isEmpty() && fileName.endsWith(QexzConst.EXCEL_XLS_SUFFIX) || fileName.endsWith(QexzConst.EXCEL_XLSX_SUFFIX)) {
            try {
                File upload = new File(uploadExcelPath + CommonUtils.getCurrentTime() + "_" + fileName);
                if (upload.exists()) {
                    upload.delete();
                } else {
                    upload.createNewFile();
                }

                //保存文件
                file.transferTo(upload);

                List<Question> questions = new ArrayList<>();
                List<String[]> strings = POIUtil.readExcel(upload);
                for (String[] s : strings) {
                    Question question = new Question.Builder()
                            .setTitle(s[0]).setContent(s[1]).
                                    setSubjectId(Integer.parseInt(s[2])).setQuestionType(Integer.parseInt(s[3])).setOptionA(s[4]).
                                    setOptionB(s[5]).setOptionC(s[6]).
                                    setOptionD(s[7]).setAnswer(s[8]).
                                    setParse(s[9]).
                                    build();
                    questions.add(question);
                }
                questionService.addQuestions(questions);

            } catch (Exception e) {
                System.out.println("上传文件失败，原因：" + e.toString());
                //异常抛到前端
                throw new QexzWebException(QexzWebError.UPLOAD_FILE_IMAGE_ANALYZE_ERROR);
            }
        }
        return new AjaxResultDto().setSuccess(true);

    }


    /**
     * 成绩管理-考试列表
     */
    @RequestMapping(value = "/result/contest/list", method = RequestMethod.GET)
    public String resultContestList(HttpServletRequest request,
                                    @RequestParam(value = "page", defaultValue = "1") int page,
                                    Model model) {
        try {
            Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
            model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
            if (currentAccount == null || currentAccount.getLevel() < 1) {
                //return "redirect:/";
                return "/error/404";
            } else {


                Map<String, Object> data = contestService.getContests(page, QexzConst.contestPageSize);
                List<Subject> subjects = subjectService.getSubjects();
                Map<Integer, String> subjectId2Name = subjects.stream().collect(Collectors.toMap(Subject::getId, Subject::getName));
                data.put("subjects", subjects);

                model.addAttribute("subjectId2Name", subjectId2Name);
                model.addAttribute(QexzConst.DATA, data);
                return "/manage/manage-resultContestBoard";
            }
        } catch (Exception e) {
            LOG.error("后台成绩管理页发生错误，原因：" + e);
            return "/error/404";
        }
    }

    /**
     * 成绩管理-考试列表-学生成绩列表
     */
    @RequestMapping(value = "/result/contest/{contestId}/list", method = RequestMethod.GET)
    public String resultStudentList(HttpServletRequest request,
                                    @PathVariable("contestId") int contestId,
                                    @RequestParam(value = "page", defaultValue = "1") int page,
                                    Model model) {
        try {
            Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
            model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
            if (currentAccount == null || currentAccount.getLevel() < 1) {
                return "/error/404";
            } else {
                //用于获得考试等信息
                Contest contest = contestService.getContestById(contestId);

                //设置question，用于批阅试卷
                List<ContestContent> contestContents = contestContentService.getContentByContestId(contestId);
                //获取问题
                List<Integer> questionIds = contestContents.stream().map(ContestContent::getQuestionId).collect(Collectors.toList());
                List<Question> questions = questionService.getQuestionByIds(questionIds);
                List<Question> questionList = new ArrayList<>();

                for (Question question : questions) {
                    if (question.getQuestionType() == 2) {
                        questionList.add(question);//仅添加主观题
                    }
                }

                //用于设置考试信息，考试信息和答案信息需要分页
                Map<String, Object> data = gradeService.getGradesByContestId(contestId, page, QexzConst.gradePageSize);
                List<Grade> grades = (List<Grade>) data.remove("grades");
                List<Integer> studentIds = grades.stream().map(Grade::getStudentId).collect(Collectors.toList());
                List<Account> students = accountService.getAccountsByStudentIds(studentIds);
                Map<Integer, Account> id2student = students.stream().
                        collect(Collectors.toMap(Account::getId, account -> account));
                List<Answer> answers = answerService.getAnswerByContestId(contestId, page, QexzConst.gradePageSize);
                Map<Integer, Answer> id2Answer = answers.stream().collect(Collectors.toMap(Answer::getStudentId, answer -> answer));
                List<ContestRankVo> contestRankVos = new ArrayList<>();
                Gson gson = new Gson();
                for (Grade grade : grades) {
                    ContestRankVo contestRankVo = new ContestRankVo();
                    contestRankVo.setGrade(grade);
                    contestRankVo.setAccount(id2student.get(grade.getStudentId()));
                    AnswerVo answerVo = new AnswerVo();
                    answerVo.setAnswer(id2Answer.get(grade.getStudentId()));
                    List<AnswerVo.AnswerContent> answerContents = gson.fromJson(id2Answer.get(grade.getStudentId()).getAnswerJson(), new TypeToken<List<AnswerVo.AnswerContent>>() {
                    }.getType());
                    //仅添加主观题
                    List<AnswerVo.AnswerContent> collect = answerContents.stream().filter(a -> a.getQuestionType() == 2).collect(Collectors.toList());
                    answerVo.setAnswerContents(collect);
                    contestRankVo.setAnswerVo(answerVo);
                    contestRankVos.add(contestRankVo);
                }
                data.put("contestRankVos", contestRankVos);
                data.put("contest", contest);
                data.put("questions", questionList);
                model.addAttribute(QexzConst.DATA, data);
                return "/manage/manage-resultStudentBoard";
            }

        } catch (Exception e) {
            LOG.error("后台成绩批改管理页发生错误，原因：" + e);
            return "/error/404";
        }

    }

    /**
     * 课程管理
     */
    @RequestMapping(value = "/subject/list", method = RequestMethod.GET)
    public String subjectList(HttpServletRequest request,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              Model model) {
        try {
            Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
            model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
            if (currentAccount == null || currentAccount.getLevel() < 1) {
                return "/error/404";
            } else {
                Map<String, Object> data = subjectService.getSubjects(page, QexzConst.subjectPageSize);
                model.addAttribute(QexzConst.DATA, data);
                return "/manage/manage-subjectBoard";
            }
        } catch (Exception e) {
            LOG.error("后台课程管理页发生错误，原因：" + e);
            return "error/404";
        }
    }

    /**
     * 帖子管理
     */
    @RequestMapping(value = "/post/list", method = RequestMethod.GET)
    public String postList(HttpServletRequest request,
                           @RequestParam(value = "page", defaultValue = "1") int page,
                           Model model) {
        try {
            Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
            model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
            if (currentAccount == null || currentAccount.getLevel() < 1) {
                return "/error/404";
            } else {
                Map<String, Object> data = postService.getPosts(page, QexzConst.postPageSize, 0, -1);
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
                model.addAttribute(QexzConst.DATA, data);
                return "/manage/manage-postBoard";
            }

        } catch (Exception e) {
            LOG.error("后台帖子管理页发生错误，原因：" + e);
            return "/error/404";
        }
    }

    /**
     * 评论管理
     */
    @RequestMapping(value = "/comment/list", method = RequestMethod.GET)
    public String commentList(HttpServletRequest request,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              Model model) {
        try {

            Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
            model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
            if (currentAccount == null || currentAccount.getLevel() < 1) {
                return "/error/404";
            } else {
                Map<String, Object> data = commentService.getComments(page, QexzConst.commentPageSize);
                List<Comment> comments = (List<Comment>) data.remove("comments");
                Set<Integer> userIds = comments.stream().map(Comment::getUserId).collect(Collectors.toCollection(HashSet::new));
                List<Account> users = accountService.getAccountsByIds(userIds);
                Map<Integer, Account> id2user = users.stream().
                        collect(Collectors.toMap(Account::getId, account -> account));

                List<DiscussDetailVo> discussDetailVos = new ArrayList<>();
                for (Comment comment : comments) {
                    DiscussDetailVo discussDetailVo = new DiscussDetailVo();
                    discussDetailVo.setComment(comment);
                    discussDetailVo.setUser(id2user.get(comment.getUserId()));
                    discussDetailVos.add(discussDetailVo);
                }
                data.put("discussDetailVos", discussDetailVos);
                model.addAttribute(QexzConst.DATA, data);
                return "/manage/manage-commentBoard";
            }

        } catch (Exception e) {
            LOG.error("后台评论管理页发生错误，原因：" + e);
            return "/error/404";
        }
    }


    @RequestMapping(value = "/complaint/list", method = RequestMethod.GET)
    public String complaintList(HttpServletRequest request,
                                @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "kind", defaultValue = "3") int kind,
                                Model model) {
        try {
            Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
            model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
            if (currentAccount == null || currentAccount.getLevel() < 1) {
                return "/error/404";
            } else {
                Map<String, Object> map = complaintService.getWhichComplaint(kind, page, QexzConst.commentPageSize);
                List<Complaint> complaints = (List<Complaint>) map.remove("complaints");
                Set<Integer> userIds = complaints.stream().map(Complaint::getUserId).collect(Collectors.toSet());
                List<Account> accounts = accountService.getAccountsByIds(userIds);
                Map<Integer, Account> id2Account = accounts.stream().collect(Collectors.toMap(Account::getId, a -> a));
                List<ComplaintVo> complaintVos = new ArrayList<>();
                if (kind == 0) {
                    //投诉人
                    for (Complaint complaint : complaints) {
                        ComplaintVo complaintVo = new ComplaintVo();
                        Account account = id2Account.get(complaint.getUserId());
                        complaintVo.setComplaintContent(account.getName());
                        complaintVo.setComplaintKind(kind);
                        complaintVo.setComplaintReason(complaint.getComplaintContent());
                        complaintVo.setComplaintTime(complaint.getCreateTime());
                        complaintVo.setComplaintType(complaintVo.getComplaintKind());
                        complaintVo.setUsername(account.getUsername());
                        complaintVo.setAtUserId(complaint.getWhichId());
                        complaintVo.setUserId(complaint.getUserId());
                        complaintVo.setState(complaint.getComplaintState());
                        complaintVo.setComplaintKindId(complaint.getWhichId());
                        complaintVos.add(complaintVo);
                    }

                } else if (kind == 1) {

                    Set<Integer> whichIds = complaints.stream().map(Complaint::getWhichId).collect(Collectors.toSet());
                    List<Post> posts = postService.getPostsByIds(whichIds);
                    if (posts != null && !posts.isEmpty()) {
                        Map<Integer, Post> id2Post = posts.stream().collect(Collectors.toMap(Post::getId, post -> post));
                        //投诉帖子
                        for (Complaint complaint : complaints) {
                            ComplaintVo complaintVo = new ComplaintVo();
                            Post post = id2Post.get(complaint.getWhichId());
                            complaintVo.setComplaintContent("标题：" + post.getTitle() + "\n 内容：" + post.getTextContent());
                            complaintVo.setComplaintKind(kind);
                            complaintVo.setComplaintReason(complaint.getComplaintContent());
                            complaintVo.setComplaintTime(complaint.getCreateTime());
                            complaintVo.setComplaintType(complaintVo.getComplaintKind());
                            complaintVo.setUsername(id2Account.get(complaint.getUserId()).getUsername());
                            complaintVo.setAtUserId(post.getAuthorId());
                            complaintVo.setUserId(complaint.getUserId());
                            complaintVo.setState(complaint.getComplaintState());
                            complaintVo.setComplaintKindId(complaint.getWhichId());
                            complaintVos.add(complaintVo);
                        }
                    }
                } else if (kind == 2) {
                    //投诉评论
                    Set<Integer> whichIds = complaints.stream().map(Complaint::getWhichId).collect(Collectors.toSet());
                    List<Comment> comments = commentService.getCommentsByIds(whichIds);
                    if (comments != null && !comments.isEmpty()) {
                        Map<Integer, Comment> id2Comment = comments.stream().collect(Collectors.toMap(Comment::getId, comment -> comment));
                        for (Complaint complaint : complaints) {
                            ComplaintVo complaintVo = new ComplaintVo();
                            Comment comment = id2Comment.get(complaint.getWhichId());
                            complaintVo.setComplaintContent(comment.getContent());
                            complaintVo.setComplaintKind(kind);
                            complaintVo.setComplaintReason(complaint.getComplaintContent());
                            complaintVo.setComplaintTime(complaint.getCreateTime());
                            complaintVo.setComplaintType(complaintVo.getComplaintKind());
                            complaintVo.setUsername(id2Account.get(complaint.getUserId()).getUsername());
                            complaintVo.setAtUserId(comment.getUserId());
                            complaintVo.setUserId(complaint.getUserId());
                            complaintVo.setState(complaint.getComplaintState());
                            complaintVo.setComplaintKindId(complaint.getWhichId());
                            complaintVos.add(complaintVo);

                        }
                    }

                } else {
                    //投诉回复
                    Set<Integer> whichIds = complaints.stream().map(Complaint::getWhichId).collect(Collectors.toSet());
                    List<Reply> replies = replyService.getRepliesByIds(whichIds);
                    if (replies != null && !replies.isEmpty()) {
                        Map<Integer, Reply> id2Reply = replies.stream().collect(Collectors.toMap(Reply::getId, reply -> reply));
                        for (Complaint complaint : complaints) {
                            ComplaintVo complaintVo = new ComplaintVo();
                            Reply reply = id2Reply.get(complaint.getWhichId());
                            complaintVo.setComplaintContent(reply.getContent());
                            complaintVo.setComplaintKind(kind);
                            complaintVo.setComplaintReason(complaint.getComplaintContent());
                            complaintVo.setComplaintTime(complaint.getCreateTime());
                            complaintVo.setComplaintType(complaintVo.getComplaintKind());
                            complaintVo.setUsername(id2Account.get(complaint.getUserId()).getUsername());
                            complaintVo.setAtUserId(reply.getUserId());
                            complaintVo.setUserId(complaint.getUserId());
                            complaintVo.setState(complaint.getComplaintState());
                            complaintVo.setComplaintKindId(complaint.getWhichId());
                            complaintVos.add(complaintVo);
                        }
                    }
                }
                map.put("complaintVos", complaintVos);
                model.addAttribute(QexzConst.DATA, map);
                model.addAttribute("kind", kind);
                return "/manage/manage-complaint";

            }
        } catch (Exception e) {
            LOG.error("后台管理投诉页发生错误，原因：" + e);
            return "/error/404";
        }

    }

    @RequestMapping("/notice/list")
    public String noticeList(HttpServletRequest request, Model model) {
        try {
            Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
            if (currentAccount == null || currentAccount.getLevel() < 1) {
                return "/error/404";
            } else {
                model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
                model.addAttribute("notices", noticeService.getSystemNotice());
                return "/manage/manage-notice";
            }
        } catch (Exception e) {
            LOG.error("后台管理公告页发生错误，原因：" + e);
            return "/error/404";
        }

    }


}
