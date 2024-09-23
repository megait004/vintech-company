package com.vintech.salary_management.VinTechCompany.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vintech.salary_management.VinTechCompany.models.AttendanceModel;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceModel, Long> {
    List<AttendanceModel> findByEmployeeId(Integer employeeId);
}