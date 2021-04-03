package com.anshishagua.controller.api;

import java.util.HashMap;
import java.util.Map;

/**
 * ApiResponse.java
 *
 * @author lixiao
 * @date 2021-03-26
 */

public class ApiResponse {
    public enum Status {
        SUCCESS, FAILED;
    }

    private final Status status;
    private final String errorMessage;
    private final Map<String, Object> data;

    public ApiResponse(Status status, String errorMessage, Map<String, Object> data) {
        this.status = status;
        this.errorMessage = errorMessage;
        this.data = data;
    }

    public static ApiResponse failed(String errorMessage) {
        return new ApiResponse(Status.FAILED, errorMessage, new HashMap<>());
    }

    public static ApiResponse success() {
        return new ApiResponse(Status.SUCCESS, "", new HashMap<>());
    }

    public static ApiResponse success(Map<String, Object> data) {
        return new ApiResponse(Status.SUCCESS, "", data);
    }

    public Status getStatus() {
        return status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Map<String, Object> getData() {
        return data;
    }
}
