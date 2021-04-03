package com.anshishagua.object.tasks;

import com.anshishagua.constants.TaskType;

/**
 * JavaTask.java
 *
 * @author lixiao
 * @date 2021-03-27
 */

public class JavaTask extends AbstractTask {
    public JavaTask() { }
    public JavaTask(String taskName) {
        setTaskName(taskName);
    }

    @Override
    public TaskType getTaskType() {
        return TaskType.JAVA;
    }
}
