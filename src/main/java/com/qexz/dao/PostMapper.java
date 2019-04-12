package com.qexz.dao;

import com.qexz.model.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
@Mapper
public interface PostMapper {

    int insertPost(@Param("post") Post post);

    int updatePostById(@Param("post") Post post);

    Post getPostById(@Param("id") int id);

    int deletePostById(@Param("id") int id);

    int getCount();

    //得到类型的帖子，0最新回复，1最新发表，2最热
    List<Post> getPosts(@Param("type") int type);

    int updateReplyNumById(@Param("id") int id, @Param("lastReplyTime") Date lastReplyTime);

    int getCountByAuthorId(@Param("authorId") int authorId);

    List<Post> getPostsByAuthorId(@Param("authorId") int authorId);

    List<Post> getPostsByIds(@Param("ids") Set<Integer> ids);

}
