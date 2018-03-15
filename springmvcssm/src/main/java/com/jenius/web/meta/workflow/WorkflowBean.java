package com.jenius.web.meta.workflow;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * Created by liyongjun on 17/2/23.
 */
public class WorkflowBean {

    private MultipartFile file;              // 流程定义部署文件
    private String filename;        // 流程定义名称

    private long id;                // 申请单ID

    private String deploymentId;    // 部署对象Id
    private String imageName;       // 资源文件名称
    private String taskId;          // 任务Id
    private String outcome;         // 连线名称
    private String comment;         // 备注

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
