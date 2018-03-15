package com.jenius.web.dao;

import com.jenius.web.meta.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by liyongjun on 17/2/2.
 */
public interface UserDao {

    List<User> getUserList();

    List<User> getUserManagementList();

    void addUser(@Param("user")User user);

    void addUserPost(@Param("uid") int uid, @Param("pid")int pid);

    void updateUser(@Param("user") User user);

    User getUserById(int id);

    void deleteUserPostBuyUserId(int uid);

    void deleteUser(int id);

    User getUserByUsername(String username);

    User loginCheck(@Param("user") User user);

    User isUser(@Param("username") String username);

    void updatePasswordById(@Param("id") int id,@Param("password") String password);
}
