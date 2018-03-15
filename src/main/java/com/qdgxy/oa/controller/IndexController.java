package com.qdgxy.oa.controller;

import com.qdgxy.oa.meta.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by liyongjun on 17/1/8.
 */

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(){

        return "login";

    }
}
