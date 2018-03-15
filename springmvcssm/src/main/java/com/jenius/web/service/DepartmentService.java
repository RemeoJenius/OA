package com.jenius.web.service;

import com.jenius.web.meta.Department;

import java.util.List;

/**
 * Created by liyongjun on 17/2/8.
 */
public interface DepartmentService {

    List<Department> getDepartmentList();

    void deleteDepartmentById(int id);

    void saveDepartment(Department department);

    void updateDepartment(Department department);

    Department getDepartmentById(int id);

}
