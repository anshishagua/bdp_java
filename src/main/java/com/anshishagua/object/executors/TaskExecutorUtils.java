package com.anshishagua.object.executors;

import com.anshishagua.object.TaskExecutor1;
import com.anshishagua.object.TaskParams;
import com.anshishagua.object.tasks.Task;

/**
 * TaskExecutorUtils.java
 *
 * @author lixiao
 * @date 2021-04-02
 */

public class TaskExecutorUtils {
    public static void execute(Task task, TaskParams taskParams, long workflowInstanceId, long taskInstanceId) {
        TaskExecutor1 executor = null;

        switch (task.getTaskType()) {
            case START:
                executor = new StartTaskExecutor(task, taskParams, workflowInstanceId, taskInstanceId);
                break;
            case END:
                executor = new EndTaskExecutor(task, taskParams, workflowInstanceId, taskInstanceId);
                break;
            case JAVA:
                executor = new JavaTaskExecutor(task, taskParams, workflowInstanceId, taskInstanceId);
                break;
            case JOIN:
                executor = new JoinTaskExecutor(task, taskParams, workflowInstanceId, taskInstanceId);
                break;
            case PYTHON:
                executor = new PythonTaskExecutor(task, taskParams, workflowInstanceId, taskInstanceId);
                break;
            case SHELL:
                executor = new ShellTaskExecutor(task, taskParams, workflowInstanceId, taskInstanceId);
                break;
            default:
                throw new RuntimeException();
        }

        executor.execute();
    }
}
