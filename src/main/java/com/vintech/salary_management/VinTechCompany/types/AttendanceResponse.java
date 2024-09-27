package com.vintech.salary_management.VinTechCompany.types;

public class AttendanceResponse {
    private String checkIn;
    private String checkOut;
    private String date;
    private Long totalHours;
    private String accountUsername;

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(Long totalHours) {
        this.totalHours = totalHours;
    }

    public String getAccountUsername() {
        return accountUsername;
    }

    public void setAccountUsername(String accountUsername) {
        this.accountUsername = accountUsername;
    }

    public AttendanceResponse() {
    }

    public AttendanceResponse(String checkIn, String checkOut, String date, Long totalHours, String accountUsername) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.date = date;
        this.totalHours = totalHours;
        this.accountUsername = accountUsername;
    }

}
