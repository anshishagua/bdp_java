package com.anshishagua.object;

import com.anshishagua.object.tasks.EndTask;
import com.anshishagua.object.tasks.JoinTask;
import com.anshishagua.object.tasks.StartTask;
import com.anshishagua.object.tasks.Task;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * WorkflowBuilder.java
 *
 * @author lixiao
 * @date 2021-03-27
 */

public class WorkflowBuilder {
    private String workflowName;
    private Map<String, Task> workflowGraph;
    private WorkflowParams workflowParams;

    public WorkflowBuilder(String workflowName) {
        this.workflowName = workflowName;
        this.workflowGraph = new HashMap<>();
        this.workflowGraph.put(StartTask.TASK_NAME, new StartTask());
        this.workflowParams = new WorkflowParams();
    }

    public WorkflowBuilder workflowParams(WorkflowParams workflowParams) {
        this.workflowParams = workflowParams;

        return this;
    }

    public WorkflowBuilder addTaskAfter(String prevTaskName, Task task) {
        Objects.requireNonNull(task);
        Task prevTask = workflowGraph.get(prevTaskName);
        if (prevTask == null) {
            throw new RuntimeException(String.format("Task %s not found", prevTaskName));
        }

        task.getPrevTaskNames().add(prevTaskName);
        prevTask.getNextTaskNames().add(task.getTaskName());

        workflowGraph.put(task.getTaskName(), task);

        return this;
    }

    public Workflow build() {
        JoinTask joinTask = new JoinTask("join");

        Task endTask = new EndTask();

        for (Task task : workflowGraph.values()) {
            if (task.getNextTaskNames().isEmpty()) {
                task.getNextTaskNames().add(EndTask.TASK_NAME);
                endTask.getPrevTaskNames().add(task.getTaskName());
            }
        }

        workflowGraph.put(EndTask.TASK_NAME, endTask);

        Workflow workflow = new Workflow();
        workflow.setWorkflowName(workflowName);
        workflow.setWorkflowParams(workflowParams);
        workflow.setWorkflowGraph(workflowGraph);

        return workflow;
    }
}
