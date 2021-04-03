package com.anshishagua.object.executors;

import com.anshishagua.object.TaskLogger;
import com.anshishagua.annotation.Run;
import com.anshishagua.annotation.TaskParam;
import com.anshishagua.object.TaskParams;
import com.anshishagua.object.tasks.Task;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * JavaExecutor.java
 *
 * @author lixiao
 * @date 2021-04-01
 */

public class JavaTaskExecutor extends AbstractTaskExecutor {
    public JavaTaskExecutor(Task task, TaskParams taskParams, long workflowInstanceId, long taskInstanceId) {
        super(task, taskParams, workflowInstanceId, taskInstanceId);
    }

    @Override
    public void doRun() throws Exception {
        Class<?> taskClass = Class.forName(getTask().getTaskClass());
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
            field.set(logObject, getWorkflowInstanceId());
            field = loggerClass.getDeclaredField("taskInstanceId");
            field.setAccessible(true);
            field.set(logObject, getTaskInstanceId());
            loggerClass.getDeclaredMethod("init").invoke(logObject);

            loggerField.set(taskObject, logObject);
        }

        Method[] methods = taskClass.getDeclaredMethods();
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

        Parameter[] parameters = taskMethod.getParameters();
        Object [] args = new Object[parameters.length];

        for (int i = 0; i < parameters.length; ++i) {
            Parameter parameter = parameters[i];
            Annotation annotation = parameter.getAnnotation(paramAnnotation);

            if (annotation == null) {
                throw new RuntimeException();
            }

            String paramName = (String) paramAnnotation.getMethod("value").invoke(annotation);
            args[i] = getTask().getTaskParams().get(paramName);

            if (args[i] == null) {
                throw new RuntimeException("No value for param " + paramName);
            }
        }

        taskMethod.invoke(taskObject, args);
    }
}
