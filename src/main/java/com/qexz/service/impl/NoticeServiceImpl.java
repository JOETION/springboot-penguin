package com.qexz.service.impl;

/* 				    
 **********************************************
 *      DATE           PERSON       REASON
 *    2019/4/12          FXY        Created
 **********************************************
 */


import com.qexz.dao.NoticeMapper;
import com.qexz.model.Notice;
import com.qexz.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    NoticeMapper noticeMapper;

    @Override
    public boolean addNotice(Notice notice) {

        int row = noticeMapper.insertNotice(notice);
        if (row > 0) {
            return true;
        }

        return false;
    }

    @Override
    public List<Notice> getBasicNotice() {
        return noticeMapper.queryBasicNotice();
    }

    @Override
    public boolean deleteNotice(int id) {
        int row = noticeMapper.deleteNotice(id);
        if (row > 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<Notice> getSystemNotice() {
        return noticeMapper.querySystemNotice();
    }
}
