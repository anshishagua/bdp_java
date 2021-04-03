package com.anshishagua.object.tasks;

import com.anshishagua.constants.TaskType;

/**
 * JoinTask.java
 *
 * @author lixiao
 * @date 2021-03-27
 */

public class JoinTask extends AbstractTask {
    public JoinTask() {

    }

    public JoinTask(String taskName) {
        setTaskName(taskName);
    }

    @Override
    public TaskType getTaskType() {
        return TaskType.JOIN;
    }
}
