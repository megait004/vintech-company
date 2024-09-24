package com.vintech.salary_management.VinTechCompany.types;

public class OtpRequest {
    private String email;
    private String subject;
    private String username;

    public OtpRequest(String email, String username) {
        this.email = email;
        this.subject = "Mã OTP đăng ký tài khoản";
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}