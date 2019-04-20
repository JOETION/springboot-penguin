package com.qexz.service;

import com.qexz.model.Post;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PostService {

    int addPost(Post post);

    boolean updatePostById(Post post);

    boolean deletePostById(int id);

    Map<String, Object> getPosts(int pageNum, int pageSize,int level,int type);

    Post getPostById(int id);

    boolean updateReplyNumById(int id);

    Map<String, Object> getPostsByAuthorId(int pageNum, int pageSize, int authorId);

    List<Post> getPostsByIds(Set<Integer> ids);

}
