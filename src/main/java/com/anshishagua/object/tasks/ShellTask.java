package com.anshishagua.object.tasks;

import com.anshishagua.constants.TaskType;

/**
 * ShellTask.java
 *
 * @author lixiao
 * @date 2021-04-02
 */

public class ShellTask extends AbstractTask {
    @Override
    public TaskType getTaskType() {
        return TaskType.SHELL;
    }
}
