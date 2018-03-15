package com.jenius.web.service.impl;

import com.jenius.web.dao.KynamicDao;
import com.jenius.web.meta.Kynamic;
import com.jenius.web.service.KynamicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liyongjun on 17/2/16.
 */
@Service
public class KynamicServiceImpl implements KynamicService {

    @Resource(name = "kynamicDao")
    private KynamicDao kynamicDao;

    public List<Kynamic> getKynamicAll() {
        return kynamicDao.getKynamicAll();
    }

    public void saveKynamic(Kynamic kynamic) {
        kynamicDao.saveKynamic(kynamic);
    }

    public boolean exsitName(String name) {
        if (kynamicDao.exsitName(name)!=null){
            return true;
        }else{
            return false;
        }

    }

    public void deleteKynamic(int kid) {
        kynamicDao.deleteKynamic(kid);
    }

    public void updateKynamic(Kynamic kynamic) {
        kynamicDao.updateName(kynamic);
    }
}
