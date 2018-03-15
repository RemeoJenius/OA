package com.jenius.web.controller;

import com.jenius.web.meta.Menu;
import com.jenius.web.meta.User;
import com.jenius.web.service.impl.MenuServiceImpl;
import com.jenius.web.service.impl.UserServiceImpl;
import javafx.geometry.Pos;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liyongjun on 17/2/12.
 */
@Controller
public class MenuController {

    @Resource(name = "menuServiceImpl")
    private MenuServiceImpl menuServiceImpl;

    @Resource(name = "userServiceImpl")
    private UserServiceImpl userServiceImpl;

    @RequestMapping(path = "getMenuList")
    public @ResponseBody
    List<Menu> getMenuList(){
        return menuServiceImpl.getMenuList();
    }

    /**
     * 点击记载树根据点击父节点
     * @param pid
     * @return
     */
    @RequestMapping(path = "getMenuListById")
    public @ResponseBody
    List<Menu> getMenuListBuyPid(@RequestParam("pid") int pid){
        return menuServiceImpl.getMenuListByPid(pid);
    }

    @RequestMapping(path = "savePrivilegeMid",method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> savePrivilege(@RequestParam("mids")int [] mids, @RequestParam("uid")int uid){
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("SUCCESS","success");
        menuServiceImpl.deleteAllByUid(uid);
        menuServiceImpl.addMid(uid,mids);
        return map;
    }

    /**
     * 如果是admin则把所有的菜单的checked设置为true
     * 如果不是admin，则先遍历所有的菜单项，再遍历用户能访问的菜单项，然后把所有的菜单项中，用户能够访问的checked设置为true
     * @param uid
     * @return
     */
    @RequestMapping(path = "getMenuByUId/{uid}")
    public @ResponseBody
    List<Menu> getMenuBuyUid(@PathVariable("uid") int uid){
        List<Menu> menus = this.getMenuList();

        User user = menuServiceImpl.getMenuByUid(uid);
        if(user != null){
            if("admin".equals(user.getUsername())){
                for (Menu menu:menus){
                    menu.setChecked(true);
                }
            }else{
                for (Menu menu:menus){
                    for(Menu menu2:user.getMenus()){
                        if(menu.getMid() == menu2.getMid()){
                            menu.setChecked(true);
                        }
                    }
                }
            }
        }
        return menus;
    }

    @RequestMapping(path = "getLeftMenuByUid")
    public @ResponseBody
    List<Menu> getLeftMenuByUid(HttpSession session){
        User user = (User) session.getAttribute("user");
        return menuServiceImpl.getLeftMenuByUid(user.getId());
    }

}
