package com.qexz.dao;

import com.qexz.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@Mapper
public interface CommentMapper {

    int insertComment(@Param("comment") Comment comment);

    List<Comment> getCommentsByPostId(@Param("postId") int postId);

    int getCount();

    List<Comment> getComments();

    int deleteCommentById(@Param("id") int id);

    int deleteCommentsByPostId(@Param("postId") int postId);

    List<Comment> getCommentsByIds(@Param("ids")Set<Integer> ids);
}
