package com.jenius.web.controller;

import com.jenius.web.meta.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by liyongjun on 17/1/24.
 */
@Controller
// 相当于 <bean id="userController" class="com.jenius.web.controller.UserController"></bean>
public class UserController {


    @RequestMapping("/hello")
    public String hello(){


        return "hello";
    }
    @RequestMapping("recieveInt")
    public String recieveInt(@RequestParam(required = true) Integer id){

        System.out.println(id);

        return "hello";
    }

    @RequestMapping("forward")
    public String testForward(){

        System.out.println("forward");
        return "forward:hello";
    }




    /**
     * 接受json数据，使用RequestBody把json数据封装厂User对象
     * 返回User对象，用ResponseBody把User对象转换成json格式
     * @return
     */

    @RequestMapping("/requestJson")
    public @ResponseBody User requestJson(@RequestBody User user){

        System.out.println(user.getUsername());
        return user;
    }
    @RequestMapping("/responseJson")
    public @ResponseBody User responseJson(User user){

        System.out.println(user);
        return user;
    }

    @RequestMapping("/test")
    public @ResponseBody User test() {
        User user = new User();
        user.setUsername("jenius");
        return user;
    }
}
