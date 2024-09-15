package com.vintech.VinTechCompany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vintech.VinTechCompany.dto.ApiResponse;
import com.vintech.VinTechCompany.model.Attendance;
import com.vintech.VinTechCompany.services.AttendanceService;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/check-in/{employeeId}")
    public ResponseEntity<ApiResponse<Attendance>> checkIn(@PathVariable Long employeeId) {
        Attendance attendance = attendanceService.checkIn(employeeId);
        if (attendance == null) {
            return ResponseEntity.status(403).body(new ApiResponse<>(false, "CHECK-IN FAILED", null));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "CHECK-IN SUCCESSFUL", attendance));
    }

    @GetMapping("/get-attendance-today/{employeeId}")
    public ResponseEntity<ApiResponse<Attendance>> getAttendanceToday(@PathVariable Long employeeId) {
        Attendance attendance = attendanceService.getAttendanceToday(employeeId);
        if (attendance == null) {
            return ResponseEntity.status(403).body(new ApiResponse<>(false, "ATTENDANCE NOT FOUND", null));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "ATTENDANCE RETRIEVED SUCCESSFULLY", attendance));
    }

    @PutMapping("/check-out/{attendanceId}")
    public ResponseEntity<ApiResponse<Attendance>> checkOut(@PathVariable Long attendanceId) {
        Attendance attendance = attendanceService.checkOut(attendanceId);
        if (attendance == null) {
            return ResponseEntity.status(403).body(new ApiResponse<>(false, "CHECK-OUT FAILED", null));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "CHECK-OUT SUCCESSFUL", attendance));
    }
}
