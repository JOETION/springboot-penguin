package com.qexz.service.impl;

/* 				    
 **********************************************
 *      DATE           PERSON       REASON
 *    2019/4/12          FXY        Created
 **********************************************
 */


import com.qexz.dao.MessageMapper;
import com.qexz.model.Message;
import com.qexz.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("messageService")
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageMapper messageMapper;

    @Override
    public boolean addMessage(Message message) {
        int row = messageMapper.insertMessage(message);
        if (row > 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<Message> getMessageByUserId(int userId) {
        return messageMapper.queryMessageByUserId(userId);
    }

    @Override
    public boolean updateMessageStateById(int id) {
        int row = messageMapper.updateMessageStateById(id);
        if (row > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteMessageById(int id) {
        int row = messageMapper.deleteMessageById(id);
        if (row > 0) {
            return true;
        }
        return false;
    }
}
