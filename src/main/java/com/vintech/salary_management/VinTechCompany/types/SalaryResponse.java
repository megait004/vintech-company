package com.vintech.salary_management.VinTechCompany.types;

public class SalaryResponse {
    private String username;
    private String role;
    private String totalHours;
    private String totalSalary;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(String totalHours) {
        this.totalHours = totalHours;
    }

    public String getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(String totalSalary) {
        this.totalSalary = totalSalary;
    }

    public SalaryResponse() {

    }

    public SalaryResponse(String username, String role, String totalHours, String totalSalary) {
        this.username = username;
        this.role = role;
        this.totalHours = totalHours;
        this.totalSalary = totalSalary;
    }
}
