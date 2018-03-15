package com.jenius.web.service.impl;

import com.jenius.web.dao.MenuDao;
import com.jenius.web.meta.Menu;
import com.jenius.web.meta.User;
import com.jenius.web.service.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liyongjun on 17/2/12.
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Resource(name = "menuDao")
    private MenuDao menuDao;

    public List<Menu> getMenuList() {

        return menuDao.getMenuList();
    }

    public List<Menu> getMenuListByPid(int pid) {
        return menuDao.getMenuListByPid(pid);
    }

    public void addMid(int uid, int[] mids) {
        for (int mid:mids){
            menuDao.addMid(uid,mid);
        }

    }

    public User getMenuByUid(int uid) {

        return menuDao.getMenuByUid(uid);
    }

    public List<Menu> getLeftMenuByUid(int uid) {
        return menuDao.getLeftMenuByUid(uid);
    }

    public List<Integer> midsByUid(int uid) {

        return menuDao.getMidsByUid(uid);
    }

    public void deleteAllByUid(int uid) {
        menuDao.deleteMidAllByUid(uid);
    }
}
