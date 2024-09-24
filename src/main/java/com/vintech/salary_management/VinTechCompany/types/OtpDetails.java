package com.vintech.salary_management.VinTechCompany.types;

import java.time.LocalDateTime;

public class OtpDetails {
    private LocalDateTime expiresAt;
    private String otp;

    public OtpDetails(LocalDateTime expiresAt, String otp) {
        this.expiresAt = expiresAt;
        this.otp = otp;
    }

    public String getOtp() {
        return otp;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }
}
