package com.qdgxy.oa.dao;

import com.qdgxy.oa.meta.User;
import org.apache.ibatis.annotations.Param;

/**
 * Created by liyongjun on 17/2/2.
 */
public interface UserDao {

    User getUserById();


    User userCheck(@Param("user") User user);
}
