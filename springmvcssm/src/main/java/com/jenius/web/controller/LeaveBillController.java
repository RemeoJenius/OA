package com.jenius.web.controller;

import com.jenius.web.meta.User;
import com.jenius.web.meta.workflow.LeaveBill;
import com.jenius.web.service.impl.LeaveBillServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liyongjun on 17/2/25.
 */
@Controller
public class LeaveBillController {

    @Resource(name = "leaveBillServiceImpl")
    private LeaveBillServiceImpl leaveBillServiceImpl;

    @RequestMapping("leaveBillList")
    public @ResponseBody
    List<LeaveBill> getLeaveBillList(HttpSession session){
        int id = ((User)session.getAttribute("user")).getId();

        return leaveBillServiceImpl.getLeaveBillList(id);
    }

    @RequestMapping("deleteLeaveBill/{id}")
    public String  deleteLeaveBillById(@PathVariable int id){
        leaveBillServiceImpl.deleteLeaveBillById(id);
        return "redirect:/auth/workflow/leaveManagement.html";

    }

    @RequestMapping("addLeaveBill")
    public @ResponseBody
    Map<String,Object> addLeaveBill(@ModelAttribute LeaveBill leaveBill, HttpSession session){
        HashMap<String ,Object> map = new HashMap<String, Object>();
        User user = (User) session.getAttribute("user");
        leaveBill.setUser(user);
        leaveBillServiceImpl.addLeaveBill(leaveBill);
        map.put("msg","success");
        return map;

    }

    @RequestMapping("updateLeaveBill/{id}")
    public @ResponseBody
    Map<String,Object> updateLeaveBill(@ModelAttribute LeaveBill leaveBill,@PathVariable int id){
        HashMap<String ,Object> map = new HashMap<String, Object>();
        leaveBillServiceImpl.updateLeaveBill(leaveBill);
        map.put("msg","success");
        return map;

    }

    /**
     * 获取leavebill表中id 返回leavebill对象
     * @param id
     * @return
     */
    @RequestMapping("getLeaveBill/{id}")
    public @ResponseBody
    LeaveBill getLeaveBill(@PathVariable int id){

        return leaveBillServiceImpl.getLeaveBillById(id);

    }

    /**
     * 获取任务id返回leavebill对象
     */
    @RequestMapping("findLeaveBill/{id}")
    public @ResponseBody
    LeaveBill findLeaveBill(@PathVariable String id){

        return leaveBillServiceImpl.findLeaveBillById(id);

    }

    /**
     * 删除已完成的任务
     *
     * @return
     */
    @RequestMapping("deleteCompletedLeaveBillById/{id}")
    public @ResponseBody
    Map<String,Object> deleteCompletedLeaveBillById(@PathVariable("id")int id){

       Map<String,Object> map = new HashMap<String,Object>();
       leaveBillServiceImpl.deleteLeaveBillById(id);
       map.put("success","success");
       return map;
    }


}
