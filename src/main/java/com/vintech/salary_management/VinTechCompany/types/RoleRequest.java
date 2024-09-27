package com.vintech.salary_management.VinTechCompany.types;

import java.math.BigDecimal;

public class RoleRequest {
    private String role;
    private BigDecimal hourlySalary;

    public BigDecimal getHourlySalary() {
        return hourlySalary;
    }

    public void setHourlySalary(BigDecimal hourlySalary) {
        this.hourlySalary = hourlySalary;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public RoleRequest() {
    }

    public RoleRequest(String role, String roleSlug, BigDecimal hourlySalary) {
        this.role = role;
        this.hourlySalary = hourlySalary;
    }

}
