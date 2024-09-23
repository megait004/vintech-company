package com.vintech.salary_management.VinTechCompany.models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "salary")
public class SalaryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private EmployeeModel employee;

    @Column(name = "hourly_salary")
    private BigDecimal hourly_salary;

    @Column(name = "total_salary")
    private BigDecimal total_salary;

    public BigDecimal getTotal_salary() {
        return total_salary;
    }

    public void setTotal_salary(BigDecimal total_salary) {
        this.total_salary = total_salary;
    }

    public BigDecimal getHourly_salary() {
        return hourly_salary;
    }

    public void setHourly_salary(BigDecimal hourly_salary) {
        this.hourly_salary = hourly_salary;
    }

    public EmployeeModel getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeModel employee) {
        this.employee = employee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
