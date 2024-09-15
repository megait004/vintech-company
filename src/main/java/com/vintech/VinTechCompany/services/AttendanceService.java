package com.vintech.VinTechCompany.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vintech.VinTechCompany.model.Attendance;
import com.vintech.VinTechCompany.model.Employee;
import com.vintech.VinTechCompany.repository.AttendanceRepository;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeService employeeService;

    public Attendance checkIn(Long employeeId) {
        try {
            Employee employee = employeeService.getEmployeeById(employeeId)
                    .orElse(null);
            if (employee == null) {
                return null;
            }

            Attendance attendance = new Attendance();
            attendance.setEmployee(employee);
            attendance.setAttendanceDate(LocalDate.now());
            attendance.setCheckInTime(LocalTime.now());

            return attendanceRepository.save(attendance);
        } catch (Exception e) {
            System.out.println("Failed to check in");
            return null;
        }
    }

    public Attendance checkOut(Long attendanceId) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElse(null);
        if (attendance == null) {
            System.out.println("Attendance record not found");
            return null;
        }

        if (attendance.getCheckOutTime() != null) {
            System.out.println("Employee has already checked out");
            return null;
        }

        LocalTime checkOutTime = LocalTime.now();
        attendance.setCheckOutTime(checkOutTime);

        long minutes = ChronoUnit.MINUTES.between(attendance.getCheckInTime(), checkOutTime);
        BigDecimal workHours = BigDecimal.valueOf(minutes).divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);
        attendance.setWorkHours(workHours);

        return attendanceRepository.save(attendance);
    }

    public Attendance getAttendanceToday(Long employeeId) {
        try {
            Employee employee = employeeService.getEmployeeByPositionId(employeeId)
                    .orElse(null);
            if (employee == null) {
                return null;
            }
            return attendanceRepository.findByEmployeeAndAttendanceDate(employee, LocalDate.now());
        } catch (Exception e) {
            System.out.println("Failed to get attendance today");
            return null;
        }
    }

    public boolean existsAttendanceForPosition(Long positionId) {
        try {
            Employee employee = employeeService.getEmployeeByPositionId(positionId)
                    .orElse(null);
            if (employee == null) {
                return false;
            }
            return attendanceRepository.existsByEmployee(employee);
        } catch (Exception e) {
            return false;
        }
    }

    public void deleteAttendanceByPositionId(Long positionId) {
        try {
            Employee employee = employeeService.getEmployeeByPositionId(positionId)
                    .orElse(null);
            if (employee == null) {
                return;
            }
            attendanceRepository.deleteByEmployee(employee);
        } catch (Exception e) {
            System.out.println("Failed to delete attendance records");
        }
    }
}
