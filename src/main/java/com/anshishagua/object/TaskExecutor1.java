package com.anshishagua.object;

import com.anshishagua.object.tasks.Task;

/**
 * TaskExecutor1.java
 *
 * @author lixiao
 * @date 2021-04-01
 */

public interface TaskExecutor1 {
    Task getTask();
    TaskLogger getTaskLogger();
    void before();
    void after();
    Exception exception();
    void execute();
}
