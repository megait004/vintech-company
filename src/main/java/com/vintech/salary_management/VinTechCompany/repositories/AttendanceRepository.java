package com.vintech.salary_management.VinTechCompany.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vintech.salary_management.VinTechCompany.models.AttendanceModel;

public interface AttendanceRepository extends JpaRepository<AttendanceModel, Long> {

}
