package com.anshishagua.service;

import com.anshishagua.constants.WorkflowStatus;
import com.anshishagua.mybatis.mapper.WorkflowInstanceMapper;
import com.anshishagua.object.WorkflowInstance;
import com.anshishagua.object.WorkflowParams;
import com.anshishagua.object.tasks.Task;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * WorkflowInstanceService.java
 *
 * @author lixiao
 * @date 2021-03-27
 */

@Service
public class WorkflowInstanceService {
    @Autowired
    private WorkflowInstanceMapper mapper;

    public void addWorkflowInstance(WorkflowInstance workflowInstance) {
        mapper.addWorkflow(workflowInstance);
    }

    public WorkflowInstance getWorkflowInstance(long id) {
        return mapper.getWorkflowInstance(id);
    }

    public List<WorkflowInstance> list(String workflowName) {
        return mapper.listWorkflow(workflowName);
    }

    public void updateWorkflowInstance(WorkflowInstance workflowInstance) {

    }

    public long addWorkflowInstance(String applicationName,
                                    String workflowName,
                                    WorkflowParams workflowParams,
                                    Map<String, Task> workflowGraph,
                                    WorkflowStatus workflowStatus,
                                    LocalDateTime startTime,
                                    LocalDateTime endTime,
                                    LocalDateTime createTime) {
        return 1;
        //return mapper.addWorkflow(applicationName, workflowName, workflowParams.toString(), workflowGraph.toString(),
        //        workflowStatus, startTime, endTime, createTime);
    }
}
