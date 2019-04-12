package com.qexz.vo;

/* 				    
 **********************************************
 *      DATE           PERSON       REASON
 *    2019/4/10          FXY        Created
 **********************************************
 */


import java.util.Date;

public class ComplaintVo {
    private int userId; //举报用户编号
    private String username;//举报用户名称
    private int complaintType;
    private String complaintContent;
    private Date complaintTime;
    private String complaintReason;
    private int complaintKind;
    private int atUserId;//被举报用户编号
    private int state;
    private int complaintKindId;


    public int getComplaintKindId() {
        return complaintKindId;
    }

    public void setComplaintKindId(int complaintKindId) {
        this.complaintKindId = complaintKindId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getAtUserId() {
        return atUserId;
    }

    public void setAtUserId(int atUserId) {
        this.atUserId = atUserId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getComplaintKind() {
        return complaintKind;
    }

    public void setComplaintKind(int complaintKind) {
        this.complaintKind = complaintKind;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Date getComplaintTime() {
        return complaintTime;
    }

    public void setComplaintTime(Date complaintTime) {
        this.complaintTime = complaintTime;
    }

    public String getComplaintReason() {
        return complaintReason;
    }

    public void setComplaintReason(String complaintReason) {
        this.complaintReason = complaintReason;
    }
}
