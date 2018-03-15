package com.jenius.web.service.impl;

import com.jenius.web.dao.UserDao;
import com.jenius.web.meta.Post;
import com.jenius.web.meta.User;
import com.jenius.web.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by liyongjun on 17/2/3.
 */
@Service
public class UserServiceImpl implements UserService{

    @Resource
    private UserDao userDao;

    public List<User> getUserList() {

        return userDao.getUserList();
    }

    public List<User> getUserManagementList() {

        return userDao.getUserManagementList();
    }

    public void addUser(User user) {
        if(user.getPassword()==null){user.setPassword("1234");}
        userDao.addUser(user);
        if(user.getPosts()!=null){
            Iterator iterator = user.getPosts().iterator();

            while (iterator.hasNext()){
                userDao.addUserPost(user.getId(),((Post)(iterator.next())).getId());
            }
        }




    }

    public void updateUser(User user) {
        userDao.updateUser(user);
        if(user.getPosts()!=null){
            Iterator iterator = user.getPosts().iterator();
            userDao.deleteUserPostBuyUserId(user.getId());
            while (iterator.hasNext()){
                userDao.addUserPost(user.getId(),((Post)(iterator.next())).getId());
            }
        }
    }

    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    public void deleteUser(int id) {
        userDao.deleteUser(id);
        userDao.deleteUserPostBuyUserId(id);
    }

    public boolean isExist(String username) {
        if(userDao.isUser(username)!= null){
            return true;
        }
        return false;
    }

    public HashMap<String,Object> loginCheck(User user) {
        HashMap<String,Object> map = new HashMap<String, Object>();
        User u = userDao.loginCheck(user);
        if(u != null){
            map.put("user",u);

            map.put("message","success");
        }else{
            map.put("message","用户名或密码不正确！");
        }
        return map;
    }

    public User getUserByName(String name) {

        return userDao.getUserByUsername(name);

    }

    public void updatePassword(int id, String newPassword) {
        userDao.updatePasswordById(id,newPassword);
    }


}
