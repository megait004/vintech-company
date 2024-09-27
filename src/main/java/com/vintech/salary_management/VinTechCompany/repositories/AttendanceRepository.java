package com.vintech.salary_management.VinTechCompany.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vintech.salary_management.VinTechCompany.models.AccountModel;
import com.vintech.salary_management.VinTechCompany.models.AttendanceModel;

public interface AttendanceRepository extends JpaRepository<AttendanceModel, Long> {
    AttendanceModel findByDate(String today);

    AttendanceModel findByaccountUsername(AccountModel accountUsername);

    List<AttendanceModel> findAllByaccountUsername(AccountModel accountUsername);
}
