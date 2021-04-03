package com.anshishagua.constants;

/**
 * TaskStatus.java
 *
 * @author lixiao
 * @date 2021-03-28
 */

public enum TaskStatus {
    INIT,
    RUNNING,
    SUCCESS,
    FAILED;

    public static TaskStatus parse(String value) {
        for (TaskStatus status : values()) {
            if (status.toString().equals(value)) {
                return status;
            }
        }

        throw new RuntimeException();
    }
}
