package com.qexz.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qexz.common.QexzConst;
import com.qexz.dto.AnswerDto;
import com.qexz.dto.ContestContentDto;
import com.qexz.dto.ContestResultDto;
import com.qexz.exception.QexzWebError;
import com.qexz.exception.QexzWebException;
import com.qexz.jobs.ImportQuestionJobs;
import com.qexz.model.*;
import com.qexz.service.*;
import com.qexz.util.CommonUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static Log LOG = LogFactory.getLog(ManageController.class);

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
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "/error/404";
        } else {
            Map<String, Object> data = accountService.getAccounts(page, QexzConst.accountPageSize);
            model.addAttribute(QexzConst.DATA, data);
            return "/manage/manage-accountList";
        }
    }

    /**
     * 考试管理
     */
    @RequestMapping(value = "/contest/list", method = RequestMethod.GET)
    public String contestList(HttpServletRequest request,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "/error/404";
        } else {
            Map<String, Object> data = contestService.getContests(page, QexzConst.contestPageSize);
            List<Subject> subjects = subjectService.getSubjects();
            data.put("subjects", subjects);
            model.addAttribute(QexzConst.DATA, data);
            return "/manage/manage-contestBoard";
        }
    }

    /**
     * 考试管理-查看试题
     */
    //TODO 管理员和教师权限不够明白，后期区分
    @RequestMapping(value = "/contest/{contestId}/problems", method = RequestMethod.GET)
    public String contestProblemList(HttpServletRequest request,
                                     @PathVariable("contestId") Integer contestId, Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "/error/404";
        } else {
            Map<String, Object> data = new HashMap<>();

            List<ContestContentDto> contestContentDtos = contestContentService.getContentByContestId(contestId);
            Contest contest = contestService.getContestById(contestId);
            data.put("questionsSize", contestContentDtos.size());
            data.put("questions", contestContentDtos);

            //更新分数，以免删除题库的题后分数显示不正确
            int score = contestContentService.getTotalScoreByContestId(contestId);
            if (contest.getTotalScore() != score) {
                contest.setTotalScore(score);
                contestService.updateContestScore(contestId, score);
            }
            data.put("contest", contest);
            model.addAttribute(QexzConst.DATA, data);
            return "/manage/manage-editContestProblem";
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
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
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
    }


    /**
     * 上传试题excel文件，并处理后放进数据库
     *
     * @param file
     * @throws QexzWebException
     */
    @RequestMapping(value = "/question/upload", method = RequestMethod.POST)
    @ResponseBody
    public void fileUpload(@RequestParam("file") MultipartFile file) throws QexzWebException {

        // 判断文件是否为空
        String fileName = file.getOriginalFilename().toLowerCase();
        if (!file.isEmpty() && fileName.endsWith(QexzConst.EXCEL_XLS_SUFFIX) || fileName.endsWith(QexzConst.EXCEL_XLSX_SUFFIX)) {
            try {
                File upload = new File(QexzConst.UPLOAD_QUESTION_EXCEL + CommonUtils.geCurrentTime() + "_" + fileName);
                if (upload.exists()) {
                    upload.delete();
                } else {
                    upload.createNewFile();
                }
                //保存文件
                file.transferTo(upload);
                //添加试题导入数据库任务
                ImportQuestionJobs.addQuestionJob(upload);
            } catch (Exception e) {
                System.out.println("上传文件失败，原因：" + e.toString());
                //异常抛到前端
                throw new QexzWebException(QexzWebError.UPLOAD_FILE_IMAGE_ANALYZE_ERROR);
            }
        }

    }


    /**
     * 成绩管理-考试列表
     */
    @RequestMapping(value = "/result/contest/list", method = RequestMethod.GET)
    public String resultContestList(HttpServletRequest request,
                                    @RequestParam(value = "page", defaultValue = "1") int page,
                                    Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "/error/404";
        } else {
            Map<String, Object> data = contestService.getContests(page, QexzConst.contestPageSize);
            List<Subject> subjects = subjectService.getSubjects();
            data.put("subjects", subjects);
            model.addAttribute(QexzConst.DATA, data);
            return "/manage/manage-resultContestBoard";
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
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            return "/error/404";
        } else {


            //用于获得考试等信息
            Contest contest = contestService.getContestById(contestId);

            //设置question，用于批阅试卷
            List<Question> questions = new ArrayList<>();
            List<ContestContentDto> contestContentDtos = contestContentService.getContentByContestId(contestId);
            for (ContestContentDto contestContentDto : contestContentDtos) {
                Question question = contestContentDto.getQuestion();
                if (question.getQuestionType() == 2) {
                    questions.add(question);//仅添加主观题
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
            List<ContestResultDto> contestResultDtos = new ArrayList<>();
            Gson gson = new Gson();
            for (Grade grade : grades) {
                ContestResultDto contestResultDto = new ContestResultDto();
                contestResultDto.setGrade(grade);
                contestResultDto.setAccount(id2student.get(grade.getStudentId()));
                AnswerDto answerDto = new AnswerDto();
                answerDto.setAnswer(id2Answer.get(grade.getStudentId()));
                List<AnswerDto.AnswerContent> answerContents = gson.fromJson(id2Answer.get(grade.getStudentId()).getAnswerJson(), new TypeToken<List<AnswerDto.AnswerContent>>() {
                }.getType());
                //仅添加主观题
                List<AnswerDto.AnswerContent> collect = answerContents.stream().filter(a -> a.getQuestionType() == 2).collect(Collectors.toList());
                answerDto.setAnswerContents(collect);
                contestResultDto.setAnswerDto(answerDto);
                contestResultDtos.add(contestResultDto);
            }
            data.put("contestResultDtos", contestResultDtos);
            data.put("contest", contest);
            data.put("questions", questions);
            model.addAttribute(QexzConst.DATA, data);
            return "/manage/manage-resultStudentBoard";
        }
    }

    /**
     * 课程管理
     */
    @RequestMapping(value = "/subject/list", method = RequestMethod.GET)
    public String subjectList(HttpServletRequest request,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "/error/404";
        } else {
            Map<String, Object> data = subjectService.getSubjects(page, QexzConst.subjectPageSize);
            model.addAttribute(QexzConst.DATA, data);
            return "/manage/manage-subjectBoard";
        }
    }

    /**
     * 帖子管理
     */
    @RequestMapping(value = "/post/list", method = RequestMethod.GET)
    public String postList(HttpServletRequest request,
                           @RequestParam(value = "page", defaultValue = "1") int page,
                           Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "/error/404";
        } else {
            Map<String, Object> data = postService.getPosts(page, QexzConst.postPageSize);
            List<Post> posts = (List<Post>) data.get("posts");
            Set<Integer> authorIds = posts.stream().map(Post::getAuthorId).collect(Collectors.toCollection(HashSet::new));
            List<Account> authors = accountService.getAccountsByIds(authorIds);
            Map<Integer, Account> id2author = authors.stream().
                    collect(Collectors.toMap(Account::getId, account -> account));
            for (Post post : posts) {
                post.setAuthor(id2author.get(post.getAuthorId()));
            }
            model.addAttribute(QexzConst.DATA, data);
            return "/manage/manage-postBoard";
        }
    }

    /**
     * 评论管理
     */
    @RequestMapping(value = "/comment/list", method = RequestMethod.GET)
    public String commentList(HttpServletRequest request,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        //TODO::处理
        //currentAccount = accountService.getAccountByUsername("admin");
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        if (currentAccount == null || currentAccount.getLevel() < 1) {
            //return "redirect:/";
            return "/error/404";
        } else {
            Map<String, Object> data = commentService.getComments(page, QexzConst.commentPageSize);
            List<Comment> comments = (List<Comment>) data.get("comments");
            Set<Integer> userIds = comments.stream().map(Comment::getUserId).collect(Collectors.toCollection(HashSet::new));
            List<Account> users = accountService.getAccountsByIds(userIds);
            Map<Integer, Account> id2user = users.stream().
                    collect(Collectors.toMap(Account::getId, account -> account));
            for (Comment comment : comments) {
                comment.setUser(id2user.get(comment.getUserId()));
            }
            model.addAttribute(QexzConst.DATA, data);
            return "/manage/manage-commentBoard";
        }
    }
}
