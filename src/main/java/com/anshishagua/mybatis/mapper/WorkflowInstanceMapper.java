package com.anshishagua.mybatis.mapper;

import com.anshishagua.constants.WorkflowStatus;
import com.anshishagua.object.WorkflowInstance;
import com.anshishagua.object.WorkflowParams;
import com.anshishagua.object.tasks.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * User: lixiao
 * Date: 2021/3/27
 * Time: 11:59 下午
 */

@Mapper
public interface WorkflowInstanceMapper {
    WorkflowInstance getWorkflowInstance(@Param("id") long workflowId);
    List<WorkflowInstance> listWorkflow(@Param("workflow_name") String workflowName);
    int addWorkflow(WorkflowInstance workflowInstance);
    void updateWorkflowInstance(WorkflowInstance workflowInstance);
}
