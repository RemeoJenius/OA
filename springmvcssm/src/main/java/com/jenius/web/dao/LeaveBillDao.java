package com.jenius.web.dao;

import com.jenius.web.meta.workflow.LeaveBill;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by liyongjun on 17/2/24.
 */
public interface LeaveBillDao {
    List<LeaveBill> getLeaveBillList(int id);

    void deleteLeaveBillById(int id);

    void addLeaveBill(@Param("leaveBill") LeaveBill leaveBill);

    void updateLeaveBill(@Param("leaveBill") LeaveBill leaveBill);

    LeaveBill getLeaveBillById(int id);

    void updateLeaveBillStateById(@Param("id") int id,@Param("state") int state);


}
