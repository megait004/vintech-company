package com.vintech.salary_management.VinTechCompany.models;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "attendance")
public class AttendanceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private AccountModel account;

    @Column(name = "check_in")
    private Time check_in;

    @Column(name = "check_out")
    private Time check_out;

    @Column(name = "date")
    private Date date;

    @Column(name = "total_hours")
    private BigDecimal total_hours;

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

    public Time getCheck_in() {
        return check_in;
    }

    public void setCheck_in(Time check_in) {
        this.check_in = check_in;
    }

    public Time getCheck_out() {
        return check_out;
    }

    public void setCheck_out(Time check_out) {
        this.check_out = check_out;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getTotal_hours() {
        return total_hours;
    }

    public void setTotal_hours(BigDecimal total_hours) {
        this.total_hours = total_hours;
    }
}
