package com.qexz.controller;

import com.qexz.common.QexzConst;
import com.qexz.vo.ExamDetailVo;
import com.qexz.vo.ExamVo;
import com.qexz.dto.AjaxResultDto;
import com.qexz.exception.QexzWebError;
import com.qexz.jobs.ImageHandleJobs;
import com.qexz.model.Account;
import com.qexz.model.Contest;
import com.qexz.model.Grade;
import com.qexz.model.Subject;
import com.qexz.service.*;
import com.qexz.util.MD5;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/account")
public class AccountController {

    private static Log LOG = LogFactory.getLog(AccountController.class);

    @Autowired
    private AccountService accountService;
    @Autowired
    private PostService postService;
    @Autowired
    private GradeService gradeService;
    @Autowired
    private ContestService contestService;
    @Autowired
    private SubjectService subjectService;
    @Value("${upload.file.image.path}")
    private String uploadImagePath;

    /**
     * 个人信息页面
     */
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile(HttpServletRequest request, Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        //TODO::拦截器过滤处理
        if (currentAccount == null) {
            //用户未登录直接返回首页面
            return "redirect:/";
        }
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        return "/user/profile";
    }

    /**
     * 更改密码页面
     */
    @RequestMapping(value = "/password", method = RequestMethod.GET)
    public String password(HttpServletRequest request, Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        //TODO::拦截器过滤处理
        if (currentAccount == null) {
            //用户未登录直接返回首页面
            return "redirect:/";
        }
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        return "/user/password";
    }

    /**
     * 考试记录页面
     */
    @RequestMapping(value = "/myExam", method = RequestMethod.GET)
    public String myExam(HttpServletRequest request, @RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        //TODO::拦截器过滤处理
        if (currentAccount == null) {
            //用户未登录直接返回首页面
            return "redirect:/";
        }
        //得到分数信息
        Map<String, Object> data = gradeService.getGradesByStudentId(page, QexzConst.gradePageSize, currentAccount.getId());
        List<Grade> grades = (List<Grade>) data.remove("grades");

        //得到考试信息和课程信息
        Set<Integer> contestIds = grades.stream().map(Grade::getContestId).collect(Collectors.toCollection(HashSet::new));
        List<Contest> contests = contestService.getContestsByContestIds(contestIds);
        List<Subject> subjects = subjectService.getSubjects();
        Map<Integer, String> subjectId2name = subjects.stream().
                collect(Collectors.toMap(Subject::getId, Subject::getName));
        Map<Integer, Contest> id2contest = contests.stream().
                collect(Collectors.toMap(Contest::getId, contest -> contest));
        List<ExamVo> examVos = new ArrayList<>();
        for (Grade grade : grades) {
            ExamVo examVo = new ExamVo();
            examVo.setSubjectName(subjectId2name.getOrDefault(grade.getContestId(), "未知科目"));
            examVo.setContest(id2contest.get(grade.getContestId()));
            examVo.setGrade(grade);
            //此处效率有点低
            examVo.setRank(gradeService.getRankByContestIdAndStudentId(grade.getContestId(), grade.getStudentId()));
            examVos.add(examVo);
        }
        data.put("examVos", examVos);
        model.addAttribute(QexzConst.DATA, data);
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        return "/user/myExam";
    }


    /**
     * 得分详情页
     */
    @RequestMapping(value = "/myExamDetail")
    public String getContestDetailInfo(HttpServletRequest request, @RequestParam("contestId") int contestId, @RequestParam("studentId") int studentId, Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        List<ExamDetailVo> data = accountService.getContestDetailInfo(contestId, studentId);
        if (data == null) {
            return "/error/404";
        }
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        model.addAttribute(QexzConst.DATA, data);
        model.addAttribute("contestName", contestService.getContestById(contestId).getTitle());
        return "/user/myExamDetail";
    }

    /**
     * 我的发帖页面
     */
    @RequestMapping(value = "/myDiscussPost", method = RequestMethod.GET)
    public String myDiscussPost(HttpServletRequest request, @RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
        if (currentAccount == null) {
            //用户未登录直接返回首页面
            return "redirect:/";
        }
        Map<String, Object> data = postService.getPostsByAuthorId(page, QexzConst.postPageSize, currentAccount.getId());
        model.addAttribute(QexzConst.DATA, data);
        model.addAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
        return "/user/myDiscussPost";
    }

    /**
     * 更新密码
     */
    @RequestMapping(value = "/api/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultDto updatePassword(HttpServletRequest request, HttpServletResponse response) {
        AjaxResultDto ajaxResultDto = new AjaxResultDto();
        try {
            String oldPassword = request.getParameter("oldPassword");
            String newPassword = request.getParameter("newPassword");
            String confirmNewPassword = request.getParameter("confirmNewPassword");
            String md5OldPassword = MD5.md5(QexzConst.MD5_SALT + oldPassword);
            String md5NewPassword = MD5.md5(QexzConst.MD5_SALT + newPassword);
            if (StringUtils.isNotEmpty(newPassword) && StringUtils.isNotEmpty(confirmNewPassword)
                    && !newPassword.equals(confirmNewPassword)) {
                return AjaxResultDto.fixedError(QexzWebError.NOT_EQUALS_CONFIRM_PASSWORD);
            }
            Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
            if (!currentAccount.getPassword().equals(md5OldPassword)) {
                return AjaxResultDto.fixedError(QexzWebError.WRONG_PASSWORD);
            }
            currentAccount.setPassword(md5NewPassword);
            boolean result = accountService.updateAccount(currentAccount);
            ajaxResultDto.setSuccess(result);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return AjaxResultDto.fixedError(QexzWebError.COMMON);
        }
        return ajaxResultDto;
    }

    /**
     * 更新个人信息
     */
    @RequestMapping(value = "/api/updateAccount", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultDto updateAccount(HttpServletRequest request) {
        AjaxResultDto ajaxResultDto = new AjaxResultDto();
        try {
            String phone = request.getParameter("phone");
            String qq = request.getParameter("qq");
            String email = request.getParameter("email");
            String description = request.getParameter("description");
            String avatarImgUrl = request.getParameter("avatarImgUrl");

            //从session里面取值，然后保存到数据库，最后更新session
            Account currentAccount = (Account) request.getSession().getAttribute(QexzConst.CURRENT_ACCOUNT);
            currentAccount.setPhone(phone);
            currentAccount.setQq(qq);
            currentAccount.setEmail(email);
            currentAccount.setDescription(description);
            currentAccount.setAvatarImgUrl(avatarImgUrl);
            boolean result = accountService.updateAccount(currentAccount);

            //更新session里面的值
            request.getSession().setAttribute(QexzConst.CURRENT_ACCOUNT, currentAccount);
            ajaxResultDto.setSuccess(result);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return AjaxResultDto.fixedError(QexzWebError.COMMON);
        }
        return ajaxResultDto;
    }

    /**
     * 验证登录
     */
    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultDto login(HttpServletRequest request, HttpServletResponse response) {
        AjaxResultDto ajaxResultDto = new AjaxResultDto();
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            Account current_account = accountService.getAccountByUsername(username);
            if (current_account != null) {
                String pwd = MD5.md5(QexzConst.MD5_SALT + password);
                if (pwd.equals(current_account.getPassword())) {
                    //设置单位为秒，设置为-1永不过期
                    //request.getSession().setMaxInactiveInterval(180*60);    //3小时
                    request.getSession().setAttribute(QexzConst.CURRENT_ACCOUNT, current_account);
                    ajaxResultDto.setData(current_account);
                } else {
                    return AjaxResultDto.fixedError(QexzWebError.WRONG_PASSWORD);
                }
            } else {
                return AjaxResultDto.fixedError(QexzWebError.WRONG_USERNAME);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return AjaxResultDto.fixedError(QexzWebError.COMMON);
        }
        return ajaxResultDto;
    }

    /**
     * 用户退出
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        request.getSession().setAttribute(QexzConst.CURRENT_ACCOUNT, null);
        return "redirect:/";
    }

    /**
     * 上传头像
     */
    @RequestMapping(value = "/api/uploadAvatar", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> uploadAvatar(@RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {
        AjaxResultDto ajaxResultDto = new AjaxResultDto();
        try {
            //原始名称
            String oldFileName = file.getOriginalFilename(); //获取上传文件的原名
            //上传图片
            if (file != null && oldFileName != null && oldFileName.length() > 0) {
                //新的图片名称
                String newFileName = UUID.randomUUID() + oldFileName.substring(oldFileName.lastIndexOf("."));
                //新图片
                File newFile = new File(uploadImagePath + newFileName);

                //将内存中的数据裁剪并写入磁盘
                ImageHandleJobs.Param param = new ImageHandleJobs.Param().setInputStream(file.getInputStream()).setSize(150, 150).setOutQuality(1).setToFile(newFile);
                ImageHandleJobs.addImageHandleJob(param);
                //将新图片名称返回到前端
                ajaxResultDto.setData(newFileName);
            } else {
                return AjaxResultDto.fixedError(QexzWebError.UPLOAD_FILE_IMAGE_NOT_QUALIFIED);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return AjaxResultDto.fixedError(QexzWebError.UPLOAD_FILE_IMAGE_ANALYZE_ERROR);
        }
        return ajaxResultDto;
    }

    /**
     * API:添加用户
     */
    @RequestMapping(value = "/api/addAccount", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultDto addAccount(@RequestBody Account account) {
        Account existAccount = accountService.getAccountByUsername(account.getUsername());
        if (existAccount == null) {//检测该用户是否已经注册
            account.setPassword(MD5.md5(QexzConst.MD5_SALT + account.getPassword()));
            account.setAvatarImgUrl(QexzConst.DEFAULT_AVATAR_IMG_URL);
            account.setDescription("");
            int accountId = accountService.addAccount(account);
            return new AjaxResultDto().setData(accountId);
        }
        return AjaxResultDto.fixedError(QexzWebError.AREADY_EXIST_USERNAME);
    }

    /**
     * API:更新用户
     */
    @RequestMapping(value = "/api/updateManageAccount", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultDto updateAccount(@RequestBody Account account) {
        account.setPassword(MD5.md5(QexzConst.MD5_SALT + account.getPassword()));
        boolean result = accountService.updateAccount(account);
        return new AjaxResultDto().setData(result);
    }

    /**
     * API:删除用户
     */
    @DeleteMapping("/api/deleteAccount/{id}")
    @ResponseBody
    public AjaxResultDto deleteAccount(@PathVariable int id) {
        boolean result = accountService.deleteAccount(id);
        return new AjaxResultDto().setData(result);
    }

    /**
     * API:禁用账号
     */
    @RequestMapping(value = "/api/disabledAccount/{id}", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultDto disabledAccount(@PathVariable int id) {
        boolean result = accountService.disabledAccount(id);
        return new AjaxResultDto().setData(result);
    }

    /**
     * API:解禁账号
     */
    @RequestMapping(value = "/api/abledAccount/{id}", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultDto abledAccount(@PathVariable int id) {
        boolean result = accountService.abledAccount(id);
        return new AjaxResultDto().setData(result);
    }

}
