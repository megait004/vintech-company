package com.vintech.salary_management.VinTechCompany.services;

import java.sql.Time;

public class AttendanceRequest {
    private Time checkIn;
    private Time checkOut;

    public Time getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Time checkIn) {
        this.checkIn = checkIn;
    }

    public Time getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Time checkOut) {
        this.checkOut = checkOut;
    }

    public AttendanceRequest() {

    }

    public AttendanceRequest(Time checkIn, Time checkOut) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }
}
