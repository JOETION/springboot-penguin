package com.qexz.dao;

import com.qexz.model.Reply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@Mapper
public interface ReplyMapper {

    int insertReply(@Param("reply") Reply reply);

    List<Reply> getReliesByPostId(@Param("postId") int postId);

    int deleteRepliesByPostId(@Param("postId") int postId);

    int deleteRepliesByCommentId(@Param("commentId") int commentId);

    int deleteReplyById(@Param("id") int id);

    List<Reply> getRepliesByIds(@Param("ids")Set<Integer> ids);
}
