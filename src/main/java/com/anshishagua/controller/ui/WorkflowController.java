package com.anshishagua.controller.ui;

import com.anshishagua.constants.TaskStatus;
import com.anshishagua.object.Application;
import com.anshishagua.object.TaskInstance;
import com.anshishagua.object.WorkflowInstance;
import com.anshishagua.service.ApplicationService;
import com.anshishagua.service.MailService;
import com.anshishagua.service.TaskInstanceService;
import com.anshishagua.service.WorkflowInstanceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * WorkflowController.java
 *
 * @author lixiao
 * @date 2021-03-26
 */

@Controller
@RequestMapping("/workflow")
public class WorkflowController {
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private WorkflowInstanceService workflowInstanceService;
    @Autowired
    private TaskInstanceService taskInstanceService;
    @Autowired
    private MailService mailService;

    private Logger logger = LoggerFactory.getLogger(WorkflowController.class);

    @RequestMapping("")
    public ModelAndView home() {
        List<Application> list = applicationService.getAllApplications();
        List<String> applicationNames = list.stream().map(it -> it.getName()).collect(Collectors.toList());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("workflow");
        modelAndView.addObject("applications", applicationNames);

        return modelAndView;
    }

    @RequestMapping("/list")
    public ModelAndView list(@RequestParam(value = "workflow_name", required = false) String workflowName) {
        logger.info("hello, world");

        List<WorkflowInstance> list = workflowInstanceService.list(workflowName);
        logger.info("" + list.size());
        ModelAndView modelAndView = new ModelAndView("list_workflows");
        modelAndView.addObject("workflows", list);

        return modelAndView;
    }

    @RequestMapping("/details")
    public ModelAndView details(@RequestParam("workflowInstanceId") long workflowInstanceId) {
        WorkflowInstance workflowInstance = workflowInstanceService.getWorkflowInstance(workflowInstanceId);
        logger.info("hello,world");

        ModelAndView modelAndView = new ModelAndView("workflow_details");
        modelAndView.addObject("workflowInstance", workflowInstance);
        ObjectMapper mapper = new ObjectMapper();

        Map<String, String> taskStatus = new HashMap<>();
        for (String taskName : workflowInstance.getWorkflowGraph().keySet()) {
            List<TaskInstance> taskInstances = taskInstanceService.getTaskInstance(workflowInstanceId, taskName);

            if (!taskInstances.isEmpty()) {
                taskStatus.put(taskName, taskInstances.get(0).getStatus().toString());
            } else {
                taskStatus.put(taskName, TaskStatus.INIT.toString());
            }
        }

        System.out.println(taskStatus);

        try {
            modelAndView.addObject("taskStatus", mapper.writeValueAsString(taskStatus));

            modelAndView.addObject("workflowGraph", mapper.writeValueAsString(workflowInstance.getWorkflowGraph()));
        } catch (Exception ex) {
            modelAndView.addObject("error", ex.toString());
        }

        return modelAndView;
    }
}
