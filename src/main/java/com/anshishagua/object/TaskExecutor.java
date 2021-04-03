package com.anshishagua.object;

import com.anshishagua.annotation.Run;
import com.anshishagua.annotation.TaskParam;
import com.anshishagua.constants.TaskStatus;
import com.anshishagua.constants.TaskType;
import com.anshishagua.constants.WorkflowStatus;
import com.anshishagua.object.tasks.JavaTask;
import com.anshishagua.object.tasks.Task;
import com.anshishagua.service.MailService;
import com.anshishagua.service.TaskInstanceService;
import com.anshishagua.service.WorkflowInstanceService;
import com.anshishagua.service.WorkflowService;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * TaskExecutor.java
 *
 * @author lixiao
 * @date 2021-03-29
 */

public class TaskExecutor implements Runnable {
    private WorkflowInstance workflowInstance;
    private TaskInstance taskInstance;
    private Task task;
    private WorkflowService workflowService;
    private WorkflowInstanceService workflowInstanceService;
    private TaskInstanceService taskInstanceService;
    private ExecutorService executorService;
    private MailService mailService;

    public TaskExecutor(WorkflowInstance workflowInstance,
                        TaskInstance taskInstance,
                        Task task,
                        WorkflowService workflowService,
                        TaskInstanceService taskInstanceService,
                        WorkflowInstanceService workflowInstanceService,
                        ExecutorService executorService,
                        MailService mailService) {
        this.workflowInstance = workflowInstance;
        this.taskInstance = taskInstance;
        this.task = task;
        this.workflowService = workflowService;
        this.workflowInstanceService = workflowInstanceService;
        this.taskInstanceService = taskInstanceService;
        this.executorService = executorService;
        this.mailService = mailService;
    }

    public WorkflowInstance getWorkflowInstance() {
        return workflowInstance;
    }

    public void setWorkflowInstance(WorkflowInstance workflowInstance) {
        this.workflowInstance = workflowInstance;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public WorkflowService getWorkflowService() {
        return workflowService;
    }

    public void setWorkflowService(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    public TaskInstanceService getTaskInstanceService() {
        return taskInstanceService;
    }

    public void setTaskInstanceService(TaskInstanceService taskInstanceService) {
        this.taskInstanceService = taskInstanceService;
    }

    @Override
    public void run() {
        TaskLogger logger = new TaskLogger(taskInstance.getTaskName());
        logger.setTaskInstanceId(taskInstance.getId());
        logger.setWorkflowInstanceId(workflowInstance.getId());
        List<TaskInstance> taskInstances = taskInstanceService.getTaskInstance(workflowInstance.getId(), task.getTaskName());

        if (taskInstances == null || taskInstances.isEmpty()) {
            throw new RuntimeException();
        }

        TaskInstance taskInstance = taskInstances.get(0);

        taskInstance.setStatus(TaskStatus.RUNNING);
        taskInstance.setStartTime(LocalDateTime.now());
        taskInstanceService.updateTaskInstance(taskInstance);

        TaskStatus taskStatus = TaskStatus.SUCCESS;

        try {
            if (task.getTaskType() == TaskType.JAVA) {
                JavaTask javaTask = (JavaTask) task;
                Class<?> taskClass = Class.forName(javaTask.getTaskClass());
                Object taskObject = taskClass.newInstance();

                Field[] fields = taskClass.getDeclaredFields();
                Field loggerField = null;
                Object logObject = null;

                for (Field field : fields) {
                    if (field.getType().getName().equals(TaskLogger.class.getName())) {
                        loggerField = field;
                        loggerField.setAccessible(true);
                    }
                }

                if (loggerField != null) {
                    Class loggerClass = Class.forName(TaskLogger.class.getName());
                    Constructor constructor = loggerClass.getConstructor(String.class);
                    logObject = constructor.newInstance(taskClass.getName());
                    Field field = loggerClass.getDeclaredField("workflowInstanceId");
                    field.setAccessible(true);
                    field.set(logObject, workflowInstance.getId());
                    field = loggerClass.getDeclaredField("taskInstanceId");
                    field.setAccessible(true);
                    field.set(logObject, taskInstance.getId());
                    loggerClass.getDeclaredMethod("init").invoke(logObject);

                    loggerField.set(taskObject, logObject);
                }

                Method [] methods = taskClass.getDeclaredMethods();
                Method taskMethod = null;
                Class runAnnotation = Class.forName(Run.class.getName());
                Class paramAnnotation = Class.forName(TaskParam.class.getName());

                for (Method method : methods) {
                    if (method.isAnnotationPresent(runAnnotation)) {
                        taskMethod = method;
                        break;
                    }
                }

                if (taskMethod == null) {
                    throw new RuntimeException();
                }

                Parameter [] parameters = taskMethod.getParameters();
                Object [] args = new Object[parameters.length];

                for (int i = 0; i < parameters.length; ++i) {
                    Parameter parameter = parameters[i];
                    Annotation annotation = parameter.getAnnotation(paramAnnotation);

                    if (annotation == null) {
                        throw new RuntimeException();
                    }

                    String paramName = (String) paramAnnotation.getMethod("value").invoke(annotation);
                    args[i] = task.getTaskParams().get(paramName);

                    if (args[i] == null) {
                        throw new RuntimeException("No value for param " + paramName);
                    }
                }

                taskMethod.invoke(taskObject, args);
            } else {
                Thread.sleep(5000);
            }
        } catch (Exception ex) {
            taskStatus = TaskStatus.FAILED;
            ex.printStackTrace();
            logger.error("Failed to run task:" + ex.toString());
        }

        taskInstance.setStatus(taskStatus);
        taskInstance.setEndTime(LocalDateTime.now());
        taskInstanceService.updateTaskInstance(taskInstance);

        Map<String, Task> workflowGraph = workflowInstance.getWorkflowGraph();

        for (String nextTaskName : workflowGraph.get(getTask().getTaskName()).getNextTaskNames()) {
            Task nextTask = workflowGraph.get(nextTaskName);

            executorService.execute(new TaskExecutor(workflowInstance, taskInstance, nextTask, workflowService, taskInstanceService, workflowInstanceService, executorService, mailService));
        }

        if (task.getTaskType() == TaskType.END) {
            workflowInstance.setStatus(WorkflowStatus.SUCCESS);
            workflowInstance.setEndTime(LocalDateTime.now());
            workflowInstanceService.updateWorkflowInstance(workflowInstance);
            String subject = String.format("Workflow %s run %s", workflowInstance.getId(), WorkflowStatus.SUCCESS);
            String content = String.format("Workflow %d runs %s at %s", workflowInstance.getId(), WorkflowStatus.SUCCESS, LocalDateTime.now());
            mailService.sendMail(subject, "anshishagua@126.com", content);
        }
    }
}
