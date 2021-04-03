package com.anshishagua.object;

import com.anshishagua.object.tasks.Task;
import com.anshishagua.object.tasks.TaskUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Workflow.java
 *
 * @author lixiao
 * @date 2021-03-26
 */

public class Workflow {
    private String workflowName;
    private WorkflowParams workflowParams;
    private Map<String, Task> workflowGraph;

    public Workflow() {
        this.workflowParams = new WorkflowParams();
        this.workflowGraph = new HashMap<>();
    }

    public String getWorkflowName() {
        return workflowName;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    public WorkflowParams getWorkflowParams() {
        return workflowParams;
    }

    public void setWorkflowParams(WorkflowParams workflowParams) {
        this.workflowParams = workflowParams;
    }

    public Map<String, Task> getWorkflowGraph() {
        return workflowGraph;
    }

    public void setWorkflowGraph(Map<String, Task> workflowGraph) {
        this.workflowGraph = workflowGraph;
    }

    public Workflow buildWorkflow() {
        Workflow workflow = new Workflow();
        workflow.setWorkflowName(workflowName);
        workflow.setWorkflowParams(workflowParams);

        return workflow;
    }

    public Object run() throws Exception {
        return "Workflow " + workflowName + " run";
    }

    public String toJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String toString() {
        return toJson();
    }

    public static Workflow fromJson(String json) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            Map<String, Object> map = mapper.readValue(json, Map.class);
            Workflow workflow = new Workflow();
            workflow.setWorkflowName((String) map.get("workflowName"));
            WorkflowParams workflowParams = new WorkflowParams();
            Map<String, Object> params = (Map<String, Object>) map.getOrDefault("workflowParams", new HashMap<>());
            for (String key : params.keySet()) {
                workflowParams.put(key, params.get(key));
            }
            workflow.setWorkflowParams(workflowParams);
            params = (Map<String, Object>) map.get("workflowGraph");
            Map<String, Task> workflowGraph = new HashMap<>();

            for (String taskName : params.keySet()) {
                Task task = TaskUtils.fromJson(mapper.writeValueAsString(params.get(taskName)));
                workflowGraph.put(taskName, task);
            }
            workflow.setWorkflowGraph(workflowGraph);

            return workflow;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
