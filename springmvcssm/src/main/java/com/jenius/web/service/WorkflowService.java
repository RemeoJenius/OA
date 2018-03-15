package com.jenius.web.service;

import com.jenius.web.meta.Person;
import com.jenius.web.meta.workflow.LeaveBill;
import com.jenius.web.meta.workflow.Rect;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by liyongjun on 17/2/21.
 */
public interface WorkflowService {

     void deploy();


    List<com.jenius.web.meta.workflow.Task> findMyTask(String assignee);

    void saveNewDeploy( MultipartFile file, String filName);
    List<com.jenius.web.meta.workflow.Deployment> findDeploymentList();

    List<com.jenius.web.meta.workflow.ProcessDefinition> findProcessDefinitionList();

    InputStream findImageInputStream(String deploymentId, String imageName);

    InputStream findProcessPic(String procDefId);

    void deleteProcessDefinitionByDeployId(String deployId);


    void saveStartProcess(int id,HttpSession session);

    String findTaskFormKeyByTaskId(String taskId);

    LeaveBill findLeaveBillByTaskId(String taskId);

    void completeMyProcess(String taskId);

    List<String> findOutComeLineByTaskId(String taskId);

    void saveSubmiTask(String taskId, String outcome,String message,int leaveBillId);

    List<Comment> findCommentByTaskId(String taskId);

    Map<String,Object> findCommentByLeaveBillId(String leaveBillId);



    ProcessDefinition findProcessDefinitionByTaskId(String taskId);

    Rect findCoordingByTaskId(String taskId);
}
