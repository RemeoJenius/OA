package com.jenius.web.controller;

/**
 * Created by liyongjun on 17/2/18.
 */

import com.jenius.web.utils.ManagerTaskHandler;
import com.jenius.web.utils.SystemRequestContext;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;


@Controller
public class ActivitiController {

    private  ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    /**
     * 通过定义好的流程图文件部署，一次只能部署一个流程
     */

    public void deploy() {
        RepositoryService repositoryService = processEngine.getRepositoryService();// 与流程定义与部署对象相关的service
        Deployment deployment = repositoryService.createDeployment() //创建一个部署对象
                .name("test")
                .addClasspathResource("diagrams/helloworld.bpmn").deploy();// 加载文件并完成部署
        System.out.println("部署ID "+deployment.getId());
        System.out.println("部署名称 "+deployment.getName());
    }
    /**
     * 将多个流程文件打包部署，一次可以部署多个流程
     */
    @RequestMapping("deployByZip")
    public void deployByZip() {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("diagrams/helloworld.zip");
        ZipInputStream zip = new ZipInputStream(is);
        Deployment deployment = processEngine
                .getRepositoryService()
                .createDeployment()
                .name("流程定义")
                .addZipInputStream(zip)
                .deploy();
        System.out.println("部署ID "+deployment.getId());
        System.out.println("部署名称 "+deployment.getName());
    }
    /**
     * 启动流程实例
     */
    @RequestMapping("qidong")
    public void startInstanceByKey(String instanceByKey) {
        RuntimeService runtimeService = processEngine.getRuntimeService();// 与正在执行的流程实例和执行对象x相关的service
        ProcessInstance instance = runtimeService.startProcessInstanceByKey("helloworld");// 使用流程定义的启动流程实例，keyd对应helloword.bpmn文件中的id的属性值：  helloworld
        System.out.println("流程实例ID "+instance.getId());
        System.out.println("流程定义ID "+instance.getProcessDefinitionId());
    }



    /**
     * 完成我的任务
     */
    @RequestMapping("wancheng")
    public void completeMyProcess(){
        String taskId= "27502";
        processEngine.getTaskService() // 与正在执行dde任务管理相关的service
                      .complete(taskId);
        System.out.println("完成任务,任务ID是"+taskId);

    }
    /**
     * 查询流程定义
     */

    @RequestMapping("getSession")
    public void getSession(){
        ManagerTaskHandler managerTaskHandler = new ManagerTaskHandler();

    }
    
}
