package com.vintech.salary_management.VinTechCompany.models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "role")
public class RoleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hourly_salary")
    private BigDecimal hourlySalary;

    @Column(name = "roleSlug")
    private String roleSlug;

    @Column(name = "role", unique = true)
    private String role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getroleSlug() {
        return roleSlug;
    }

    public void setroleSlug(String roleSlug) {
        this.roleSlug = roleSlug;
    }

    public RoleModel() {
    }

    public RoleModel(String role) {
        this(BigDecimal.ZERO, role);
    }

    public RoleModel(BigDecimal hourlySalary, String role) {
        this.hourlySalary = hourlySalary;
        this.role = role;
    }
}
