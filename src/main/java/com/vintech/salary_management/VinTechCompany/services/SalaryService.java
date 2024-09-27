package com.vintech.salary_management.VinTechCompany.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vintech.salary_management.VinTechCompany.models.AccountModel;
import com.vintech.salary_management.VinTechCompany.models.AttendanceModel;
import com.vintech.salary_management.VinTechCompany.models.SalaryModel;
import com.vintech.salary_management.VinTechCompany.repositories.AccountRepository;
import com.vintech.salary_management.VinTechCompany.repositories.AttendanceRepository;
import com.vintech.salary_management.VinTechCompany.repositories.SalaryRepository;
import com.vintech.salary_management.VinTechCompany.types.SalaryResponse;

@Service
public class SalaryService {

    @Autowired
    private SalaryRepository salaryRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private AccountRepository accountRepository;

    public List<SalaryResponse> getAllSalary() {
        List<SalaryModel> salaries = salaryRepository.findAll();
        List<SalaryResponse> salaryResponses = new ArrayList<>();
        for (SalaryModel salary : salaries) {
            SalaryResponse salaryResponse = new SalaryResponse();
            salaryResponse.setUsername(salary.getAccount().getUsername());
            salaryResponse.setRole(salary.getAccount().getRole().getRole());
            salaryResponse.setTotalHours(salary.getTotalHours().toString());
            salaryResponse.setTotalSalary(salary.getTotalSalary().toString());
            salaryResponses.add(salaryResponse);
        }
        return salaryResponses;
    }

    public void updateReport() {
        List<AccountModel> accounts = accountRepository.findAll();
        salaryRepository.deleteAll();
        for (AccountModel account : accounts) {
            SalaryModel salary = new SalaryModel();
            salary.setAccount(account);
            salaryRepository.save(salary);
        }
        List<SalaryModel> salaries = salaryRepository.findAll();
        for (SalaryModel salary : salaries) {
            List<AttendanceModel> attendances = attendanceRepository.findAllByaccountUsername(salary.getAccount());
            Long totalHours = 0L;
            for (AttendanceModel attendance : attendances) {
                totalHours += attendance.getTotalHours();
            }
            salary.setTotalHours(totalHours);
            BigDecimal hourlyRate = salary.getAccount().getRole().getHourlySalary();
            Long totalSalary = totalHours * hourlyRate.longValue();
            salary.setTotalSalary(totalSalary);
            salaryRepository.save(salary);
        }
        salaryRepository.saveAll(salaries);
    }

    public SalaryResponse getSalaryByUsername(AccountModel account) {
        if (account == null) {
            return null;
        }
        SalaryModel salary = salaryRepository.findByAccount(account);
        if (salary == null) {
            return null;
        }
        SalaryResponse salaryResponse = new SalaryResponse();
        salaryResponse.setUsername(salary.getAccount().getUsername());
        salaryResponse.setRole(salary.getAccount().getRole().getRole());
        salaryResponse.setTotalHours(salary.getTotalHours().toString());
        salaryResponse.setTotalSalary(salary.getTotalSalary().toString());
        return salaryResponse;
    }
}
