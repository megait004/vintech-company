package com.vintech.salary_management.VinTechCompany.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vintech.salary_management.VinTechCompany.models.RoleModel;
import com.vintech.salary_management.VinTechCompany.models.SalaryModel;
import com.vintech.salary_management.VinTechCompany.repositories.SalaryRepository;

@Service
public class SalaryService {

    @Autowired
    private SalaryRepository salaryRepository;

    public List<SalaryModel> getAllSalary() {
        return salaryRepository.findAll();
    }

    public void updateReport() {
        List<SalaryModel> salaries = salaryRepository.findAll();
        for (SalaryModel salary : salaries) {
            RoleModel role = salary.getAccount().getRole();
            BigDecimal hourlyRate = role.getHourlySalary();
            BigDecimal totalSalary = salary.getTotal_hours().multiply(hourlyRate);
            totalSalary = totalSalary.setScale(2, RoundingMode.HALF_UP);
            salary.setTotal_salary(totalSalary);
        }
        salaryRepository.saveAll(salaries);
    }
}
