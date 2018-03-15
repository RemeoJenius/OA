package com.jenius.web.service.impl;

import com.jenius.web.dao.DepartmentDao;
import com.jenius.web.meta.Department;
import com.jenius.web.service.DepartmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liyongjun on 17/2/8.
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Resource
    private DepartmentDao departmentDao;

    public List<Department> getDepartmentList() {
        return (List<Department>) departmentDao.getAllDepartment();
    }

    public void deleteDepartmentById(int id) {
        departmentDao.deleteDepartmentById(id);
    }

    public void saveDepartment(Department department) {
        departmentDao.saveDepartment(department);
    }

    public void updateDepartment(Department department) {
        departmentDao.updateDepartment(department);
    }

    public Department getDepartmentById(int id) {
        return departmentDao.getDepartmentById(id);
    }
}
