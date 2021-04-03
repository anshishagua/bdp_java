package com.anshishagua.service;

import com.anshishagua.annotation.Param;
import com.anshishagua.annotation.Run;
import com.anshishagua.constants.TaskStatus;
import com.anshishagua.constants.WorkflowStatus;
import com.anshishagua.object.Application;
import com.anshishagua.object.TaskExecutor;
import com.anshishagua.object.TaskInstance;
import com.anshishagua.object.Workflow;
import com.anshishagua.object.WorkflowInstance;
import com.anshishagua.object.WorkflowParams;
import com.anshishagua.object.tasks.StartTask;
import com.anshishagua.object.tasks.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.omg.SendingContext.RunTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.net.URLClassLoader;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * WorkflowService.java
 *
 * @author lixiao
 * @date 2021-03-27
 */

@Service
public class WorkflowService {
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private WorkflowInstanceService workflowInstanceService;
    @Autowired
    private TaskInstanceService taskInstanceService;
    @Autowired
    private MailService mailService;

    private ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

    public WorkflowInstance runWorkflow(String applicationName, String workflowName, String workflowClass, String workflowParams) {
        Application application = applicationService.getLatestApplication(applicationName);

        String codePath = application.getPath();

        if (!codePath.startsWith("file://")) {
            codePath = "file://" + codePath;
        }

        try {
            URL [] urls = {new URL(codePath)};
            URLClassLoader classLoader = new URLClassLoader(urls);
            Class<?> clazz = classLoader.loadClass(workflowClass);
            Class<?> workflowParamClass = classLoader.loadClass(WorkflowParams.class.getName());

            Object workflow = clazz.newInstance();
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> map = objectMapper.readValue(workflowParams, Map.class);
            map.put("workflowName", workflowName);

            Object params = workflowParamClass.newInstance();

            Method method = workflowParamClass.getMethod("put", Object.class, Object.class);
            for (String key : map.keySet()) {
                method.invoke(params, key, map.get(key));
            }

            Class<? extends Annotation> runClass = (Class<? extends Annotation>) classLoader.loadClass(Run.class.getName());
            Class<? extends Annotation> paramAnnotation = (Class<? extends Annotation>) classLoader.loadClass(Param.class.getName());
            Method [] methods = clazz.getDeclaredMethods();

            for (Method m : methods) {
                if (m.isAnnotationPresent(runClass)) {
                    method = m;
                }
            }

            if (method == null) {
                throw new RuntimeException("No method found for workflow init method");
            }

            Parameter [] parameters = method.getParameters();
            Object [] args = new Object[parameters.length];

            for (int i = 0; i < parameters.length; ++i) {
                Parameter parameter = parameters[i];
                Annotation annotation = parameter.getAnnotation(paramAnnotation);

                if (annotation == null) {
                    throw new RuntimeException();
                }

                String paramName = (String) paramAnnotation.getMethod("value").invoke(annotation);
                args[i] = map.get(paramName);
            }

            Object result = method.invoke(workflow, args);
            String workflowJsonString = result.toString();
            Workflow workflowObject = Workflow.fromJson(workflowJsonString);

            LocalDateTime now = LocalDateTime.now();

            WorkflowInstance workflowInstance = new WorkflowInstance();
            workflowInstance.setWorkflowName(workflowName);
            workflowInstance.setWorkflowClass(workflowClass);
            workflowInstance.setWorkflowGraph(workflowObject.getWorkflowGraph());
            workflowInstance.setWorkflowParams(workflowObject.getWorkflowParams());
            workflowInstance.setApplicationName(applicationName);
            workflowInstance.setCreateTime(now);
            workflowInstance.setStartTime(now);
            workflowInstance.setStatus(WorkflowStatus.RUNNING);
            workflowInstanceService.addWorkflowInstance(workflowInstance);

            Map<String, TaskInstance> taskInstanceMap = new HashMap<>();

            for (Map.Entry<String, Task> entry : workflowObject.getWorkflowGraph().entrySet()) {
                Task task = entry.getValue();

                TaskInstance taskInstance = new TaskInstance();
                taskInstance.setCreateTime(now);
                taskInstance.setStatus(TaskStatus.INIT);
                taskInstance.setTaskType(task.getTaskType());
                taskInstance.setWorkflowInstanceId(workflowInstance.getId());
                taskInstance.setTaskName(task.getTaskName());
                taskInstance.setTaskParams(task.getTaskParams());
                taskInstance.setTaskClass(task.getTaskClass());

                taskInstanceService.addTaskInstance(taskInstance);
                taskInstanceMap.put(task.getTaskName(), taskInstance);
            }

            Task startTask = workflowInstance.getWorkflowGraph().get(StartTask.TASK_NAME);

            TaskExecutor executor = new TaskExecutor(workflowInstance, taskInstanceMap.get(startTask.getTaskName()), startTask, this, taskInstanceService, workflowInstanceService, threadPoolExecutor, mailService);
            threadPoolExecutor.execute(executor);
            return workflowInstance;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
