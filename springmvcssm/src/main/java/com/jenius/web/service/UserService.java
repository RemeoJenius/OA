package com.jenius.web.service;

import com.jenius.web.meta.Department;
import com.jenius.web.meta.User;

import java.util.HashMap;
import java.util.List;

/**
 * Created by liyongjun on 17/2/3.
 */
public interface UserService {
    List<User> getUserList();

    List<User> getUserManagementList();

    void addUser(User user);

    void updateUser(User user);

    User getUserById(int id);

    void deleteUser(int id);

    boolean isExist(String username);

    HashMap<String,Object> loginCheck(User user);

    User getUserByName(String name);

    void updatePassword(int id, String newPassword);
}
