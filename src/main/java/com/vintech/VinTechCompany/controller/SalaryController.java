package com.vintech.VinTechCompany.controller;

import java.time.YearMonth;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vintech.VinTechCompany.model.Salary;
import com.vintech.VinTechCompany.services.SalaryService;

@RestController
@RequestMapping("/api/salaries")
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @PostMapping("/calculate-all")
    public ResponseEntity<List<Salary>> calculateAllSalaries() {
        List<Salary> salaries = salaryService.calculateAllSalaries();
        return ResponseEntity.ok(salaries);
    }

    @PostMapping("/calculate/{employeeId}")
    public ResponseEntity<Salary> calculateSalary(
            @PathVariable Long employeeId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM") YearMonth month) {
        Salary salary = salaryService.calculateSalary(employeeId, month);
        return ResponseEntity.ok(salary);
    }

    @GetMapping("/current-month")
    public ResponseEntity<List<Salary>> getAllSalariesForCurrentMonth() {
        List<Salary> salaries = salaryService.getAllSalariesForCurrentMonth();
        return ResponseEntity.ok(salaries);
    }

    @GetMapping("/{month}")
    public ResponseEntity<List<Salary>> getAllSalariesForMonth(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM") YearMonth month) {
        List<Salary> salaries = salaryService.getAllSalariesForMonth(month);
        return ResponseEntity.ok(salaries);
    }
}