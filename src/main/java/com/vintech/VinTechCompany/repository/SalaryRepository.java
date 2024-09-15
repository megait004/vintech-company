package com.vintech.VinTechCompany.repository;

import java.time.YearMonth;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vintech.VinTechCompany.model.Employee;
import com.vintech.VinTechCompany.model.Salary;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {
	List<Salary> findByMonth(YearMonth month);
	boolean existsByEmployee(Employee employee);
	void deleteByEmployee(Employee employee);
}