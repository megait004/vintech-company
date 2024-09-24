package com.vintech.salary_management.VinTechCompany.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vintech.salary_management.VinTechCompany.models.AttendanceModel;
import com.vintech.salary_management.VinTechCompany.repositories.AttendanceRepository;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    public void checkIn(AttendanceModel attendance) {
        attendanceRepository.save(attendance);
    }

    public void checkOut(AttendanceModel attendance) {
        attendanceRepository.save(attendance);
    }

}
