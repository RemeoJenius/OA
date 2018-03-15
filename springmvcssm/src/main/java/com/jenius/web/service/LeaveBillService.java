package com.jenius.web.service;

import com.jenius.web.meta.workflow.LeaveBill;

import java.util.List;

/**
 * Created by liyongjun on 17/2/24.
 */
public interface LeaveBillService {

    List<LeaveBill> getLeaveBillList(int id);

    void deleteLeaveBillById(int id);

    void addLeaveBill(LeaveBill leaveBill);

    void updateLeaveBill(LeaveBill leaveBill);


    LeaveBill getLeaveBillById(int id);

    LeaveBill findLeaveBillById(String taskId);
}
