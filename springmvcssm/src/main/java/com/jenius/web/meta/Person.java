package com.jenius.web.meta;

import java.io.Serializable;

/**
 * Created by liyongjun on 17/2/20.
 */
public class Person implements Serializable{


    private static final long serialVersionUID = 8370356650320368120L;
    private Integer id;
    private String name;
    private int day;
    private String reason;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
