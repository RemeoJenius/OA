package com.jenius.web.dao;

import com.jenius.web.meta.Department;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
 * Created by liyongjun on 17/2/4.
 */
public interface DepartmentDao {

    void saveDepartment (@Param("department") Department department);

    void updateDepartment (@Param("department") Department department);

    void deleteDepartmentById (int id);

    Collection<Department> getAllDepartment();

    Department getDepartmentById (int id);



}
