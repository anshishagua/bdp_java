package com.anshishagua.controller.ui;

import com.anshishagua.object.TaskInstance;
import com.anshishagua.service.TaskInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * TaskController.java
 *
 * @author lixiao
 * @date 2021-03-28
 */

@Controller
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskInstanceService taskInstanceService;

    @RequestMapping("/details")
    public ModelAndView details(@RequestParam("workflowInstanceId") long workflowInstanceId,
                                @RequestParam("taskName") String taskName) {
        ModelAndView modelAndView = new ModelAndView("task_details");

        List<TaskInstance> list = taskInstanceService.getTaskInstance(workflowInstanceId, taskName);

        modelAndView.addObject("task_instances", list);

        return modelAndView;
    }

    @RequestMapping("/logs")
    public ModelAndView logs(@RequestParam("taskInstanceId") long taskInstanceId) {
        TaskInstance taskInstance = taskInstanceService.getTaskInstance(taskInstanceId);

        String logPath = String.format("/tmp/workflow-%d-task-%d.log",
                taskInstance.getWorkflowInstanceId(), taskInstanceId);
        List<String> logs = new ArrayList<>();
        try {
            logs.addAll(Files.readAllLines(Paths.get(logPath)));
        } catch (IOException ex) {
            logs.add(ex.toString());
        }

        ModelAndView modelAndView = new ModelAndView("task_logs");
        modelAndView.addObject("logs", logs);

        return modelAndView;
    }
}
