package com.vintech.VinTechCompany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vintech.VinTechCompany.model.Attendance;
import com.vintech.VinTechCompany.services.AttendanceService;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/check-in/{employeeId}")
    public ResponseEntity<Attendance> checkIn(@PathVariable Long employeeId) {
        Attendance attendance = attendanceService.checkIn(employeeId);
        return ResponseEntity.ok(attendance);
    }

    @GetMapping("/get-attendance-today/{employeeId}")
    public ResponseEntity<Attendance> getAttendanceToday(@PathVariable Long employeeId) {
        Attendance attendance = attendanceService.getAttendanceToday(employeeId);
        return ResponseEntity.ok(attendance);
    }

    @PutMapping("/check-out/{attendanceId}")
    public ResponseEntity<Attendance> checkOut(@PathVariable Long attendanceId) {
        Attendance attendance = attendanceService.checkOut(attendanceId);
        return ResponseEntity.ok(attendance);
    }
}
