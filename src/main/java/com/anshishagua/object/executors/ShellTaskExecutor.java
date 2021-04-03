package com.anshishagua.object.executors;

import com.anshishagua.object.TaskParams;
import com.anshishagua.object.tasks.Task;

/**
 * ShellTaskExecutor.java
 *
 * @author lixiao
 * @date 2021-04-02
 */

public class ShellTaskExecutor extends AbstractTaskExecutor {
    public ShellTaskExecutor(Task task, TaskParams taskParams, long workflowInstanceId, long taskInstanceId) {
        super(task, taskParams, workflowInstanceId, taskInstanceId);
    }

    @Override
    public void doRun() throws Exception {

    }
}
