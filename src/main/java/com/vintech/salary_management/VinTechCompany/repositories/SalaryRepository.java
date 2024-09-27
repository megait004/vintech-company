package com.vintech.salary_management.VinTechCompany.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vintech.salary_management.VinTechCompany.models.AccountModel;
import com.vintech.salary_management.VinTechCompany.models.SalaryModel;

@Repository
public interface SalaryRepository extends JpaRepository<SalaryModel, Long> {
    SalaryModel findByAccount(AccountModel account);
}
