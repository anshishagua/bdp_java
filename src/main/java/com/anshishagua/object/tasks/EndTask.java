package com.anshishagua.object.tasks;

import com.anshishagua.constants.TaskType;

import java.util.Collections;
import java.util.List;

/**
 * EndTask.java
 *
 * @author lixiao
 * @date 2021-03-27
 */

public class EndTask extends AbstractTask {
    public static final String TASK_NAME = "end";

    @Override
    public TaskType getTaskType() {
        return TaskType.END;
    }

    @Override
    public String getTaskName() {
        return TASK_NAME;
    }

    @Override
    public List<String> getNextTaskNames() {
        return Collections.emptyList();
    }
}
