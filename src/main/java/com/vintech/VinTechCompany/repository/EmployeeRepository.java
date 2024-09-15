package com.vintech.VinTechCompany.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vintech.VinTechCompany.model.Employee;
import com.vintech.VinTechCompany.model.Position;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    void deleteAllByPosition(Position position);
    List<Employee> findAllByPosition(Position position);
    Optional<Employee> findByPosition(Position position);
}
