package com.jenius.web.service.impl;

import com.jenius.web.dao.LeaveBillDao;
import com.jenius.web.meta.workflow.LeaveBill;
import com.jenius.web.service.LeaveBillService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liyongjun on 17/2/24.
 */
@Service
public class LeaveBillServiceImpl implements LeaveBillService {

    @Resource(name = "leaveBillDao")
    private LeaveBillDao leaveBillDao;

    @Resource(name = "workflowServiceImpl")
    private WorkflowServiceImpl workflowServiceImpl;

    public List<LeaveBill> getLeaveBillList(int id) {

        return leaveBillDao.getLeaveBillList(id);
    }

    public void deleteLeaveBillById(int id) {
        leaveBillDao.deleteLeaveBillById(id);
    }

    public void addLeaveBill(LeaveBill leaveBill) {
        leaveBillDao.addLeaveBill(leaveBill);
    }

    public void updateLeaveBill(LeaveBill leaveBill) {
        leaveBillDao.updateLeaveBill(leaveBill);
    }

    public LeaveBill getLeaveBillById(int id) {
        return leaveBillDao.getLeaveBillById(id);
    }

    public LeaveBill findLeaveBillById(String taskId) {

        return workflowServiceImpl.findLeaveBillByTaskId(taskId);
    }
}
