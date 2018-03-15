package com.jenius.web.service;

import com.jenius.web.meta.Menu;
import com.jenius.web.meta.User;

import java.util.List;

/**
 * Created by liyongjun on 17/2/12.
 */
public interface MenuService {
    List<Menu> getMenuList();

    List<Menu> getMenuListByPid(int pid);

    void addMid(int uid,int[] mids);

    User getMenuByUid(int uid);

    List<Menu> getLeftMenuByUid(int uid);

    List<Integer> midsByUid(int uid);

    void deleteAllByUid(int uid);
}
