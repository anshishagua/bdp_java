package com.anshishagua.object.tasks;

import com.anshishagua.constants.TaskType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * TaskUtils.java
 *
 * @author lixiao
 * @date 2021-03-27
 */

public class TaskUtils {
    public static Task fromJson(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = null;

        try {
            map = objectMapper.readValue(json, Map.class);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        TaskType taskType = TaskType.parse((String) map.get("taskType"));
        Class<?> clazz = null;

        switch (taskType) {
            case START:
                clazz = StartTask.class;
                break;
            case END:
                clazz = EndTask.class;
                break;
            case JAVA:
                clazz = JavaTask.class;
                break;
            case JOIN:
                clazz = JoinTask.class;
                break;
            default:
                throw new RuntimeException();
        }

        try {
            return (Task) objectMapper.readValue(json, clazz);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
