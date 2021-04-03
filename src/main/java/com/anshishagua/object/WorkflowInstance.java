package com.anshishagua.object;

import com.anshishagua.constants.WorkflowStatus;
import com.anshishagua.object.tasks.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * WorkflowInstance.java
 *
 * @author lixiao
 * @date 2021-03-27
 */

public class WorkflowInstance {
    private long id;
    private String applicationName;
    private String workflowName;
    private String workflowClass;
    private String workflowParamsString;
    private Map<String, Object> workflowParams;
    private String workflowGraphString;
    private Map<String, Task> workflowGraph;
    @JsonSerialize(using = LocalDateTimeConverter.class)
    private LocalDateTime createTime;
    @JsonSerialize(using = LocalDateTimeConverter.class)
    private LocalDateTime startTime;
    @JsonSerialize(using = LocalDateTimeConverter.class)
    private LocalDateTime endTime;
    private WorkflowStatus status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getWorkflowName() {
        return workflowName;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    public String getWorkflowClass() {
        return workflowClass;
    }

    public void setWorkflowClass(String workflowClass) {
        this.workflowClass = workflowClass;
    }

    public String getWorkflowParamsString() {
        return workflowParamsString;
    }

    public void setWorkflowParamsString(String workflowParamsString) {
        this.workflowParamsString = workflowParamsString;
    }

    public Map<String, Object> getWorkflowParams() {
        return workflowParams;
    }

    public void setWorkflowParams(Map<String, Object> workflowParams) {
        this.workflowParams = workflowParams;
    }

    public String getWorkflowGraphString() {
        return workflowGraphString;
    }

    public void setWorkflowGraphString(String workflowGraphString) {
        this.workflowGraphString = workflowGraphString;
    }

    public Map<String, Task> getWorkflowGraph() {
        return workflowGraph;
    }

    public void setWorkflowGraph(Map<String, Task> workflowGraph) {
        this.workflowGraph = workflowGraph;
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

    public WorkflowStatus getStatus() {
        return status;
    }

    public void setStatus(WorkflowStatus status) {
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
