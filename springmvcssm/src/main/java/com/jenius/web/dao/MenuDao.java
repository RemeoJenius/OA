package com.jenius.web.dao;

import com.jenius.web.meta.Menu;
import com.jenius.web.meta.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by liyongjun on 17/2/12.
 */
public interface MenuDao {

    List<Menu> getMenuList();

    List<Menu> getMenuListByPid(int pid );

    void addMid(@Param("uid") int uid, @Param("mid") int mid);

    User getMenuByUid(int uid);

    List<Menu> getLeftMenuByUid(int uid);

    List<Integer> getMidsByUid(int uid);

    void deleteMidAllByUid(int uid);
}
