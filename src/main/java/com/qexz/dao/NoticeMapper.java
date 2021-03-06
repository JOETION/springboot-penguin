package com.qexz.dao;

/* 				    
 **********************************************
 *      DATE           PERSON       REASON
 *    2019/4/12          FXY        Created
 **********************************************
 */


import com.qexz.model.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface NoticeMapper {

    public int insertNotice(@Param("notice") Notice notice);

    public List<Notice> queryBasicNotice();

    public int deleteNotice(@Param("id")int id);

    public List<Notice> querySystemNotice();
}
