package com.qexz.dao;

/* 				    
 **********************************************
 *      DATE           PERSON       REASON
 *    2019/4/12          FXY        Created
 **********************************************
 */

import com.qexz.model.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface MessageMapper {

    public int insertMessage(@Param("message")Message message);
}
