package com.jenius.web.service.impl;

import com.jenius.web.dao.PostDao;
import com.jenius.web.meta.Post;
import com.jenius.web.service.PostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liyongjun on 17/2/9.
 */
@Service
public class PostServiceImpl implements PostService {

    @Resource(name = "postDao")
    private PostDao postDao;

    public List<Post> getPostList() {

        return postDao.getPostList();
    }

    public void deletePostById(int id) {
        postDao.deletePostById(id);
    }

    public void addPost(Post post) {
        postDao.addPost(post);
    }

    public Post getPostById(int id) {
        return postDao.getPostById(id);
    }

    public void updatePost(Post post, int id) {
        postDao.updatePost(post, id);
    }
}
