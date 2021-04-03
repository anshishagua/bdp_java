package com.anshishagua.object.tasks;

import com.anshishagua.constants.TaskType;

/**
 * PythonTask.java
 *
 * @author lixiao
 * @date 2021-04-01
 */

public class PythonTask extends AbstractTask {
    @Override
    public TaskType getTaskType() {
        return TaskType.PYTHON;
    }
}
