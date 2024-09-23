package com.vintech.salary_management.VinTechCompany.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vintech.salary_management.VinTechCompany.models.NotificationModel;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationModel, Long> {
    List<NotificationModel> findByEmployeeId(Integer employeeId);
}