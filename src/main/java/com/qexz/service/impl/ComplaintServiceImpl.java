package com.qexz.service.impl;

/* 				    
 **********************************************
 *      DATE           PERSON       REASON
 *    2019/4/9          FXY        Created
 **********************************************
 */


import com.github.pagehelper.PageHelper;
import com.qexz.dao.*;
import com.qexz.model.*;
import com.qexz.service.ComplaintService;
import com.qexz.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("complaintService")
public class ComplaintServiceImpl implements ComplaintService {

    @Autowired
    ComplaintMapper complaintMapper;

    @Autowired
    MessageMapper messageMapper;

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    PostMapper postMapper;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    ReplyMapper replyMapper;

    @Autowired
    NoticeMapper noticeMapper;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean addComplaint(Complaint complaint) {
        int row = complaintMapper.insertComplaint(complaint);
        Message message = new Message();
        message.setMessageContent("你于：" + CommonUtils.getDateByFormat(new Date(), "yyyy-MM-dd HH:mm:ss") + " 提交的投诉内容为：《 " + complaint.getComplaintContent() + " 》的投诉信息，正在审核中，请耐心等待！");
        message.setMessageState(0);
        message.setMessageUrl("http://www.baidu.com");
        message.setMessageTitle("投诉信息正在审核");
        message.setUserId(complaint.getUserId());
        int row1 = messageMapper.insertMessage(message);
        if (row > 0 && row1 > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Complaint getWhichComplaintByUserId(int which, int whichId, int userId) {
        return complaintMapper.queryWhichComplaintByUserId(which, whichId, userId);
    }

    @Override
    public Map<String, Object> getWhichComplaint(int which, int pageNum, int pageSize) {
        Map<String, Object> data = new HashMap<>();
        int count = complaintMapper.getCountByWhich(which);
        if (count == 0) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", 1);
            data.put("totalPageSize", 0);
            data.put("complaints", new ArrayList<>());
            return data;
        }
        int totalPageNum = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        if (pageNum > totalPageNum) {
            data.put("pageNum", 0);
            data.put("pageSize", 0);
            data.put("totalPageNum", totalPageNum);
            data.put("totalPageSize", 0);
            data.put("complaints", new ArrayList<>());
            return data;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Complaint> complaints = complaintMapper.queryComplaintByWhich(which);
        data.put("pageNum", pageNum);
        data.put("pageSize", pageSize);
        data.put("totalPageNum", totalPageNum);
        data.put("totalPageSize", count);
        data.put("complaints", complaints);
        return data;
    }

    @Override
    public boolean deleteWhichComplaintByUserId(int which, int whichId, int userId) {
        int row = complaintMapper.deleteComplaint(which, whichId, userId);
        if (row > 0) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean updateWhichComplaintByUserId(Complaint complaint, int atuserId) {

        Account atAccount = accountMapper.getAccountById(atuserId);
        int which = complaint.getComplaintWhich();
        String result = null;
        int state = complaint.getComplaintState();
        if (state == 1) {
            result = "失败";
        } else if (state == 2) {
            result = "成功";
        }


        //举报用户
        if (which == 0) {
            Message message = new Message();
            message.setMessageContent("你于：" + CommonUtils.getDateByFormat(complaint.getCreateTime(), "yyyy-MM-dd HH:mm:ss") + "中举报用户：" + atAccount.getName() + " " + state);
            message.setUserId(complaint.getUserId());
            message.setMessageState(0);
            message.setMessageTitle("投诉" + result);
            message.setMessageUrl("http:www.baidu.com");
            messageMapper.insertMessage(message);
            if (state == 2) {
                message.setMessageContent("你于：" + CommonUtils.getDateByFormat(complaint.getCreateTime(), "yyyy-MM-dd HH:mm:ss") + "中被举报，请注意言行！");
                message.setUserId(atuserId);
                message.setMessageTitle("账号被封禁");
                messageMapper.insertMessage(message);

                //账号封禁
                atAccount.setState(1);
                accountMapper.updateAccountById(atAccount);
            }

        } else {
            //举报帖子，评论，回复，则统一设置
            Message message = new Message();
            Notice notice = new Notice();
            Post post = null;
            switch (which) {
                case 1:
                    post = postMapper.getPostById(complaint.getWhichId());
                    notice.setNoticeContent("用户：" + atAccount.getName() + " 于" + CommonUtils.getDateByFormat(post.getCreateTime(), "yyyy-MM-dd HH:mm:ss") + "在帖子《" + post.getTitle() + "》中有违规言行，以封禁");
                    message.setMessageContent("你于：" + CommonUtils.getDateByFormat(post.getCreateTime(), "yyyy-MM-dd HH:mm:ss") + "在帖子《" + post.getTitle() + "》中有违规言行，请注意言行！");
                    break;
                case 2:
                    Comment comment = commentMapper.getCommentById(complaint.getWhichId());
                    post = postMapper.getPostById(comment.getPostId());
                    notice.setNoticeContent("用户：" + atAccount.getName() + " 于" + CommonUtils.getDateByFormat(comment.getCreateTime(), "yyyy-MM-dd HH:mm:ss") + "在帖子《" + post.getTitle() + "》的评论中有违规言行，以封禁");
                    message.setMessageContent("你于：" + CommonUtils.getDateByFormat(comment.getCreateTime(), "yyyy-MM-dd HH:mm:ss") + "在帖子《" + post.getTitle() + "》的评论中有违规言行，请注意言行！");
                    break;
                case 3:
                    Reply reply = replyMapper.getReplyById(complaint.getWhichId());
                    post = postMapper.getPostById(reply.getPostId());
                    notice.setNoticeContent("用户：" + atAccount.getName() + " 于" + CommonUtils.getDateByFormat(reply.getCreateTime(), "yyyy-MM-dd HH:mm:ss") + "在帖子《" + post.getTitle() + "》的回复中有违规言行，以封禁");
                    message.setMessageContent("你于：" + CommonUtils.getDateByFormat(reply.getCreateTime(), "yyyy-MM-dd HH:mm:ss") + "在帖子《" + post.getTitle() + "》的回复中有违规言行，请注意言行！");
                    break;
            }
            if (state == 2) {
                notice.setNoticeType(1);
                notice.setNoticeUrl("http:www.baidu.com");
                noticeMapper.insertNotice(notice);

                message.setUserId(atuserId);
                message.setMessageState(0);
                message.setMessageTitle("账号被封禁");
                message.setMessageUrl("http:www.baidu.com");
                messageMapper.insertMessage(message);
                //账号封禁
                atAccount.setState(1);
                accountMapper.updateAccountById(atAccount);
            }
            message.setMessageTitle("投诉" + result);
            message.setMessageUrl("http:www.baidu.com");
            message.setMessageContent("你于：" + CommonUtils.getDateByFormat(complaint.getCreateTime(), "yyyy-MM-dd HH:mm:ss") + "举报帖子《" + post.getTitle() + "》的相关信息 " + state);
            message.setUserId(complaint.getUserId());
            messageMapper.insertMessage(message);
        }


        int row = complaintMapper.updateComplaint(which, complaint.getWhichId(), complaint.getUserId(), state);
        if (row > 0) {
            return true;
        }
        return false;
    }

}
