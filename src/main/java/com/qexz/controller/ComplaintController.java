package com.qexz.controller;

/* 				    
 **********************************************
 *      DATE           PERSON       REASON
 *    2019/4/9          FXY        Created
 **********************************************
 */

import com.qexz.common.QexzConst;
import com.qexz.dto.AjaxResultDto;
import com.qexz.dto.ComplaintDto;
import com.qexz.exception.QexzWebError;
import com.qexz.model.*;
import com.qexz.service.*;
import com.qexz.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping(value = "/complaint")
public class ComplaintController {

    private static Logger LOG = LoggerFactory.getLogger(ComplaintController.class);

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

    @Autowired
    CommentService CommentService;

    @Autowired
    ReplyService replyService;


    @RequestMapping(value = "/api/addComplaint", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultDto addComplaint(HttpServletRequest request, @RequestBody Complaint complaint) {
        try {
            Complaint complaintByUserId = complaintService.getWhichComplaintByUserId(complaint.getComplaintWhich(), complaint.getWhichId(), complaint.getUserId());
            if (complaintByUserId == null) {
                complaintService.addComplaint(complaint);
                //刷新session
                request.getSession().setAttribute(QexzConst.CURRENT_MESSAGES, messageService.getMessageByUserId(complaint.getUserId()));
                return new AjaxResultDto().setData(true);
            } else {
                return new AjaxResultDto().setMessage("已经投诉过该条信息了！");
            }
        } catch (Exception e) {
            LOG.error("添加投诉信息失败，原因：" + e);
            return new AjaxResultDto().fixedError(QexzWebError.COMMON);
        }

    }

    @RequestMapping(value = "/api/deleteComplaint", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResultDto deleteComplaint(@RequestParam("userId") int userId, @RequestParam("which") int which, @RequestParam("whichId") int whichId) {
        try {
            complaintService.deleteWhichComplaintByUserId(which, whichId, userId);
            return new AjaxResultDto().setData(true);
        } catch (Exception e) {
            LOG.error("删除投诉信息失败，原因：" + e);
            return AjaxResultDto.fixedError(QexzWebError.COMMON);
        }
    }

    //更新投诉状态
    //写在一个方法中，保证事务
    @RequestMapping(value = "/api/updateComplaint", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultDto updateComplaint(@RequestBody ComplaintDto complaintDto) {

        try {
            Complaint complaint = new Complaint();
            complaint.setComplaintState(complaintDto.getState());
            complaint.setComplaintWhich(complaintDto.getWhich());
            complaint.setWhichId(complaintDto.getWhichId());
            complaint.setCreateTime(complaintDto.getComplaintTime());
            complaint.setUserId(complaintDto.getUserId());
            complaintService.updateWhichComplaintByUserId(complaint, complaintDto.getAtUserId());
            return new AjaxResultDto().setData(true);
        } catch (Exception e) {
            LOG.error("更新投诉状态信息失败，原因：" + e);
            return AjaxResultDto.fixedError(QexzWebError.COMMON);
        }
    }

}
