package com.jenius.web.dao;

import com.jenius.web.meta.Version;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by liyongjun on 17/2/17.
 */
public interface VersionDao {

    void addVersion(@Param("version")Version version);

    List<Version> selectVersionByKid(int kid);

}
