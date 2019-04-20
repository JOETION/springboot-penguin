package com.qexz.controller;

/* 				    
 **********************************************
 *      DATE           PERSON       REASON
 *    2019/4/14          FXY        Created
 **********************************************
 */

import com.qexz.dto.AjaxResultDto;
import com.qexz.exception.QexzWebError;
import com.qexz.model.Notice;
import com.qexz.service.NoticeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/notice")
public class NoticeController {

    private Logger LOG = LoggerFactory.getLogger(NoticeController.class);

    @Autowired
    private NoticeService noticeService;

    //发布公告
    @RequestMapping(value = "/api/addNotice", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResultDto addNotice(@RequestBody Notice notice) {
        try {
            noticeService.addNotice(notice);
            return new AjaxResultDto().setData(true);

        } catch (Exception e) {
            LOG.error("添加公告失败，原因：" + e);
            return AjaxResultDto.fixedError(QexzWebError.COMMON);
        }
    }


    //删除公告
    @RequestMapping(value = "/api/deleteNotice", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResultDto deleteNotice(@RequestParam("id") int id) {
        try {
            noticeService.deleteNotice(id);
            return new AjaxResultDto().setData(true);
        } catch (Exception e) {
            LOG.error("删除公告失败，原因：" + e);
            return AjaxResultDto.fixedError(QexzWebError.COMMON);
        }
    }

}
