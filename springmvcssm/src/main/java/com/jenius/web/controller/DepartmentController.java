package com.jenius.web.controller;


import com.jenius.web.meta.Department;
import com.jenius.web.service.impl.DepartmentServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liyongjun on 17/2/8.
 */

@Controller
public class DepartmentController {

    @Resource
    private DepartmentServiceImpl departmentServiceImpl;

    @RequestMapping("departmentList")
    public @ResponseBody
    List<Department> departmentList(){

        return departmentServiceImpl.getDepartmentList();
    }

    @RequestMapping("deleteDepartment")
    public String deleteDepartment(@RequestParam("Did") int Did){
        System.out.println(Did);
        departmentServiceImpl.deleteDepartmentById(Did);
        return "redirect:/auth/department/list.html";
    }

    @RequestMapping("addDepartment")
    public String addDepartment(@ModelAttribute("department")Department department){
        System.out.println(department);
        departmentServiceImpl.saveDepartment(department);
        return "redirect:/auth/department/list.html";
    }

    @RequestMapping("updateDepartment")
    public String updateDepartment(@ModelAttribute("department")Department department,@RequestParam("id") int id){
        department.setId(id);
        departmentServiceImpl.updateDepartment(department);
        return "redirect:/auth/department/list.html";
    }

    @RequestMapping("getDepartmentById")
    public @ResponseBody Department getDepartmentById(@RequestParam("id") int id){

        return departmentServiceImpl.getDepartmentById(id);
    }
}
