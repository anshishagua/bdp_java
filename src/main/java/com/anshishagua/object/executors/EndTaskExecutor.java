package com.anshishagua.object.executors;

import com.anshishagua.object.TaskParams;
import com.anshishagua.object.tasks.Task;

/**
 * EndTaskExecutor.java
 *
 * @author lixiao
 * @date 2021-04-02
 */

public class EndTaskExecutor extends AbstractTaskExecutor {
    public EndTaskExecutor(Task task, TaskParams taskParams, long workflowInstanceId, long taskInstanceId) {
        super(task, taskParams, workflowInstanceId, taskInstanceId);
    }

    @Override
    public void doRun() throws Exception {

    }
}
