package com.qexz.service;

/* 				    
 **********************************************
 *      DATE           PERSON       REASON
 *    2019/4/12          FXY        Created
 **********************************************
 */


import com.qexz.model.Notice;

import java.util.List;

public interface NoticeService {

    public boolean addNotice(Notice notice);

    //讨论页公告栏，默认每次4条
    public List<Notice> getBasicNotice();

    //删除公告
    public boolean deleteNotice(int id);

    //取得所有系统公告
    public List<Notice> getSystemNotice();

}
