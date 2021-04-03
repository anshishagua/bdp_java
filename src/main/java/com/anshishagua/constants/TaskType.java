package com.anshishagua.constants;

/**
 * TaskType.java
 *
 * @author lixiao
 * @date 2021-03-27
 */

public enum TaskType {
    START,
    END,
    JOIN,
    JAVA,
    PYTHON,
    SHELL;

    public static TaskType parse(String value) {
        for (TaskType taskType : values()) {
            if (taskType.toString().equals(value)) {
                return taskType;
            }
        }

        throw new RuntimeException("Unknown taskType: " + value);
    }
}
