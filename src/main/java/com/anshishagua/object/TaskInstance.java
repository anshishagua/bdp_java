package com.anshishagua.object;

import com.anshishagua.constants.TaskStatus;
import com.anshishagua.constants.TaskType;
import com.anshishagua.constants.WorkflowStatus;
import com.anshishagua.object.tasks.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Task.java
 *
 * @author lixiao
 * @date 2021-03-26
 */

public class TaskInstance {
    private long id;
    private long workflowInstanceId;
    private String taskName;
    private TaskType taskType;
    private String taskClass;
    private Map<String, Object> taskParams;
    private String taskParamsString;
    @JsonSerialize(using = LocalDateTimeConverter.class)
    private LocalDateTime createTime;
    @JsonSerialize(using = LocalDateTimeConverter.class)
    private LocalDateTime startTime;
    @JsonSerialize(using = LocalDateTimeConverter.class)
    private LocalDateTime endTime;
    private TaskStatus status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getWorkflowInstanceId() {
        return workflowInstanceId;
    }

    public void setWorkflowInstanceId(long workflowInstanceId) {
        this.workflowInstanceId = workflowInstanceId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public String getTaskClass() {
        return taskClass;
    }

    public void setTaskClass(String taskClass) {
        this.taskClass = taskClass;
    }

    public Map<String, Object> getTaskParams() {
        return taskParams;
    }

    public void setTaskParams(Map<String, Object> taskParams) {
        this.taskParams = taskParams;
    }

    public String getTaskParamsString() {
        return taskParamsString;
    }

    public void setTaskParamsString(String taskParamsString) {
        this.taskParamsString = taskParamsString;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String toJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
