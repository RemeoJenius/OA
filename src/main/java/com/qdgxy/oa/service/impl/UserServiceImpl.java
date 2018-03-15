package com.qdgxy.oa.service.impl;

import com.qdgxy.oa.dao.UserDao;
import com.qdgxy.oa.meta.User;
import com.qdgxy.oa.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by liyongjun on 17/2/2.
 */

@Service
public class UserServiceImpl implements UserService{

    @Resource
    private UserDao userDao;


    public User userCheck(User user) {
        return  userDao.userCheck(user);
    }
}
