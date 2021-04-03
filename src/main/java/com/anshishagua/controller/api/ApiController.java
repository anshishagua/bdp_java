package com.anshishagua.controller.api;

import com.anshishagua.constants.TaskStatus;
import com.anshishagua.constants.TaskType;
import com.anshishagua.object.TaskInstance;
import com.anshishagua.object.User;
import com.anshishagua.object.WorkflowInstance;
import com.anshishagua.service.ApplicationService;
import com.anshishagua.service.TaskInstanceService;
import com.anshishagua.service.UserService;
import com.anshishagua.service.WorkflowInstanceService;
import com.anshishagua.service.WorkflowService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * ApiController.java
 *
 * @author lixiao
 * @date 2021-03-26
 */

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private WorkflowService workflowService;
    @Autowired
    private TaskInstanceService taskInstanceService;
    @Autowired
    private WorkflowInstanceService workflowInstanceService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ApiResponse login(@RequestParam("username") String username,
                             @RequestParam("password") String password) {
        User user = userService.getUser(username, password);

        if (user == null) {
            return ApiResponse.failed("Username not found");
        }

        return ApiResponse.success();
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ApiResponse register(@RequestParam("username") String username,
                                @RequestParam("password") String password,
                                @RequestParam("gender") String gender) {
        User user = userService.getByName(username);

        if (user != null) {
            return ApiResponse.failed("User " + username + " exists");
        }

        userService.addUser(username, password, gender);

        return ApiResponse.success();
    }

    public ApiResponse logout() {
        return null;
    }

    @RequestMapping(value = "/application/{applicationId}", method = RequestMethod.DELETE)
    public ApiResponse deleteApplication(@PathVariable int applicationId) {
        int result = applicationService.deleteApplication(applicationId);

        if (result == 1) {
            return ApiResponse.success();
        }

        return ApiResponse.failed("Not found application id " + applicationId);
    }

    @RequestMapping(value = "/application/deploy", method = RequestMethod.POST)
    public ApiResponse deploy(
            @RequestParam("applicationName") String applicationName,
            @RequestParam("message") String message,
            @RequestParam("file") MultipartFile file) {
        try {
            String destPath = String.format(
                    "/tmp/application-%s.zip",
                    UUID.randomUUID().toString().replace("-", ""));

            Files.copy(file.getInputStream(), Paths.get(destPath));
            applicationService.addApplication(applicationName, message, destPath);
        } catch (IOException ex) {
            return ApiResponse.failed(ex.toString());
        }

        return ApiResponse.success();
    }

    @RequestMapping(value = "/workflow/kill", method = RequestMethod.POST)
    public ApiResponse killWorkflow(@RequestParam("workflowInstanceId") long workflowInstanceId) {
        WorkflowInstance workflowInstance = workflowInstanceService.getWorkflowInstance(workflowInstanceId);

        if (workflowInstance == null) {
            return ApiResponse.failed("Workflow " + workflowInstanceId + " not found");
        }

        return ApiResponse.success();
    }

    @RequestMapping(value = "/workflow/run", method = RequestMethod.POST)
    public ApiResponse runWorkflow(@RequestParam("applicationName") String applicationName,
                                   @RequestParam("workflowName") String workflowName,
                                   @RequestParam("workflowClass") String workflowClass,
                                   @RequestParam("workflowParams") String workflowParams) {
        WorkflowInstance workflowInstance = null;

        try {
            workflowInstance = workflowService.runWorkflow(applicationName, workflowName, workflowClass, workflowParams);
        } catch (Exception ex) {
            return ApiResponse.failed(ex.toString());
        }

        Map<String, Object> map = new HashMap<>();
        map.put("workflow", workflowInstance.toJson());

        ApiResponse response = new ApiResponse(ApiResponse.Status.SUCCESS, "", map);
        return response;
    }

    @RequestMapping(value = "/task/run", method = RequestMethod.POST)
    public ApiResponse runTask(@RequestParam("workflowInstanceId") long workflowInstanceId,
                               @RequestParam("taskName") String taskName) {

        return null;
    }

    @RequestMapping(value = "/task/rerun", method = RequestMethod.POST)
    public ApiResponse rerunTask(
            @RequestParam("workflowInstanceId") long workflowInstanceId,
            @RequestParam("taskName") String taskName,
            @RequestParam("taskType") String taskType,
            @RequestParam("taskClass") String taskClass,
            @RequestParam("taskParams") String taskParams,
            @RequestParam("skipCurrent") boolean skipCurrent,
            @RequestParam("runNext") boolean runNext) {
        TaskInstance taskInstance = new TaskInstance();
        taskInstance.setCreateTime(LocalDateTime.now());

        if (skipCurrent) {
            taskInstance.setStatus(TaskStatus.SUCCESS);
        } else {
            taskInstance.setStatus(TaskStatus.RUNNING);
        }
        taskInstance.setTaskType(TaskType.parse(taskType));
        taskInstance.setWorkflowInstanceId(workflowInstanceId);
        taskInstance.setTaskName(taskName);
        ObjectMapper mapper = new ObjectMapper();
        try {
            taskInstance.setTaskParams(mapper.readValue(taskParams, Map.class));
        } catch (Exception ex) {
            return ApiResponse.failed(ex.toString());
        }
        taskInstance.setTaskClass(taskClass);

        taskInstanceService.addTaskInstance(taskInstance);

        System.out.println(taskParams);
        System.out.println(runNext);
        return ApiResponse.success();
    }

    @RequestMapping(value = "/task/kill", method = RequestMethod.POST)
    public ApiResponse killTask(@RequestParam("taskInstanceId") long taskInstanceId) {
        TaskInstance taskInstance = taskInstanceService.getTaskInstance(taskInstanceId);
        taskInstance.setStatus(TaskStatus.FAILED);
        taskInstance.setEndTime(LocalDateTime.now());

        taskInstanceService.updateTaskInstance(taskInstance);

        return ApiResponse.success();
    }
}
