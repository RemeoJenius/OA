package com.jenius.web.dao;

import com.jenius.web.meta.Kynamic;
import com.jenius.web.meta.Version;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by liyongjun on 17/2/16.
 */
public interface KynamicDao {
    List<Kynamic> getKynamicAll();

    void saveKynamic(@Param("kynamic") Kynamic kynamic);

    Kynamic exsitName(String name);

    void deleteKynamic(int kid);

    void updateName(@Param("kynamic") Kynamic kynamic);

    List<Version> getVersionByKid(int kid);
}
