package com.jenius.web.controller;

import com.jenius.web.meta.Version;
import com.jenius.web.service.impl.VersionServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liyongjun on 17/2/17.
 */
@Controller
public class VersionController {

    @Resource(name = "versionServiceImpl")
    private VersionServiceImpl versionServiceImpl;

    @RequestMapping("getVersionByKid")
    public @ResponseBody List<Version> getVersionByKid(@RequestParam("kid")int kid){

        return versionServiceImpl.selectVersionByKid(kid);
    }
}
