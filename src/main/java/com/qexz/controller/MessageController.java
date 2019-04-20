package com.qexz.controller;

/* 				    
 **********************************************
 *      DATE           PERSON       REASON
 *    2019/4/14          FXY        Created
 **********************************************
 */

import com.qexz.common.QexzConst;
import com.qexz.dto.AjaxResultDto;
import com.qexz.exception.QexzWebError;
import com.qexz.model.Account;
import com.qexz.model.Message;
import com.qexz.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "/message")
public class MessageController {

    private static Logger LOG = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    MessageService messageService;

    @RequestMapping(value = "/api/updateMessageState", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResultDto updateMessageState(HttpServletRequest request, @RequestParam("id") int id) {
        try {
            HttpSession session = request.getSession();
            Account current_account = (Account) session.getAttribute(QexzConst.CURRENT_ACCOUNT);
            if (current_account == null) {
                return new AjaxResultDto().setMessage("请先登录！");
            }
            messageService.updateMessageStateById(id);
            //更新session里面的值
            List<Message> messages = (List<Message>) session.getAttribute(QexzConst.CURRENT_MESSAGES);
            messages.stream().filter(message -> message.getId() == id).forEach(message1 -> message1.setMessageState(1));
            session.setAttribute(QexzConst.CURRENT_MESSAGES, messages);
            return new AjaxResultDto().setData(true);
        } catch (Exception e) {
            LOG.error("更新消息状态失败，原因：" + e);
            return AjaxResultDto.fixedError(QexzWebError.COMMON);
        }
    }


    @RequestMapping(value = "/api/deleteMessage", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResultDto deleteMessage(HttpServletRequest request, @RequestParam("id") int id) {
        try {
            HttpSession session = request.getSession();
            List<Message> messages = (List<Message>) session.getAttribute(QexzConst.CURRENT_MESSAGES);
            messages.removeIf(message -> message.getId() == id);
            messageService.deleteMessageById(id);
            session.setAttribute(QexzConst.CURRENT_MESSAGES, messages);
            return new AjaxResultDto().setData(true);

        } catch (Exception e) {
            LOG.error("删除消息失败，原因：" + e);
            return AjaxResultDto.fixedError(QexzWebError.COMMON);
        }
    }


}
