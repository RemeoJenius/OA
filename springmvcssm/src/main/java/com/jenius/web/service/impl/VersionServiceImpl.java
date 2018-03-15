package com.jenius.web.service.impl;

import com.jenius.web.dao.VersionDao;
import com.jenius.web.meta.Version;
import com.jenius.web.service.VersionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liyongjun on 17/2/17.
 */
@Service
public class VersionServiceImpl implements VersionService {

    @Resource(name = "versionDao")
    private VersionDao versionDao;

    public List<Version> selectVersionByKid(int kid) {
        return versionDao.selectVersionByKid(kid);
    }
}
