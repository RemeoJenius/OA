package com.jenius.web.service;

import com.jenius.web.meta.Kynamic;

import java.util.List;

/**
 * Created by liyongjun on 17/2/16.
 */
public interface KynamicService {

    List<Kynamic> getKynamicAll();

    void saveKynamic(Kynamic kynamic);

    boolean exsitName(String name);

    void deleteKynamic(int kid);

    void updateKynamic(Kynamic kynamic);
}
