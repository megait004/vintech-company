package com.vintech.VinTechCompany.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vintech.VinTechCompany.model.Employee;
import com.vintech.VinTechCompany.model.Position;
import com.vintech.VinTechCompany.repository.EmployeeRepository;
import com.vintech.VinTechCompany.repository.PositionRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PositionRepository positionRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<Employee> getAllEmployeesByPosition(Long positionId) {
        Optional<Position> positionOpt = positionRepository.findById(positionId);
        if (positionOpt.isPresent()) {
            return employeeRepository.findAllByPosition(positionOpt.get());
        }
        return List.of();
    }

    public Optional<Employee> getEmployeeByPositionId(Long positionId) {
        Optional<Position> positionOpt = positionRepository.findById(positionId);
        if (positionOpt.isPresent()) {
            return employeeRepository.findByPosition(positionOpt.get());
        }
        return Optional.empty();
    }

    public Optional<Employee> getEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId);
    }

    public Employee saveEmployee(Employee employee) {
        try {
            if (employee.getPosition() != null && employee.getPosition().getPositionId() != null) {
                Optional<Position> positionOpt = positionRepository.findById(employee.getPosition().getPositionId());
                positionOpt.ifPresent(employee::setPosition);
            }
            return employeeRepository.save(employee);
        } catch (Exception e) {
            System.out.println("Failed to save employee");
            return null;
        }
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    @Transactional
    public boolean deleteAllEmployeeByPositionId(Long positionId) {
        Optional<Position> positionOpt = positionRepository.findById(positionId);
        if (positionOpt.isPresent()) {
            employeeRepository.deleteAllByPosition(positionOpt.get());
            return true;
        }
        return false;
    }

}
