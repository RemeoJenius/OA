package com.jenius.web.controller;

import com.jenius.web.dao.UserDao;
import com.jenius.web.meta.Department;
import com.jenius.web.meta.Post;
import com.jenius.web.meta.User;
import com.jenius.web.service.impl.DepartmentServiceImpl;
import com.jenius.web.service.impl.PostServiceImpl;
import com.jenius.web.service.impl.UserServiceImpl;
import com.jenius.web.utils.GetTaskerSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liyongjun on 17/2/2.
 */
@Controller
public class UserController {

    /**
     *
     */
    @Resource(name = "userServiceImpl")
    private UserServiceImpl userServiceImpl;

    @Resource(name="departmentServiceImpl")
    private DepartmentServiceImpl departmentServiceImpl;

    @Resource(name = "postServiceImpl")
    private PostServiceImpl postServiceImpl;

    private HashMap<String, Object> msgMap;



    @RequestMapping("userList")
    public @ResponseBody
    List<User> userList(){

        return userServiceImpl.getUserList();
    }

    @RequestMapping("getUserManagementList")
    public @ResponseBody
    List<User> getUserManagementList(){

        return userServiceImpl.getUserManagementList();
    }

    @RequestMapping("addUserUI")
    public @ResponseBody
    Map<String, Object> addUserUI(){
        Map<String, Object> map = new HashMap<String, Object>();
        List<Post> posts = postServiceImpl.getPostList();
        List<Department> departments = departmentServiceImpl.getDepartmentList();
        map.put("posts",posts);
        map.put("departments",departments);
        return map;
    }

    @RequestMapping(path = "addUser" ,method ={RequestMethod.POST})
    public @ResponseBody Map<String, Object> addUser(@RequestBody User user) {

        msgMap = new HashMap<String, Object>();
        if(userServiceImpl.isExist(user.getUsername())){
            msgMap.put("message","用户名已存在!");
        }else{
            userServiceImpl.addUser(user);
            msgMap.put("success","success");
        }

        return msgMap;
    }

    @RequestMapping(path = "updateUser" ,method ={RequestMethod.POST})
    public @ResponseBody Map<String, Object> updateUser(@RequestBody User user) {
        userServiceImpl.updateUser(user);
        msgMap = new HashMap<String, Object>();
        if(userServiceImpl.isExist(user.getUsername())){
            msgMap.put("message","用户名已存在!");
        }
        msgMap.put("success","success");
        return msgMap;
    }

    @RequestMapping("getUserById")
    public @ResponseBody User getUser(@RequestParam("id") int id){

        User user = userServiceImpl.getUserById(id);
        return user;
    }

    @RequestMapping("getUser")
    @ResponseBody
    public User getUser(HttpSession session){
        int id = ((User)(session.getAttribute("user"))).getId();
        User user = userServiceImpl.getUserById(id);
        return user;
    }

    @RequestMapping("deleteUser")
    public String deleteUser(@RequestParam("id") int id){

        userServiceImpl.deleteUser(id);
        return "redirect:/auth/user/list.html";
    }
    @RequestMapping("login")
    public @ResponseBody Map<String, Object> login(@RequestParam("rand") String rand,@ModelAttribute("user")User user, HttpSession session){
        HashMap<String, Object> map = userServiceImpl.loginCheck(user);
        String yanzheng = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        User u = (User)map.get("user");
        if(u!=null&&yanzheng.equals(rand)){
            session.setAttribute("user",u);
            GetTaskerSession.session = session;
        } else if(u==null){
            map.put("message","用户或者密码错误!");
            map.put("user",null);

        }else{
            map.put("message","验证码错误!");
            map.put("user",null);
        }
        return map;
    }

    @RequestMapping("getUserByName")
    public @ResponseBody User getUserByName(){
        User user = userServiceImpl.getUserByName("remeo");
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        UserServiceImpl userServiceImpl = webApplicationContext.getBean(UserServiceImpl.class);
        System.out.println(userServiceImpl);
        return user;
    }

    @RequestMapping("logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/";
    }
    /**
     * 修改密码
     */
    @RequestMapping("updatePasswordById")
    @ResponseBody
    public Map<String,Object> updatePassword(HttpSession session,@RequestParam("orldPassword") String orldPassword,@RequestParam("newPassword") String newPassword){

        User user = ((User)(session.getAttribute("user")));
        Map<String,Object> map = new HashMap<String,Object>();
        System.out.println(orldPassword);
        System.out.println(newPassword);
        if(user.getPassword().equals(orldPassword)){
            userServiceImpl.updatePassword(user.getId(),newPassword);
            session.removeAttribute("user");
            map.put("success","success");
            return map;
        }else{
            map.put("message","密码有误！");
            return map;
        }
    }
    @RequestMapping("showUsername")
    @ResponseBody
    public Map<String,Object> showUsername(HttpSession session){
        Map<String,Object> map = new HashMap<String,Object>();
        User user = (User) session.getAttribute("user");
        map.put("username",user.getUsername());
        return map;

    }

}
