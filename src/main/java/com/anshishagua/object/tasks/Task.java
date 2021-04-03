package com.anshishagua.object.tasks;

import com.anshishagua.constants.TaskType;
import com.anshishagua.object.TaskParams;

import java.util.List;

/**
 * Task.java
 *
 * @author lixiao
 * @date 2021-03-27
 */

public interface Task {
    String getTaskName();
    List<String> getPrevTaskNames();
    List<String> getNextTaskNames();
    TaskType getTaskType();
    TaskParams getTaskParams();
    String getTaskClass();
    void setTaskParams(TaskParams taskParams);
    String toJson();
}
