package com.qexz.service;

/* 				    
 **********************************************
 *      DATE           PERSON       REASON
 *    2019/4/12          FXY        Created
 **********************************************
 */


import com.qexz.model.Message;

import java.util.List;

public interface MessageService {

    public boolean addMessage(Message message);

    public List<Message> getMessageByUserId(int userId);

    public boolean updateMessageStateById(int id);

    public boolean deleteMessageById(int id);

}
