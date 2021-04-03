package com.anshishagua.constants;

/**
 * WorkflowStatus.java
 *
 * @author lixiao
 * @date 2021-03-27
 */

public enum WorkflowStatus {
    RUNNING,
    SUCCESS,
    PENDING,
    FAILED;

    public static WorkflowStatus parse(String value) {
        for (WorkflowStatus status : values()) {
            if (status.toString().equals(value)) {
                return status;
            }
        }

        throw new RuntimeException();
    }
}
