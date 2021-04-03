package com.anshishagua.object.tasks;

import com.anshishagua.constants.TaskType;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * StartTask.java
 *
 * @author lixiao
 * @date 2021-03-27
 */

public class StartTask extends AbstractTask {
    public static final String TASK_NAME = "start";

    @Override
    public TaskType getTaskType() {
        return TaskType.START;
    }

    @Override
    public String getTaskName() {
        return TASK_NAME;
    }

    @Override
    public List<String> getPrevTaskNames() {
        return Collections.emptyList();
    }
}
