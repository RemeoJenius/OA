package com.jenius.web.service.impl;

import com.jenius.web.dao.LeaveBillDao;
import com.jenius.web.meta.User;
import com.jenius.web.meta.workflow.LeaveBill;
import com.jenius.web.meta.workflow.Rect;
import com.jenius.web.service.WorkflowService;
import com.jenius.web.utils.GetTaskerSession;
import org.activiti.bpmn.model.Activity;
import org.activiti.engine.*;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

/**
 * Created by liyongjun on 17/2/21.
 */
@Service
public class WorkflowServiceImpl implements WorkflowService {

    @Resource(name = "leaveBillDao")
    private LeaveBillDao leaveBillDao;

    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    private RepositoryService repositoryService = processEngine.getRepositoryService();
    private TaskService taskService = processEngine.getTaskService();
    private RuntimeService runtimeService = processEngine.getRuntimeService();
    private FormService formService = processEngine.getFormService();
    private HistoryService historyService = processEngine.getHistoryService();

    public void deploy() {

        repositoryService = processEngine.getRepositoryService();// 与流程定义与部署对象相关的service
        Deployment deployment = repositoryService.createDeployment() //创建一个部署对象
                .name("test")
                .addClasspathResource("diagrams/leave.bpmn").deploy();// 加载文件并完成部署
        System.out.println("部署ID "+deployment.getId());
        System.out.println("部署名称 "+deployment.getName());
    }



    /**
     * 查询我的任务
     */
    public List<com.jenius.web.meta.workflow.Task> findMyTask(String assignee){
        List<Task> taskList = taskService
                .createTaskQuery()
                .taskAssignee(assignee)
                .list();
        List<com.jenius.web.meta.workflow.Task> list = new ArrayList<com.jenius.web.meta.workflow.Task>();
        for(Task task : taskList){
            com.jenius.web.meta.workflow.Task t = new com.jenius.web.meta.workflow.Task();
            t.setTaskId(task.getId());
            t.setName(task.getName());
            t.setCreateTime(task.getCreateTime());
            t.setAssignee(task.getAssignee());
            t.setExecutionId(task.getExecutionId());
            t.setProcessInstanceId(task.getProcessInstanceId());
            t.setProcessDefinitionId(task.getProcessDefinitionId());
            list.add(t);
        }
        return list;

    }

    public void saveNewDeploy(MultipartFile file, String filName) {
        InputStream is ;
        try {
            is = file.getInputStream();
            ZipInputStream zip = new ZipInputStream(is);
            Deployment deployment = repositoryService
                    .createDeployment()
                    .name(filName)
                    .addZipInputStream(zip)
                    .deploy();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 查询部署对象信息      对应表：ACT_RE_DEPLOYMENT
     * @return
     */
    public List<com.jenius.web.meta.workflow.Deployment> findDeploymentList() {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY年MM月dd日hh时mm分ss秒");
        List<Deployment> list = repositoryService
                .createDeploymentQuery()
                .orderByDeploymenTime()
                .asc()
                .list();
        List<com.jenius.web.meta.workflow.Deployment> dList = new ArrayList<com.jenius.web.meta.workflow.Deployment>();
        for (Deployment deployment : list){
            com.jenius.web.meta.workflow.Deployment d = new com.jenius.web.meta.workflow.Deployment();
            d.setId(deployment.getId());
            d.setName(deployment.getName());
            d.setCategory(deployment.getCategory());
            d.setDeployTime(sdf.format(deployment.getDeploymentTime()));
            d.setTenantId(deployment.getTenantId());
            dList.add(d);
        }
        return dList;

    }

    /**
     * 查询流程定义的信息  对应表：ACT_RE_PROCDEF
     * @return
     */
    public List<com.jenius.web.meta.workflow.ProcessDefinition> findProcessDefinitionList() {
        List<ProcessDefinition> list = repositoryService
                .createProcessDefinitionQuery()
                .orderByProcessDefinitionVersion()
                .asc()
                .list();
        List<com.jenius.web.meta.workflow.ProcessDefinition> pdList = new ArrayList<com.jenius.web.meta.workflow.ProcessDefinition>();
        for (ProcessDefinition processDefinition : list){
            com.jenius.web.meta.workflow.ProcessDefinition p = new com.jenius.web.meta.workflow.ProcessDefinition();
            p.setId(processDefinition.getId());
            p.setName(processDefinition.getName());
            p.setDeployId(processDefinition.getDeploymentId());
            p.setDgrmResourceName(processDefinition.getDiagramResourceName());
            p.setResourceName(processDefinition.getResourceName());
            p.setKey(processDefinition.getKey());
            p.setVersion(processDefinition.getVersion());
            pdList.add(p);
        }
        return pdList;
    }

    /**
     * 使用部署对象ID和资源图片名称,获取图片的输入流
     * @param deploymentId
     * @param imageName
     * @return
     */
    public InputStream findImageInputStream(String deploymentId, String imageName) {

        return repositoryService.getResourceAsStream(deploymentId,imageName);
    }

    /**
     * 使用流程定义id获取图片输入流
     * @param procDefId
     * @return
     */
    public InputStream findProcessPic(String procDefId) {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ProcessDefinition procDef = repositoryService.createProcessDefinitionQuery().processDefinitionId(procDefId)
                .singleResult();
        String diagramResourceName = procDef.getDiagramResourceName();
        InputStream imageStream = repositoryService.getResourceAsStream(procDef.getDeploymentId(), diagramResourceName);
        return imageStream;
    }

    public void deleteProcessDefinitionByDeployId(String deployId) {
        repositoryService.deleteDeployment(deployId,true);
    }

    public void saveStartProcess(int id, HttpSession session) {
        // 获取请假单id，使用请假单id，查询请假单的对象
        LeaveBill leaveBill = leaveBillDao.getLeaveBillById(id);
        // 更新请假单的请假状态从0变成1 初始录入-->审核中

        leaveBillDao.updateLeaveBillStateById(id,1);

        // 使用当前对象获取到流程定义的key（对象的名称就是流程定义的key）
        String key = leaveBill.getClass().getSimpleName();
        /**
         * 从session中获取当前任务的办理人，使用流程变量设置下一个任务的办理人
         * inputUser 是流程变量的名称
         * 获取班里人是流程变量的值
         */
        Map<String,Object> variables = new HashMap<String,Object>();
        variables.put("inputUser",((User)(session.getAttribute("user"))).getUsername());

        // 使用流程变量设置字符串（格式：leaveBill.1）通过设置，启动的流程（流程实例）关联业务
        String objId = key+"."+id;
        variables.put("objId",objId);
        // 使用流程的key启动流程实例，同时设置流程变量，同时向正在执行的执行对象表中的字段BUSINESS_KEY添加业务数据，同时让流程关联业务
        runtimeService.startProcessInstanceByKey(key,objId,variables);

    }

    /**
     * 使用任务Id，获取当前任务节点中对应的from key中的连接的值
     * @param taskId
     * @return
     */
    public String findTaskFormKeyByTaskId(String taskId) {

        TaskFormData taskFormData = formService.getTaskFormData(taskId);
        // 获取Form key 的值
        String url = taskFormData.getFormKey();
        return url;
    }

    public LeaveBill findLeaveBillByTaskId(String taskId) {
        // 1. 使用任务id，查询任务对象Task
        Task task = taskService.createTaskQuery()
                .taskId(taskId).singleResult();
        // 2. 使用任务对象Task获取流程实例id
        String processInstanceId = task.getProcessInstanceId();
        // 3. 使用流程实例id查询正在执行的执行对象表，返回一个流程实例对象
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        // 4. 使用流程实例对象获取BUSINESS_KEY
        String business_key = pi.getBusinessKey();
        // 5. 获取BUSINESS_KEY对应的主键id，使用id，查询请假单对象
        String id = "";
        if(StringUtils.isNotBlank(business_key)){
            // 截取字符串  (LeaveBill.10)
            id = business_key.split("\\.")[1];
            // 查询请假单对象

        }
        LeaveBill leaveBill = leaveBillDao.getLeaveBillById(Integer.parseInt(id));
        return leaveBill;

    }

    public void completeMyProcess(String taskId) {

        processEngine.getTaskService() // 与正在执行dde任务管理相关的service
                .complete(taskId);
    }

    /**
     * 已知任务id，查询ProcessDefinitionEntiy对象，从而获取当前任务完成之后的连线名称，并放置到List<String>集合中
     * @param taskId
     * @return
     */
    public List<String> findOutComeLineByTaskId(String taskId) {

        List<String> list = new ArrayList<String>();
        // 1. 使用任务id，查询任务对象Task
        Task task = taskService.createTaskQuery()
                .taskId(taskId).singleResult();
        // 2.获取流程定义id
        String processDefinitionId = task.getProcessDefinitionId();
        // 3.查询processDefinitionEntiy对象
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
        String processInstanceId = task.getProcessInstanceId();
        // 使用流程实例id查询正在执行的执行对象表，返回一个流程实例对象
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        String activityId = pi.getActivityId();
        // 4.获取当前的活动
        ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);
        // 5.获取获取当前活动之后连线的名称
        List<PvmTransition> pvmList = activityImpl.getOutgoingTransitions();
         if(pvmList!=null && pvmList.size()>0){
             for(PvmTransition pvm : pvmList){
                 String name = (String) pvm.getProperty("name");
                 if(StringUtils.isNotBlank(name)){
                     list.add(name);
                 }else{
                     list.add("默认提交");
                 }
             }
         }
        return list;
    }

    /**
     * 提交之后根据提交按钮上的名称对应连线上的名称来执行下一个usertask的操作，并添加批注信息
     * @param taskId
     * @param outcome
     * @param message
     * @param leaveBillId
     */
    public void saveSubmiTask(String taskId, String outcome,String message,int leaveBillId) {

        // 1.在完成任务之前，添加一个批注信息，用用于记录作对当前申请人的一些审核情况
        // 使用任务id，查询任务对象，获取流程实例id
        Task task = taskService.createTaskQuery()
                .taskId(taskId)
                .singleResult();
        // 获取流程实例id
        String processInstanceId = task.getProcessInstanceId();
        // ACT_HI_COMMENT这个表中有一个userid字段对应的就是审核人，如果不设置该字段为null,需要添加当前的审核人
        User user = (User) GetTaskerSession.session.getAttribute("user");
        Authentication.setAuthenticatedUserId(user.getUsername());

        taskService.addComment(taskId,processInstanceId,message);
        LeaveBill leaveBill = leaveBillDao.getLeaveBillById(leaveBillId);
        /**
         * 2.如果是连线的名称是“默认提交”就不用设置，如果不是就需要设置流程变量，
         在完成任务之前，设置流程变量，按照连线的名称，去完成任务
         流程变量的名称：outcommen
         流程变量的值    ：连线的名称
         */
        Map<String ,Object> map = new HashMap<String, Object>();
        if(outcome!=null && !outcome.equals("默认提交")){
            map.put("outcome",outcome);
        }
        System.out.println(leaveBill.getDays());
        map.put("day",leaveBill.getDays());
        // 3.使用任务id完成当前任务,同时设置流程变量
        taskService.complete(taskId,map);
        // 4.当前任务完成之后，需要指定下一个任务办理人（task监听器已完成）
        // 5.在完成任务之后判断，判断流程是否结束，如果流程结束要将state 从1 变成2 （从审核中->审核完成）
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        // 流程结束
        if(processInstance==null){
            // 5.在完成任务之后判断，判断流程是否结束，如果流程结束要将state 从1 变成2 （从审核中->审核完成）
            leaveBillDao.updateLeaveBillStateById(leaveBillId,2);
        }

    }

    /**
     * 获取批注信息，传递的是当前的任务id，获取历史的任务id对应的批注
     * @param taskId
     * @return
     */
    public List<Comment> findCommentByTaskId(String taskId) {
        List<Comment> list = new ArrayList<Comment>();
        // 使用当前的任务ID，查询当前流程对应的历史任务id
        // 使用当前的任务id，获取当前的任务对象
        Task task = taskService.createTaskQuery()
                .taskId(taskId)
                .singleResult();
        // 获取流程实例id
        String processInstanceId = task.getProcessInstanceId();
        // 使用流程实例id，查询历史任务，获取历史任务对应的每个id
//        List<HistoricTaskInstance> hriList = historyService.createHistoricTaskInstanceQuery() // 历史任务表查询
//                    .processInstanceId(processInstanceId) // 使用流程实例查询
//                    .list();
//        // 遍历集合，获取每个任务id
//        if(hriList!=null && hriList.size()>0){
//            for (HistoricTaskInstance historicTaskInstance : hriList){
//                String htaskId = historicTaskInstance.getId();
//                // 获取批注信息
//                List<Comment> taskList = taskService.getTaskComments(htaskId); // 对应历史完成后的任务id
//                list.addAll(taskList);
//            }
//        }
        list = taskService.getProcessInstanceComments(processInstanceId);
        return list;


    }

    /**
     *  获取leaveBillId查找历史的批注信息
     */
    public Map<String,Object> findCommentByLeaveBillId(String leaveBillId) {
        Map<String,Object> map = new HashMap<String,Object>();
        // 使用请假单id，查询请假单对象
        LeaveBill leaveBill = leaveBillDao.getLeaveBillById(Integer.parseInt(leaveBillId));
        String  objectName = leaveBill.getClass().getSimpleName();
        // 组织流程表中字段中的值
        String objectId = objectName+"."+leaveBillId;
        // 使用历史的流程实例查询，获取流程实例id
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceBusinessKey(objectId) // 使用BusinessKey字段查询
                .singleResult();
        String processInstanceId = historicProcessInstance.getId();
        List<Comment> list = taskService.getProcessInstanceComments(processInstanceId);
        // 使用历史的流程变量查询
        map.put("commentList",list);
        map.put("leaveBill",leaveBill);
        return map;
    }



    public ProcessDefinition findProcessDefinitionByTaskId(String taskId) {
        // 通过任务id 获取任务对象
        Task task = taskService.createTaskQuery()
                .taskId(taskId)
                .singleResult();
        String processDefinitionId = task.getProcessDefinitionId();
        // 获取流程实例对象
        ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId)
                .singleResult();
        return pd;
    }

    /**
     * 获取当前任务坐标，通过任务id获取
     * @param taskId
     * @return
     */
    public Rect findCoordingByTaskId(String taskId) {
        // 通过任务id 获取任务对象
        Task task = taskService.createTaskQuery()
                .taskId(taskId)
                .singleResult();
        // 获取流程化定义id
        String processDefinitionId = task.getProcessDefinitionId();
        // 获取流程定义的实体对象（对应.bpmn文件中的数据）
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
        // 使用流程实例id，查询正在执行的执行对象表，获取当前活动对应的流程实例对象
        String processInstanceId = task.getProcessInstanceId();
        // 使用流程实例id，查询正在执行的执行对象表，获取当前活动对应的流程实例对象
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        // 获取当前活动的字段
        String activitId = pi.getActivityId();
        // 获取当前活动对象
        ActivityImpl activity = processDefinitionEntity.findActivity(activitId);
        // 获取坐标
        Rect rect = new Rect();
        rect.setX(activity.getX());
        rect.setY(activity.getY());
        rect.setWidth(activity.getWidth());
        rect.setHeight(activity.getHeight());
        return rect;
    }


}
