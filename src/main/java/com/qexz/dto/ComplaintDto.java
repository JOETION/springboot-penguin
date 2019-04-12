package com.qexz.dto;

/* 				    
 **********************************************
 *      DATE           PERSON       REASON
 *    2019/4/12          FXY        Created
 **********************************************
 */


import java.io.Serializable;
import java.util.Date;

public class ComplaintDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private int userId;
    private int atUserId;
    private Date complaintTime;
    private int which;
    private int whichId;
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAtUserId() {
        return atUserId;
    }

    public void setAtUserId(int atUserId) {
        this.atUserId = atUserId;
    }

    public Date getComplaintTime() {
        return complaintTime;
    }

    public void setComplaintTime(Date complaintTime) {
        this.complaintTime = complaintTime;
    }

    public int getWhich() {
        return which;
    }

    public void setWhich(int which) {
        this.which = which;
    }

    public int getWhichId() {
        return whichId;
    }

    public void setWhichId(int whichId) {
        this.whichId = whichId;
    }
}
