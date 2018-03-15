package com.jenius.web.utils;

import com.jenius.web.dao.UserDao;
import com.jenius.web.meta.User;
import com.jenius.web.service.impl.UserServiceImpl;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.ejb.SessionContext;
import javax.servlet.http.HttpSession;

/**
 * Created by liyongjun on 17/2/26.
 */
public class ManagerTaskHandler implements TaskListener{

    public void notify(DelegateTask delegateTask) {

        // 从session中获取当前用户
        HttpSession session = GetTaskerSession.session;
        System.out.println(session);
        User user = (User) session.getAttribute("user");
        String assignee = user.getUsername();

        // 使用当前用户名查询用户的详细信息
        // 从web中获取spring容器
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        UserServiceImpl userServiceImpl = webApplicationContext.getBean(UserServiceImpl.class);
        User u = userServiceImpl.getUserByName(user.getUsername());
        // 将当前用户的领导放置在办理人中
        // 设置任务的办理人
        delegateTask.setAssignee(u.getManager().getUsername());
    }
}
