package com.qexz.controller;

import com.qexz.dto.AjaxResultDto;
import com.qexz.exception.QexzWebError;
import com.qexz.model.Reply;
import com.qexz.service.ReplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/reply")
public class ReplyController {

    private static Logger LOG = LoggerFactory.getLogger(ReplyController.class);

    @Autowired
    private ReplyService replyService;

    //添加回复
    @RequestMapping(value = "/api/addReply", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultDto addReply(@RequestBody Reply reply) {
        try {
            int replyId = replyService.addReply(reply);
            return new AjaxResultDto().setData(replyId);
        } catch (Exception e) {
            LOG.error("添加回复信息失败，原因：" + e);
            return AjaxResultDto.fixedError(QexzWebError.COMMON);
        }
    }

    @DeleteMapping(value = "/api/deleteReply/{id}")
    @ResponseBody
    public AjaxResultDto deleteReply(@PathVariable int id) {
        try {
            boolean b = replyService.deleteReplyById(id);
            return new AjaxResultDto().setData(b);
        } catch (Exception e) {
            LOG.error("删除回复信息失败，原因：" + e);
            return AjaxResultDto.fixedError(QexzWebError.COMMON);
        }
    }

}
