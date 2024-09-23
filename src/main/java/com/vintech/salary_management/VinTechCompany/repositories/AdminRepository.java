package com.vintech.salary_management.VinTechCompany.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vintech.salary_management.VinTechCompany.models.AdminModel;

@Repository
public interface AdminRepository extends JpaRepository<AdminModel, Long> {
    AdminModel findByUsername(String username);

    AdminModel findByEmail(String email);
}