package com.jenius.web.controller;

import com.jenius.web.meta.Kynamic;
import com.jenius.web.service.impl.KynamicServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liyongjun on 17/2/16.
 */
@Controller
public class KynamicController {



    @Resource(name = "kynamicServiceImpl")
    private KynamicServiceImpl kynamicServiceImpl;

    @RequestMapping("showKynamicTree")
    public @ResponseBody
    List<Kynamic> getKynamicAll(){
        return kynamicServiceImpl.getKynamicAll();
    }

    @RequestMapping("saveKynamic")
    public @ResponseBody
    Map<String, Object> saveKynamic(@ModelAttribute("kunamic")Kynamic kynamic){
        HashMap<String, Object> map = new HashMap<String, Object>();
        kynamicServiceImpl.saveKynamic(kynamic);
        map.put("message","操作成功！");
        map.put("kid",kynamic.getKid());
        return map;
    }

    @RequestMapping("exsitName")
    public @ResponseBody Map<String,Object> exsitName(@RequestParam("name")String name){
        HashMap<String, Object> map = new HashMap<String, Object>();
        if(kynamicServiceImpl.exsitName(name)){

            map.put("message","1");
        }

        return map;
    }

    @RequestMapping("deleteKynamic")
    public @ResponseBody  Map<String, Object> deleteKynamic(@RequestParam("kid")int kid){
        HashMap<String, Object> map = new HashMap<String, Object>();
        try {
            kynamicServiceImpl.deleteKynamic(kid);
            map.put("message","删除成功!");
        }catch (Exception e){
            map.put("message","删除失败!");
        }
        return map;
    }

    @RequestMapping("updateKynamic")
    public @ResponseBody Map<String, Object> updateKynamic(@ModelAttribute("kynamic")Kynamic kynamic){
        HashMap<String, Object> map = new HashMap<String, Object>();
        System.out.println(kynamic);
        kynamicServiceImpl.updateKynamic(kynamic);
        map.put("message","修改成功");
        return map;
    }
}
