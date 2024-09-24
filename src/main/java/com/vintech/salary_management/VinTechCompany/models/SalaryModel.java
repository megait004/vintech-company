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
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private AccountModel account;

    @Column(name = "total_hours")
    private BigDecimal total_hours;

    @Column(name = "total_salary")
    private BigDecimal total_salary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccountModel getAccount() {
        return account;
    }

    public void setAccount(AccountModel account) {
        this.account = account;
    }

    public BigDecimal getTotal_hours() {
        return total_hours;
    }

    public void setTotal_hours(BigDecimal total_hours) {
        this.total_hours = total_hours;
    }

    public BigDecimal getTotal_salary() {
        return total_salary;
    }

    public void setTotal_salary(BigDecimal total_salary) {
        this.total_salary = total_salary;
    }

    public SalaryModel() {
    }

    public SalaryModel(Long id, AccountModel account, BigDecimal total_hours, BigDecimal total_salary) {
        this.id = id;
        this.account = account;
        this.total_hours = total_hours;
        this.total_salary = total_salary;
    }

}
