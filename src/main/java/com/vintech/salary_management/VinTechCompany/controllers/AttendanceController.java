package com.vintech.salary_management.VinTechCompany.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vintech.salary_management.VinTechCompany.annotations.IsAdmin;
import com.vintech.salary_management.VinTechCompany.models.AttendanceModel;
import com.vintech.salary_management.VinTechCompany.repositories.AttendanceRepository;
import com.vintech.salary_management.VinTechCompany.services.AttendanceService;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @IsAdmin
    @GetMapping
    public ResponseEntity<List<AttendanceModel>> getAllAttendance() {
        List<AttendanceModel> attendance = attendanceRepository.findAll();
        return ResponseEntity.ok(attendance);
    }

    @PostMapping("/check-in")
    public ResponseEntity<String> checkIn(@RequestBody AttendanceModel attendance) {
        attendanceService.checkIn(attendance);
        return ResponseEntity.ok("Check-in successful");
    }

    @PostMapping("/check-out")
    public ResponseEntity<String> checkOut(@RequestBody AttendanceModel attendance) {
        attendanceService.checkOut(attendance);
        return ResponseEntity.ok("Check-out successful");
    }

    @IsAdmin
    @GetMapping("/{id}")
    public ResponseEntity<AttendanceModel> getAttendanceById(@PathVariable Long id) {
        AttendanceModel attendance = attendanceRepository.findById(id).orElse(null);
        return ResponseEntity.ok(attendance);
    }
}
