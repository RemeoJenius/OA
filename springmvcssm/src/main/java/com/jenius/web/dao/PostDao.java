package com.jenius.web.dao;

import com.jenius.web.meta.Post;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by liyongjun on 17/2/9.
 */
public interface PostDao {

    List<Post> getPostList();

    void deletePostById(int id);

    void addPost(@Param("post") Post post);

    void updatePost(@Param("post") Post post,@Param("id") int id);

    Post getPostById(int id);
}
