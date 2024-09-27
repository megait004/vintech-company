package com.vintech.salary_management.VinTechCompany.config;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.vintech.salary_management.VinTechCompany.models.AccountModel;
import com.vintech.salary_management.VinTechCompany.models.RoleModel;
import com.vintech.salary_management.VinTechCompany.repositories.AccountRepository;
import com.vintech.salary_management.VinTechCompany.repositories.RoleRepository;
import com.vintech.salary_management.VinTechCompany.services.AuthService;
import com.vintech.salary_management.VinTechCompany.services.SlugService;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthService authService;
    @Autowired
    private SlugService slugService;

    @Override
    public void run(String... args) throws Exception {
        if (accountRepository.findByUsername("admin") == null) {
            RoleModel adminRole = roleRepository.findByRole("ADMIN");
            if (adminRole == null) {
                adminRole = new RoleModel();
                adminRole.setRole("ADMIN");
                String roleSlug = slugService.createSlug("ADMIN");
                adminRole.setroleSlug(roleSlug);
                adminRole.setHourlySalary(new BigDecimal("999999999"));
                adminRole = roleRepository.save(adminRole);
            }
            AccountModel adminAccount = new AccountModel();
            adminAccount.setUsername("admin");
            adminAccount.setPassword(authService.hashPassword("admin"));
            adminAccount.setEmail("admin@giapzech.ovfteam.com");
            adminAccount.setRole(adminRole);
            adminAccount.setAvatarId("1");
            accountRepository.save(adminAccount);
        }
    }
}