package com.anshishagua.mybatis.mapper;

import com.anshishagua.object.TaskInstance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * TaskInstanceMapper.java
 *
 * @author lixiao
 * @date 2021-03-28
 */

@Mapper
public interface TaskInstanceMapper {
    TaskInstance getById(@Param("taskInstanceId") long taskInstanceId);
    List<TaskInstance> getTaskInstance(@Param("workflowInstanceId") long workflowInstanceId,
                                       @Param("taskName") String taskName);
    int addTaskInstance(TaskInstance taskInstance);
    boolean updateTaskInstance(TaskInstance taskInstance);
}
