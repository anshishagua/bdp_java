package com.anshishagua.service;

import com.anshishagua.constants.TaskStatus;
import com.anshishagua.mybatis.mapper.TaskInstanceMapper;
import com.anshishagua.object.TaskInstance;
import com.anshishagua.object.tasks.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * TaskInstanceService.java
 *
 * @author lixiao
 * @date 2021-03-28
 */

@Service
public class TaskInstanceService {
    @Autowired
    private TaskInstanceMapper mapper;

    public void addTaskInstance(TaskInstance taskInstance) {
        mapper.addTaskInstance(taskInstance);
    }

    public TaskInstance getTaskInstance(long taskInstanceId) {
        return mapper.getById(taskInstanceId);
    }

    public List<TaskInstance> getTaskInstance(long workflowInstanceId, String taskName) {
        List<TaskInstance> list =  mapper.getTaskInstance(workflowInstanceId, taskName);
        ObjectMapper mapper = new ObjectMapper();

        for (TaskInstance taskInstance : list) {
            try {
                taskInstance.setTaskParamsString(mapper.writeValueAsString(taskInstance.getTaskParams()));
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        return list;
    }

    public boolean updateTaskInstance(TaskInstance taskInstance) {
        return mapper.updateTaskInstance(taskInstance);
    }

    public TaskInstance runTask(long workflowInstanceId, Task task) {
        List<TaskInstance> taskInstances = getTaskInstance(workflowInstanceId, task.getTaskName());

        if (taskInstances == null || taskInstances.isEmpty()) {
            throw new RuntimeException();
        }

        TaskInstance taskInstance = taskInstances.get(0);

        taskInstance.setStatus(TaskStatus.RUNNING);
        taskInstance.setStartTime(LocalDateTime.now());

        updateTaskInstance(taskInstance);

        return taskInstance;
    }
}
