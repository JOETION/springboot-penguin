package com.qexz.controller;

import com.qexz.dto.AjaxResultDto;
import com.qexz.model.Reply;
import com.qexz.service.ReplyService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/reply")
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    //添加回复
    @RequestMapping(value = "/api/addReply", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultDto addReply(@RequestBody Reply reply) {
        int replyId = replyService.addReply(reply);
        return new AjaxResultDto().setData(replyId);
    }

    @DeleteMapping(value = "/api/deleteReply/{id}")
    @ResponseBody
    public AjaxResultDto deleteReply(@PathVariable int id) {
        boolean b = replyService.deleteReplyById(id);
        return new AjaxResultDto().setData(b);
    }

}
