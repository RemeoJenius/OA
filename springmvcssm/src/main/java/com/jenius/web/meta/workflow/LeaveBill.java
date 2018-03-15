package com.jenius.web.meta.workflow;

import com.jenius.web.meta.User;

import java.util.Date;

/**
 * Created by liyongjun on 17/2/24.
 */
public class LeaveBill {

    private Integer id; // 主键ID
    private Integer days; // 请假天数
    private String content; // 请假内容
    private Date leaveDate = new Date(); // 请假时间
    private String remark; // 备注
    private User user;   // 请假人

    private Integer state = 0; // 请假单状态0初始录入，1开始审批，2为审批完成


    public LeaveBill() {
    }

    public LeaveBill(Integer id, Integer days, String content, String remark, Date leaveDate, Integer state) {
        this.id = id;
        this.days = days;
        this.content = content;
        this.leaveDate = leaveDate;
        this.remark = remark;
        this.state = state;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
