package com.jenius.test;

import com.jenius.web.meta.Person;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.*;
import org.junit.Test;

import java.io.InputStream;
import java.util.*;
import java.util.zip.ZipInputStream;

/**
 * Created by liyongjun on 17/2/20.
 */
public class ProcessVariablesTest {
    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    @org.junit.Test
    public void deploy() {
        InputStream inputStream = this.getClass().getResourceAsStream("/diagrams/sequenceFlow.bpmn");
        RepositoryService repositoryService = processEngine.getRepositoryService();// 与流程定义与部署对象相关的service
        Deployment deployment = repositoryService.createDeployment() //创建一个部署对象
                .name("sequenceFlow连线")
                .addInputStream("sequenceFlow.bpmn",inputStream) // 使用资源文件的名称（要与资源文件的名称要一致），和输入流完成部署
                .deploy();// 加载文件并完成部署
        System.out.println("部署ID "+deployment.getId());
        System.out.println("部署名称 "+deployment.getName());
    }
    /**
     * 从InputStream部署
     */
    public void deployByZip() {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("diagrams/bpm.zip");
        ZipInputStream zip = new ZipInputStream(is);
        Deployment deployment = processEngine
                .getRepositoryService()
                .createDeployment()
                .addZipInputStream(zip)
                .deploy();
    }

    /**
     * 启动流程
     * @param
     */
    @org.junit.Test
    public void startInstanceByKey() {
        RuntimeService runtimeService = processEngine.getRuntimeService();// 与正在执行的流程实例和执行对象x相关的service
        ProcessInstance instance = runtimeService.startProcessInstanceByKey("sequenceFlow");// 使用流程定义的启动流程实例，key对应helloword.bpmn文件中的id的属性值：  helloworld
        System.out.println("流程实例ID "+instance.getId());
        System.out.println("流程定义ID "+instance.getProcessDefinitionId());
    }

    /**
     * 根据班里人查找个人任务
     */
    @Test
    public void findMyProcessTask(){
        String assignee = "田七";
        List<Task> list = processEngine.getTaskService()
                .createTaskQuery()
                .taskAssignee(assignee)
                .list();
        if(list!=null && list.size()>0){
            for(Task t : list){
                System.out.println(t.getId());
                System.out.println(t.getName());
            }
        }

    }

    /**
     * 设置流程变量
     */
    @org.junit.Test
    public void setProcessVariables(){
        // 与任务（正在执行）
        TaskService taskService = processEngine.getTaskService();
        // 任务ID
        String taskId = "92504";
        // 一、设置流程变量，基本类型
//        taskService.setVariableLocal(taskId,"请假天数",3); // 与当前任务ID绑定
//        taskService.setVariable(taskId,"请假日期",new Date());
//        taskService.setVariable(taskId,"请假原因","回家探亲");
        // 二、设置流程变量，使用java bean类型
        /**
         * 当一个java bean(实现序列化）放置到流程变量中,要求javabeande属性不能在发生变化
         * 如果发生变化，再获取的时候，会抛出异常
         * 解决方案：在Person中添加序列化ID：
         * private static final long serialVersionUID = 8370356650320368120L;
         * 同时实现   Serializable 接口
         */


        Person person = new Person();
        person.setId(10);
        person.setName("姜晓琳");
        taskService.setVariableLocal(taskId,"人员信息",person); // 与当前任务ID绑定

        System.out.println("设置流程变量成功");
    }


    /**
     * 获取流程变量
     */
    @Test
    public void getProcessVariables(){
        // 与任务（正在执行）
        TaskService taskService = processEngine.getTaskService();
        // 任务ID
        String taskId = "92504";
        // 一、设置流程变量，基本类型
//        Integer day = (Integer) taskService.getVariable(taskId,"请假天数");
//        Date date = (Date) taskService.getVariable(taskId,"请假日期");
//        String resonce = (String) taskService.getVariable(taskId,"请假原因");
//        System.out.println(day);
//        System.out.println(date);
//        System.out.println(resonce);
        // 二、设置流程变量，java bean类型
        Person p = (Person) taskService.getVariable(taskId,"人员信息");
        System.out.println(p.getId());
        System.out.println(p.getName());

    }

    /**
     * 模拟设置和获取流程变量的场景
     */
    public void setAndGetVariables(){
        // 与流程实例，执行对象（正在执行）
        RuntimeService runtimeService = processEngine.getRuntimeService();
        // 与任务（正在执行）
        TaskService taskService = processEngine.getTaskService();

        // 设置流程变量
//        runtimeService.setVariable(executionId,variableName,value); // 表示使用执行对象ID，和流程变量的名称，设置流程变量的值（一次只能设置一次值）
        //runtimeService.setVariables(executionId,variables);// 表示使用执行对象ID，和map集合设置流程变量，map集合的key就是流程变量的名字，map集合的value就是流程变量的值（一次可以设置多个）

        // 设置流程变量
//        taskService.setVariable(taskId,variableName,value); // 表示任务ID，和流程变量的名称，设置流程变量的值（一次只能设置一次值）
        //taskService.setVariables(taskId,variables);// 表示任务ID，和map集合设置流程变量，map集合的key就是流程变量的名字，map集合的value就是流程变量的值（一次可以设置多个）
        //runtimeService.startProcessInstanceByKey("processDefinitionKey",new HashMap<String,Object>()); // 启动流程实例的同时，可以设置流程变量，map集合
        //taskService.complete(taskId,variables); // 完成任务的的同时设置流程变量 map集合

        // 获取流程变量
        // runtimeService.getVariable(executionId,variableName); // 使用执行对象ID和流程变量的名称，获取流程变量的值

        // runtimeService.getVariables(executionId); //使用执行对象id，获取所有的流程变量，将流程变量放在map集合中
        // runtimeService.getVariablesLocal(executionId,variableNames); // 使用执行对象的id，获取流程变量的值，通过设置流程变量的名称存放在集合中，获取指定流程变量名称的流程变量的值，值存放在map集合中


        // taskService.getVariable(taskId,variableName); // 使用任务ID和流程变量的名称，获取流程变量的值

        // taskService.getVariables(taskId); //使用任务id，获取所有的流程变量，将流程变量放在map集合中
        // taskService.getVariablesLocal(taskId,variableNames); // 使用任务的id，获取流程变量的值，通过设置流程变量的名称存放在集合中，获取指定流程变量名称的流程变量的值，值存放在map集合中

    }
    @Test
    public void completeMyProcess(){
        String taskId= "115003";
        // 完成任务的同时，设置流程变量，使用流程变量用来指定完成任务后，下一个连线，对应sequenceFlow.bpmn这个文件中的#{message=='不重要'这个表达式}
        Map<String,Object> variables = new HashMap<String, Object>();
        variables.put("message","重要");
        processEngine.getTaskService() // 与正在执行dde任务管理相关的service
                .complete(taskId,variables);
        System.out.println("完成任务,任务ID是"+taskId);

    }

    /**
     * 查询流程变量的历史表
     */
    @Test
    public void processHistoryVariable(){
        List<HistoricVariableInstance> list = processEngine.getHistoryService()
                .createHistoricVariableInstanceQuery() // 创建一个历史流程变量实例查询对象
                .variableName("请假天数")
                .list();

        if(list!=null && list.size()>0){
            for (HistoricVariableInstance hvi : list){
                System.out.println(hvi.getId());
                System.out.println(hvi.getVariableName());
                System.out.println(hvi.getValue());
                System.out.println("------------------------");
            }
        }
    }

    /**
     * 查询历史流程实例
     */
    @Test
    public void queryHistoryProcess(){
        List<HistoricProcessInstance> list = processEngine.getHistoryService()
                .createHistoricProcessInstanceQuery()
                .list();
        if(list!=null && list.size()>0){
            for (HistoricProcessInstance hpi : list){
                System.out.println(hpi.getId());
            }
        }
    }

    /**
     * 查询历史流程实例
     */
    @Test
    public void queryHistoryProcessInstanceId(){
        String processInstanceId ="82501";
        HistoricProcessInstance historicProcessInstance= processEngine.getHistoryService()
                .createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        System.out.println(historicProcessInstance.getProcessDefinitionName());

    }

    /**
     * 查询历史任务
     */
    @Test
    public void findHistoryTask(){
        String assignee = "李永军";
        List<HistoricTaskInstance> list  = processEngine.getHistoryService()
                .createHistoricTaskInstanceQuery()
                .taskAssignee(assignee)
                .list();
        if(list!=null && list.size()>0){
            for (HistoricTaskInstance hti : list){
                System.out.println(hti.getName());
            }
        }
    }


}
