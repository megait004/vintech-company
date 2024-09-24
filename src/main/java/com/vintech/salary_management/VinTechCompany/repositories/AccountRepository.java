package com.vintech.salary_management.VinTechCompany.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vintech.salary_management.VinTechCompany.models.AccountModel;

@Repository
public interface AccountRepository extends JpaRepository<AccountModel, Long> {
    AccountModel findByUsername(String username);

    public Object findByEmail(String email);
}
