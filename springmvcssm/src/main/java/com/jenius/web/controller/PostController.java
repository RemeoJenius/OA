package com.jenius.web.controller;

import com.jenius.web.meta.Post;
import com.jenius.web.meta.User;
import com.jenius.web.service.impl.PostServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liyongjun on 17/2/9.
 */
@Controller
public class PostController {

    @Resource(name = "postServiceImpl")
    private PostServiceImpl postServiceImpl;

    @RequestMapping("postList")
    public @ResponseBody
    List<Post> postList(){

        return postServiceImpl.getPostList();
    }

    @RequestMapping("getPostById")
    public @ResponseBody Post getPostById(@RequestParam("id") int id){
        return postServiceImpl.getPostById(id);
    }

    @RequestMapping("deletePost")
    public String deletePost(@RequestParam("id") int id){
        postServiceImpl.deletePostById(id);
        return "redirect:/auth/post/list.html";
    }

    @RequestMapping("addPost")
    public String addPost(@ModelAttribute Post post){
        postServiceImpl.addPost(post);
        return "redirect:/auth/post/list.html";
    }
    @RequestMapping("updatePost")
    public String updatePost(@ModelAttribute Post post,@RequestParam("id") int id){
        postServiceImpl.updatePost(post, id);
        return "redirect:/auth/post/list.html";
    }

}
