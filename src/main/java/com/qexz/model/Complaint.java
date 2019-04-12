package com.qexz.model;

/* 				    
 **********************************************
 *      DATE           PERSON       REASON
 *    2019/4/9          FXY        Created
 **********************************************
 */


import java.util.Date;

public class Complaint {
    private int id;
    private int userId;
    private int complaintWhich;
    private int whichId;
    private int complaintType;
    private String complaintContent;
    private int complaintState;
    private Date createTime;
    private Date updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getComplaintWhich() {
        return complaintWhich;
    }

    public void setComplaintWhich(int complaintWhich) {
        this.complaintWhich = complaintWhich;
    }

    public int getWhichId() {
        return whichId;
    }

    public void setWhichId(int whichId) {
        this.whichId = whichId;
    }

    public int getComplaintType() {
        return complaintType;
    }

    public void setComplaintType(int complaintType) {
        this.complaintType = complaintType;
    }

    public String getComplaintContent() {
        return complaintContent;
    }

    public void setComplaintContent(String complaintContent) {
        this.complaintContent = complaintContent;
    }

    public int getComplaintState() {
        return complaintState;
    }

    public void setComplaintState(int complaintState) {
        this.complaintState = complaintState;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
