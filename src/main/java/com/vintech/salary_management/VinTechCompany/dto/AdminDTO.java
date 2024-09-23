package com.vintech.salary_management.VinTechCompany.dto;

public class AdminDTO {
    private String username;
    private String email;
    private String app_key;

    public AdminDTO(String username, String email, String app_key) {
        this.username = username;
        this.email = email;
        this.app_key = app_key;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApp_key() {
        return app_key;
    }

    public void setApp_key(String app_key) {
        this.app_key = app_key;
    }
}