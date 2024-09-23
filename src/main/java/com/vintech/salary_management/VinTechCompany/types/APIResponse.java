package com.vintech.salary_management.VinTechCompany.types;

public class APIResponse {
    private boolean success;
    private String message;
    private Object data;

    public APIResponse(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public APIResponse(boolean success, String message) {
        this(success, message, null);
    }

    public static APIResponse success(String message, Object data) {
        return new APIResponse(true, message, data);
    }

    public static APIResponse success(String message) {
        return new APIResponse(true, message);
    }

    public static APIResponse error(String message) {
        return new APIResponse(false, message);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}
