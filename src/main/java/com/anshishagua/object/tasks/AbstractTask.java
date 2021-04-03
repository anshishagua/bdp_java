package com.anshishagua.object.tasks;

import com.anshishagua.constants.TaskType;
import com.anshishagua.object.TaskParams;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * AbstractTask.java
 *
 * @author lixiao
 * @date 2021-03-27
 */

public abstract class AbstractTask implements Task {
    private String taskName;
    private TaskType taskType;
    private String taskClass;
    private List<String> prevTaskNames = new ArrayList<>();
    private List<String> nextTaskNames = new ArrayList<>();
    private TaskParams taskParams = new TaskParams();

    @Override
    public String getTaskName() {
        return taskName;
    }

    @Override
    public List<String> getPrevTaskNames() {
        return prevTaskNames;
    }

    @Override
    public List<String> getNextTaskNames() {
        return nextTaskNames;
    }

    @Override
    public abstract TaskType getTaskType();

    @Override
    public String getTaskClass() {
        return taskClass;
    }

    public void setTaskClass(String taskClass) {
        this.taskClass = taskClass;
    }

    @Override
    public TaskParams getTaskParams() {
        return taskParams;
    }

    public void setNextTaskNames(List<String> nextTaskNames) {
        this.nextTaskNames = nextTaskNames;
    }

    public void setPrevTaskNames(List<String> prevTaskNames) {
        this.prevTaskNames = prevTaskNames;
    }

    @Override
    public void setTaskParams(TaskParams taskParams) {
        this.taskParams = taskParams;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String toString() {
        return toJson();
    }
}
