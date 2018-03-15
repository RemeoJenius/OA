package com.jenius.web.service;

import com.jenius.web.meta.Version;

import java.util.List;

/**
 * Created by liyongjun on 17/2/17.
 */
public interface VersionService {

    List<Version> selectVersionByKid(int kid);
}
