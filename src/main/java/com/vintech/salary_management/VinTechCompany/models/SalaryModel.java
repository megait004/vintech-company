package com.vintech.salary_management.VinTechCompany.models;

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
    @JoinColumn(name = "accountUsername", referencedColumnName = "username")
    private AccountModel account;

    @Column(name = "totalHours")
    private Long totalHours;

    @Column(name = "totalSalary")
    private Long totalSalary;

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

    public Long getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(Long totalHours) {
        this.totalHours = totalHours;
    }

    public Long getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(Long totalSalary) {
        this.totalSalary = totalSalary;
    }

    public SalaryModel() {
    }

    public SalaryModel(Long id, AccountModel account, Long totalHours, Long totalSalary) {
        this.id = id;
        this.account = account;
        this.totalHours = totalHours;
        this.totalSalary = totalSalary;
    }

}
