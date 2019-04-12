package com.qexz.service;

/* 				    
 **********************************************
 *      DATE           PERSON       REASON
 *    2019/4/9          FXY        Created
 **********************************************
 */


import com.qexz.model.Complaint;

import java.util.Map;

public interface ComplaintService {
    boolean addComplaint(Complaint complaint);

    Complaint getWhichComplaintByUserId(int which,int whichId,int userId);

    Map<String, Object> getWhichComplaint(int which,int pageNum, int pageSize);

    boolean deleteWhichComplaintByUserId(int which,int whichId,int userId);

    boolean updateWhichComplaintByUserId(int which,int whichId,int userId,int state);

}
