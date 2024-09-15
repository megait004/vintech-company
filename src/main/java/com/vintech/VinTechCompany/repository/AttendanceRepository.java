package com.vintech.VinTechCompany.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vintech.VinTechCompany.model.Attendance;
import com.vintech.VinTechCompany.model.Employee;
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByEmployeeAndAttendanceDateBetween(Employee employee, LocalDate startDate, LocalDate endDate);
    Attendance findByEmployeeAndAttendanceDate(Employee employee, LocalDate attendanceDate);
    boolean existsByEmployee(Employee employee);
    void deleteByEmployee(Employee employee);
}