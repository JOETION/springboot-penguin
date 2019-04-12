package com.qexz.service.impl;

/* 				    
 **********************************************
 *      DATE           PERSON       REASON
 *    2019/4/9          FXY        Created
 **********************************************
 */


import com.github.pagehelper.PageHelper;
import com.qexz.dao.ComplaintMapper;
import com.qexz.model.Complaint;
import com.qexz.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("complaintService")
public class ComplaintServiceImpl implements ComplaintService {

    @Autowired
    ComplaintMapper complaintMapper;

    @Override
    public boolean addComplaint(Complaint complaint) {
        int row = complaintMapper.insertComplaint(complaint);
        if (row > 0) {
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
    public boolean updateWhichComplaintByUserId(int which, int whichId, int userId,int state) {
        int row = complaintMapper.updateComplaint(which, whichId, userId,state);
        if (row > 0) {
            return true;
        }
        return false;
    }
}
