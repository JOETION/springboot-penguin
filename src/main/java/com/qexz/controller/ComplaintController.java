package com.qexz.controller;

/* 				    
 **********************************************
 *      DATE           PERSON       REASON
 *    2019/4/9          FXY        Created
 **********************************************
 */

import com.qexz.dto.AjaxResultDto;
import com.qexz.dto.ComplaintDto;
import com.qexz.model.*;
import com.qexz.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@RequestMapping(value = "/complaint")
public class ComplaintController {

    @Autowired
    ComplaintService complaintService;

    @Autowired
    NoticeService noticeService;

    @Autowired
    MessageService messageService;

    @Autowired
    AccountService accountService;

    @Autowired
    PostService postService;


    @RequestMapping(value = "/api/addComplaint", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public AjaxResultDto addComplaint(@RequestBody Complaint complaint) {
        Complaint complaintByUserId = complaintService.getWhichComplaintByUserId(complaint.getComplaintWhich(), complaint.getWhichId(), complaint.getUserId());
        if (complaintByUserId == null) {
            boolean result = complaintService.addComplaint(complaint);
            if (result) {
                return new AjaxResultDto().setData(result);
            }
            return new AjaxResultDto().setMessage("添加投诉信息失败！");
        } else {
            return new AjaxResultDto().setMessage("已经投诉过该条信息了！");
        }
    }

    @RequestMapping(value = "/api/deleteComplaint", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResultDto deleteComplaint(@RequestParam("userId") int userId, @RequestParam("which") int which, @RequestParam("whichId") int whichId) {
        boolean b = complaintService.deleteWhichComplaintByUserId(which, whichId, userId);
        if (b) {
            return new AjaxResultDto().setData(b);
        }
        return new AjaxResultDto().setMessage("删除投诉失败");
    }

    //更新投诉状态
    //写在一个方法中，保证事务
    @RequestMapping(value = "/api/updateComplaint", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultDto updateComplaint(@RequestBody ComplaintDto complaintDto) {

        Account atAccount = accountService.getAccountById(complaintDto.getAtUserId());

        String state = null;
        if (complaintDto.getState() == 1) {
            state = "失败";
        } else if (complaintDto.getState() == 2) {
            state = "成功";
        }

        int which = complaintDto.getWhich();

        if (which == 0) {
            Message message = new Message();
            message.setMessageContent("你于：" + complaintDto.getComplaintTime() + "中举报用户：" + atAccount.getName() + " " + state);
            message.setUserId(complaintDto.getUserId());
            message.setMessageState(0);
            messageService.addMessage(message);
            if (complaintDto.getState() == 2) {
                message.setMessageContent("你于：" + complaintDto.getComplaintTime() + "中被举报，请注意言行！");
                message.setUserId(complaintDto.getAtUserId());
                messageService.addMessage(message);
            }

        } else if (which == 1 || which == 2 || which == 3) {
            Post post = postService.getPostById(complaintDto.getWhichId());
            Message message = new Message();
            if (complaintDto.getState() == 2) {
                Notice notice = new Notice();
                notice.setNoticeContent("用户：" + atAccount.getName() + "于 " + post.getCreateTime() + "在帖子《" + post.getTitle() + "》中有违规言行，以封禁");
                notice.setNoticeType(1);
                notice.setNoticeUrl("http:www.baidu.com");
                noticeService.addNotice(notice);
                message.setMessageContent("你于：" + complaintDto.getComplaintTime() + "在帖子《" + post.getTitle() + "》中有违规言行，请注意言行！");
                message.setUserId(complaintDto.getAtUserId());
                message.setMessageState(0);
                messageService.addMessage(message);
            }
            message.setMessageContent("你于：" + complaintDto.getComplaintTime() + "举报帖子《" + post.getTitle() + "》" + state);
            message.setUserId(complaintDto.getUserId());
            messageService.addMessage(message);
        }

        boolean b = complaintService.updateWhichComplaintByUserId(which, complaintDto.getWhichId(), complaintDto.getUserId(), complaintDto.getState());

        if (b) {
            return new AjaxResultDto().setData(b);
        }
        return new AjaxResultDto().setMessage("更新投诉信息失败！");

    }

}
