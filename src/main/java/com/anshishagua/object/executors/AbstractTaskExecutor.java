package com.anshishagua.object.executors;

import com.anshishagua.object.TaskLogger;
import com.anshishagua.object.TaskExecutor1;
import com.anshishagua.object.TaskParams;
import com.anshishagua.object.tasks.Task;

import java.io.IOException;

/**
 * AbstractExecutor.java
 *
 * @author lixiao
 * @date 2021-04-01
 */

public abstract class AbstractTaskExecutor implements TaskExecutor1 {
    private Exception exception;
    private Task task;
    private TaskParams taskParams;
    private TaskLogger logger;
    private long workflowInstanceId;
    private long taskInstanceId;

    public AbstractTaskExecutor() {

    }

    public AbstractTaskExecutor(Task task, TaskParams taskParams, long workflowInstanceId, long taskInstanceId) {
        this.task = task;
        this.taskParams = taskParams;
        this.taskInstanceId = taskInstanceId;
        this.workflowInstanceId = workflowInstanceId;
        this.logger = new TaskLogger(task.getTaskName());
        this.logger.setTaskInstanceId(taskInstanceId);
        this.logger.setWorkflowInstanceId(workflowInstanceId);
        try {
            this.logger.init();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public long getTaskInstanceId() {
        return taskInstanceId;
    }

    public long getWorkflowInstanceId() {
        return workflowInstanceId;
    }

    @Override
    public TaskLogger getTaskLogger() {
        return logger;
    }

    @Override
    public Task getTask() {
        return task;
    }

    @Override
    public void before() {

    }

    @Override
    public void after() {

    }

    @Override
    public Exception exception() {
        return this.exception;
    }

    public abstract void doRun() throws Exception;

    @Override
    public final void execute() {
        before();

        try {
            doRun();
        } catch (Exception ex) {
            this.exception = ex;
        }

        after();
    }
}
