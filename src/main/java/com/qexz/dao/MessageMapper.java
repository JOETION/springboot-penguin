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

import java.util.List;

@Component
@Mapper
public interface MessageMapper {

    public int insertMessage(@Param("message") Message message);


    public List<Message> queryMessageByUserId(@Param("userId") int userId);

    public int updateMessageStateById(@Param("id") int id);

    public int deleteMessageById(@Param("id") int id);
}
