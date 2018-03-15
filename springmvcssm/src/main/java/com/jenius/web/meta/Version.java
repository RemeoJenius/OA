package com.jenius.web.meta;

import java.util.Date;

/**
 * Created by liyongjun on 17/2/14.
 */
public class Version {
    private int vid;
    private int version; //版本号
    private Date updateTime;
    private String title;
    private String content;

    private Kynamic kynamic;

    public Version() {
    }

    public Version(Integer vid, Integer version, Date updateTime, String title, String content) {
        this.vid = vid;
        this.version = version;
        this.updateTime = updateTime;
        this.title = title;
        this.content = content;
    }

    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Kynamic getKynamic() {
        return kynamic;
    }

    public void setKynamic(Kynamic kynamic) {
        this.kynamic = kynamic;
    }
}
