package com.jenius.test;

import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FileUtils;
import org.junit.experimental.theories.Theory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

/**
 * Created by liyongjun on 17/2/18.
 */
public class Test {

    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    /**
     * 通过定义好的流程图文件部署，一次只能部署一个流程
     */
    @org.junit.Test
    public void deploxy() {

        RepositoryService repositoryService = processEngine.getRepositoryService();// 与流程定义与部署对象相关的service
        Deployment deployment = repositoryService.createDeployment() //创建一个部署对象
                .name("leaveBill")
                .addClasspathResource("diagrams/leaveBill3.bpmn").deploy();// 加载文件并完成部署
        System.out.println("部署ID "+deployment.getId());
        System.out.println("部署名称 "+deployment.getName());
    }
    /**
     * 将多个流程文件打包部署，一次可以部署多个流程
     */
    @org.junit.Test
    public void deployByZip() {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("diagrams/helloworld.zip");
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
        ProcessInstance instance = runtimeService.startProcessInstanceByKey("test");// 使用流程定义的启动流程实例，key对应helloword.bpmn文件中的id的属性值：  helloworld
        System.out.println("流程实例ID "+instance.getId());
        System.out.println("流程定义ID "+instance.getProcessDefinitionId());
    }

    @org.junit.Test
    public void findMyProcess(){
        TaskService taskService = processEngine.getTaskService();// 与正在执行dde任务管理相关的service
        String assignee = "jenius";
        List<Task> taskList = taskService.createTaskQuery()      // 创建任务查询对象
                .taskAssignee(assignee).list();// 指定个人任务查询，指定办理人
        if(taskList!=null && taskList.size()>0){
            for (Task task:taskList){
                System.out.println(task.getAssignee());
                System.out.println(task.getName());
                System.out.println(task.getId());
                System.out.println("-----------------------");
            }
        }
    }

    @org.junit.Test
    public void completeMyProcess(){
        String taskId= "335011";
        Map<String,Object> map = new HashMap<String ,Object>();
        map.put("outcome","批准");
        processEngine.getTaskService() // 与正在执行dde任务管理相关的service
                .complete(taskId,map);
        System.out.println("完成任务,任务ID是"+taskId);

    }

    /**
     * 查询流程定义
     */
    @org.junit.Test
    public void findProcessDefinition(){
        List<ProcessDefinition> list =  processEngine.getRepositoryService() // 与流程定义与部署对象相关的service
                                         .createProcessDefinitionQuery() // 创建一个流程定义的查询
                            // 指定查询条件,where条件
                                         //.deploymentId(deploymentId) // 使用部署对象ID查询
                                         //.processDefinitionId(processDefinitionId) //使用流程定义ID查询
                                         //.processDefinitionKey(processDefinitionKey) //使用流程定义 key查询
                                         //.processDefinitionName(processDefinitionName) //使用流程定义名称查询
                                         //.processDefinitionNameLike(processDefinitionNameLike)  //使用流程定义名称模糊查询
                        // 排序
                                         .orderByProcessDefinitionVersion().asc() //按照流程定义版本生序排列
                        // 返回结果集
                                        //.listPage(firstResult,maxResults) //分页查询
                                        //.count(); // 返回结果数量
                                        //.singleResult(); // 返回唯一结果集
                                        .list();  // 返回一个集合列表，封装流程定义

        if(list!=null && list.size()>0){
            for (ProcessDefinition pd : list){
                System.out.println("流程定义ID"+pd.getId());// 流程定义的key+版本_随机生成书
                System.out.println("流程定义的名称:"+pd.getName()); // 对应.bpmn中的name属性值
                System.out.println("流程定义key:"+pd.getKey());     // 对应.bpmn中的id属性值
                System.out.println("流程定义版本"+pd.getVersion()); // 当流程定义的key值相同时，版本升级默认从1开始
                System.out.println("资源名称bbpmn文件："+pd.getResourceName());
                System.out.println("资源名称png文件:"+pd.getDiagramResourceName());
                System.out.println("部署对象ID:"+pd.getDeploymentId());
                System.out.println("------------------------------------------");
            }
        }


    }
    @org.junit.Test
    public void deleteProcessDefinition(){
        // 使用部署Id完成删除
        String deploymentId ="155006";
        /**
         * 不带级联的删除
         * 只能删除没有启动的流程，如果流程启动将抛出异常
         */
       // processEngine.getRepositoryService()
                             // .deleteDeployment(deploymentId);
        /**
         * 级联删除
         * 不管流程是否启动都将删除、
         *
         */
        processEngine.getRepositoryService()
                .deleteDeployment(deploymentId,true);


        System.out.println("删除成功!");
    }

    /**
     * 查看流程图
     *
     */
    @org.junit.Test
    public void viewPic(){
        // 将生成的图片放在文件夹下
        String deploymentId = "37501";
        // 获取图片资源名称
        String resourceName = "";
        List<String> list = processEngine.getRepositoryService()
                .getDeploymentResourceNames(deploymentId);

        if(list!=null && list.size()>0){
            for (String name : list){
                if(name.indexOf(".png")>0){
                    resourceName = name;
                }
            }
        }

        //获取图片输入流
        InputStream in = processEngine.getRepositoryService() //
                    .getResourceAsStream(deploymentId,resourceName);
        //将图片生成
        File file = new File("/Volumes/ziyan/image/"+resourceName);
        // 将输入流的图片写入
        try {
            FileUtils.copyInputStreamToFile(in,file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 附加功能：查询最新版本de流程定义
     */
    @org.junit.Test
    public void findLastVersionProcessDefinition(){
        Map<String, ProcessDefinition> lastVersionProcessDefinition = new HashMap<String, ProcessDefinition>();
        List<ProcessDefinition> list = processEngine.getRepositoryService()
                                            .createProcessDefinitionQuery()
                                            .orderByProcessDefinitionVersion()
                                            .asc()
                                            .list();
        if(list!=null && list.size()>0){
            for (ProcessDefinition pd : list){
                System.out.println(pd.getKey());
                System.out.println(pd.getVersion());
                lastVersionProcessDefinition.put(pd.getKey(),pd);

            }
        }
        for(ProcessDefinition pd : lastVersionProcessDefinition.values()){
            System.out.println(pd.getKey());
            System.out.println(pd.getVersion());
            System.out.println("=============");
        }
    }

    /**
     * 附加功能：删除流程定义(删除key相同的所有不同版本的流程定义）
     */
    @org.junit.Test
    public void deleteProcessDefinitionByKey(){
        // 流程定义的key
        String processDefinitionKey = "LeaveBill";
        // 先使用流程定义的key查询流程定义，查询出所有的版本
        List<ProcessDefinition> processDefinitions = processEngine.getRepositoryService().createProcessDefinitionQuery()
                .processDefinitionKey(processDefinitionKey) // 使用流程定义的key查询
                .list();
        // 遍历获取每个流程定义部署的ID
        if(processDefinitions!=null && processDefinitions.size()>0){
            for (ProcessDefinition pd : processDefinitions){
                String deploymentId= pd.getDeploymentId();
                processEngine.getRepositoryService()
                        .deleteDeployment(deploymentId,true);
            }
        }

    }

    /**
     * 查询流程状态（判断流程正在执行，还是结束）
     */
    @org.junit.Test
    public void isProcessStarOrEnd(){
        String processInstanceId = "60001";
        ProcessInstance processInstance = processEngine.getRuntimeService() // 正在执行的流程实例和实例对象
                .createProcessInstanceQuery() // 创建一个流程实例查询
                .processInstanceId(processInstanceId) // 使用流程实例ID
                .singleResult();
        if( processInstance == null){
            System.out.println("流程已结束");
        }else{
            System.out.println("流程正在进行");
        }
    }
    /**
     * 查询历史任务
     */
    @org.junit.Test
    public void findHistoryTask(){
        String Assignee = "张三";
        List<HistoricTaskInstance> list = processEngine.getHistoryService() // 与历史数据（历史表）相关的service
                .createHistoricTaskInstanceQuery() // 创建历史任务实例查询
                .taskAssignee(Assignee).list();    // 指定历史任务的办理人
        if(list!=null && list.size()>0){
            for (HistoricTaskInstance hti :list){
                System.out.println(hti.getId());
            }
        }

    }

    /**
     * 查询历史流程实例
     */
    @org.junit.Test
    public void findHistoryProcessInstance(){
        String processInstanceId = "60001";
        HistoricProcessInstance hpi = processEngine.getHistoryService() // 与历史数据（历史表）相关的service
                .createHistoricProcessInstanceQuery() // 创建历史流程实例查询
                .processInstanceId(processInstanceId) // 使用流程实例Id查询
                .singleResult();
        System.out.println(hpi.getId());
        System.out.println(hpi.getStartActivityId());
        System.out.println(hpi.getDurationInMillis());
    }

    @org.junit.Test
    public void helloMap(){


    }



}
