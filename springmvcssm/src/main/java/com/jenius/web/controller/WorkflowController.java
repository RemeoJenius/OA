package com.jenius.web.controller;

import com.jenius.web.meta.Person;
import com.jenius.web.meta.User;
import com.jenius.web.meta.workflow.LeaveBill;
import com.jenius.web.meta.workflow.Rect;
import com.jenius.web.meta.workflow.WorkflowBean;
import com.jenius.web.service.impl.LeaveBillServiceImpl;
import com.jenius.web.service.impl.WorkflowServiceImpl;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.in;

/**
 * Created by liyongjun on 17/2/21.
 */
@Controller
public class WorkflowController {

    @Resource(name = "workflowServiceImpl")
    private WorkflowServiceImpl workflowServiceImpl;

    @Resource(name = "leaveBillServiceImpl")
    private LeaveBillServiceImpl leaveBillServiceImpl;

    private String url; // 动态设置url 从from key中的值

    /**
     * 通过定义好的流程图文件部署，一次只能部署一个流程
     */
    @RequestMapping("deploy")
    public void deploy() {
        workflowServiceImpl.deploy();
    }

    /**
     * 启动流程
     */
    @RequestMapping("startProcess/{updateId}")
    public String startInstanceByKey(@PathVariable int updateId,HttpSession session) {
        // 启动流程实例，更新请假状态，让启动的流程实例，关联业务
       workflowServiceImpl.saveStartProcess(updateId,session);

       return "/workflow/taskManagement";
    }

    @RequestMapping("findMyprocess")
    public @ResponseBody List<com.jenius.web.meta.workflow.Task> findMyProcess(HttpSession session){
        User user = (User) session.getAttribute("user");
        String assigness = (String) user.getUsername() ;
        List<com.jenius.web.meta.workflow.Task> tasks = workflowServiceImpl.findMyTask(assigness);

        return tasks;
    }

    /**
     * 发布流程
     * @param request
     * @return
     */
    @RequestMapping("newDeploy")
    public String newDeploy(HttpServletRequest request, @ModelAttribute("workflowBean")WorkflowBean workflowBean,@RequestParam("file") MultipartFile file) {
        /**
         * 获取页面传递的值
         * 1.获取页面上传递的zip格式的文件。格式为file
         */
        workflowBean.setFile(file);
        workflowServiceImpl.saveNewDeploy(file,workflowBean.getFilename());

        // 重定向
        return "/workflow/leaveDeploy";
    }

    /**
     * 部署首页显示
     */
    @RequestMapping("deployHome")
    public @ResponseBody Map<String, Object> deployHome(){
        Map<String, Object> map = new HashMap<String,Object>();
        //1.查询部署对象信息      对应表：ACT_RE_DEPLOYMENT
        List<com.jenius.web.meta.workflow.Deployment> deploymentList = workflowServiceImpl.findDeploymentList();
        //2.查询流程定义的信息  对应表：ACT_RE_PROCDEF
        List<com.jenius.web.meta.workflow.ProcessDefinition> processDefinitionList = workflowServiceImpl.findProcessDefinitionList();
        map.put("deploymentList",deploymentList);
        map.put("pdList",processDefinitionList);
        return map;
    }

    /**
     * 查看流程图版本1
     */
    @RequestMapping("showImage")
    public void showImage(HttpServletResponse response,@RequestParam("deploymentId") String deploymentId, @RequestParam("imageName") String imageName) throws IOException {
        InputStream in = workflowServiceImpl.findImageInputStream(deploymentId,imageName);
        byte[] b = new byte[1024];
        int len = -1;
        while((len = in.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }
    @RequestMapping(value = "showImg")
    public void shwoImg(@RequestParam String processDefinitionId,HttpServletResponse response){
        try {
            InputStream pic = workflowServiceImpl.findProcessPic(processDefinitionId);

            byte[] b = new byte[1024];
            int len = -1;
            while((len = pic.read(b, 0, 1024)) != -1) {
                response.getOutputStream().write(b, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取坐标
     */
    @RequestMapping("getRect/{taskId}")
    public @ResponseBody Rect getRect(@PathVariable("taskId")String taskId){
        // 获取当前活动，获取当前活动的对应坐标x,y,width,height
        Rect rect = workflowServiceImpl.findCoordingByTaskId(taskId);
        return rect;
    }

    /**
     * 查看当前流程图
     */
    @RequestMapping("returnDeploymentIdAndDiagramResourceName")
    public @ResponseBody Map<String,Object> showImageByTaskId(HttpServletResponse response,@RequestParam("taskId") String taskId) throws IOException {
        // 通过任务id，获取任务对象，使用任务对象获取获取流程实例id查询流程实例对象
        ProcessDefinition processDefinition = workflowServiceImpl.findProcessDefinitionByTaskId(taskId);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("deploymentId", processDefinition.getDeploymentId());
        map.put("imageName", processDefinition.getDiagramResourceName());
        return map;


    }


//    /**
//     * 查看流程图版本2
//     */
//    @RequestMapping(value = "/shwoImg/{procDefId}")
//    public void shwoImg(@PathVariable String procDefId,HttpServletResponse response){
//        try {
//            InputStream pic = workflowServiceImpl.findProcessPic(procDefId);
//
//            byte[] b = new byte[1024];
//            int len = -1;
//            while((len = pic.read(b, 0, 1024)) != -1) {
//                response.getOutputStream().write(b, 0, len);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//
//    }

    /**
     * 删除流程定义
     */
    @RequestMapping("deleteProcessDefinition/{deployId}")
    public String deleteProcessDefinition(@PathVariable String deployId){
        workflowServiceImpl.deleteProcessDefinitionByDeployId(deployId);
        return "/workflow/leaveDeploy";
    }

    /**
     * 打开任务表单
     */
    @RequestMapping("viewTaskForm/{id}")
    public String viewTaskForm(@PathVariable("id")String id){
        // 任务ID
        String taskId = id;
        // 获取任务表单中任务节点的url链接
        this.url = workflowServiceImpl.findTaskFormKeyByTaskId(taskId);
        return "forward:/api/url/"+url+"/"+taskId;
    }



    /**
     * 任务id查找请假单id
     */
    @RequestMapping("url/{url}/{taskId}")
    public @ResponseBody Map<String,Object> audit(@PathVariable("url")String url, @PathVariable("taskId") String taskId){
        HashMap<String,Object> map = new HashMap<String, Object>();
        LeaveBill leaveBill;
        System.out.println(url);
        if(url.equals(this.url)){
            // 任务id查找请假单id
            leaveBill = leaveBillServiceImpl.findLeaveBillById(taskId);
            // 已知任务id，查询ProcessDefinitionEntiy对象，从而获取当前任务完成之后的连线名称，并放置到List<String>集合中
            List<String> outcomeList = workflowServiceImpl.findOutComeLineByTaskId(taskId);
            map.put("leaveBill",leaveBill);
            map.put("outcomeList",outcomeList);
            // 查询所有历史审核的人的信息，帮助当前人完成审核，返回lList<Comment>
            List<Comment> commentList = workflowServiceImpl.findCommentByTaskId(taskId);
            map.put("commentList",commentList);
            return map;
        }else{
            return null;
        }


    }

    /**
     * 完成任务
     */
    @RequestMapping("completeMyProcess/{taskId}")
    public String completeMyProcess(@PathVariable("taskId") String taskId){
        workflowServiceImpl.completeMyProcess(taskId);
        return "forward:/auth/workflow/taskManagement";
    }

    /**
     * 提交任务
     */
    @RequestMapping("submitTask/{taskId}/{leaveBillId}")
    public @ResponseBody Map<String,Object> submitTask(@PathVariable("taskId") String taskId,@PathVariable("leaveBillId")int leaveBillId,@RequestParam("outcome")String outcome,@RequestParam("annotation")String annotation){
        HashMap<String,Object> map = new HashMap<String, Object>();
        workflowServiceImpl.saveSubmiTask(taskId,outcome,annotation,leaveBillId);
        map.put("success","success");
        return map;
    }

    /**
     *  获取leaveBillId查找历史的批注信息
     */
    @RequestMapping("showHisComment/{leaveBillId}")
    public @ResponseBody Map<String,Object> showHisComment(@PathVariable("leaveBillId") String leaveBillId){
        // 利用请假单id，获取历史批注信息
        Map<String,Object> map = workflowServiceImpl.findCommentByLeaveBillId(leaveBillId);

        return map;

    }




}
