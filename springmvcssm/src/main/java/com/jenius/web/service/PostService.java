package com.jenius.web.service;

import com.jenius.web.meta.Post;

import java.util.List;

/**
 * Created by liyongjun on 17/2/9.
 */
public interface PostService {
    List<Post> getPostList();

    void deletePostById(int id);

    void addPost(Post post);

    Post getPostById(int id);

    void updatePost(Post post, int id);
}
