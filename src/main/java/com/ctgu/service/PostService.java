package com.ctgu.service;

import com.ctgu.model.Post;

import java.util.List;
import java.util.Map;

public interface PostService {

    int addPost(Post post);

    boolean updatePostById(Post post);

    boolean deletePostById(int id);

    Map<String, Object> getPosts(int pageNum, int pageSize);

    Post getPostById(int id);

    boolean updateReplyNumById(int id);

    Map<String, Object> getPostsByAuthorId(int pageNum, int pageSize, int authorId);

    List<Post> getPostsOrderByCreate_time();

    Map<String, Object> getPostsPage(int pageNum, int pageSize , List<Post> posts);
}
