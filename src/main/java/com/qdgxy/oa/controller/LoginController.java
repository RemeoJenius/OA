package com.qdgxy.oa.controller;

import com.google.code.kaptcha.Constants;
import com.qdgxy.oa.meta.User;
import com.qdgxy.oa.service.impl.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liyongjun on 17/1/1.
 */

@Controller
public class LoginController {

    /**
     *
     */
    @Resource
    private UserServiceImpl userServiceImpl;


    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(HttpServletRequest request , HttpServletResponse response, @RequestParam("rand") String rand , ModelMap map, @ModelAttribute("user") User user) throws IOException {
        HttpSession session = request.getSession();
        String yanzheng = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if(yanzheng.equals(rand)){
            System.out.println(yanzheng);
            return "index";
        }

        return "forward:hello";
    }

    @RequestMapping("check")
    public @ResponseBody Map<String, Object> hello(HttpSession session, HttpServletRequest request , HttpServletResponse response, @RequestParam("rand") String rand , @ModelAttribute("user") User user1) throws ServletException, IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        String yanzheng = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        User user = userServiceImpl.userCheck(user1);

        if (user != null && yanzheng.equals(rand)){
            session.setAttribute("user",user);
            map.put("user",user);
        }else if(yanzheng.equals(rand)){
            map.put("message","用户名或密码错误!");
        }else{
            map.put("message","验证码错误");
        }




        return map;
    }
}
