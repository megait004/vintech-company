package com.vintech.VinTechCompany.services;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vintech.VinTechCompany.model.Attendance;
import com.vintech.VinTechCompany.model.Employee;
import com.vintech.VinTechCompany.model.Salary;
import com.vintech.VinTechCompany.repository.AttendanceRepository;
import com.vintech.VinTechCompany.repository.SalaryRepository;

@Service
public class SalaryService {

    @Autowired
    private SalaryRepository salaryRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeService employeeService;

    public Salary calculateSalary(Long employeeId, YearMonth month) {
        Optional<Employee> employeeOpt = employeeService.getEmployeeById(employeeId);
        if (employeeOpt.isEmpty()) {
            return null; // Or return a default Salary object
        }
        Employee employee = employeeOpt.get();

        List<Attendance> attendances = attendanceRepository.findByEmployeeAndAttendanceDateBetween(
                employee,
                month.atDay(1),
                month.atEndOfMonth()
        );

        BigDecimal totalHours = attendances.stream()
                .map(Attendance::getWorkHours)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal baseSalary = employee.getPosition().getBaseSalary();
        BigDecimal overTimeSalary = calculateOverTimeSalary(totalHours, baseSalary);
        BigDecimal absence = calculateAbsence(attendances.size(), month.lengthOfMonth());
        BigDecimal netSalary = baseSalary.add(overTimeSalary).subtract(absence);

        Salary salary = new Salary();
        salary.setEmployee(employee);
        salary.setMonth(month);
        salary.setBaseSalary(baseSalary);
        salary.setOverTimeSalary(overTimeSalary);
        salary.setTotalHours(totalHours);
        salary.setAbsence(absence);
        salary.setNetSalary(netSalary);

        return salaryRepository.save(salary);
    }

    private BigDecimal calculateOverTimeSalary(BigDecimal totalHours, BigDecimal baseSalary) {
        BigDecimal standardHours = BigDecimal.valueOf(160);
        if (totalHours.compareTo(standardHours) > 0) {
            BigDecimal overTimeHours = totalHours.subtract(standardHours);
            BigDecimal hourlyRate = baseSalary.divide(standardHours, new MathContext(2, RoundingMode.HALF_UP));
            return overTimeHours.multiply(hourlyRate).multiply(BigDecimal.valueOf(1.5));
        }
        return BigDecimal.ZERO;
    }

    private BigDecimal calculateAbsence(int daysWorked, int totalDays) {
        int absenceDays = totalDays - daysWorked;
        if (absenceDays > 0) {
            return BigDecimal.valueOf(absenceDays).multiply(BigDecimal.valueOf(100));
        }
        return BigDecimal.ZERO;
    }

    public List<Salary> getAllSalariesForCurrentMonth() {
        YearMonth currentMonth = YearMonth.from(LocalDate.now());
        return salaryRepository.findByMonth(currentMonth);
    }

    public List<Salary> getAllSalariesForMonth(YearMonth month) {
        return salaryRepository.findByMonth(month);
    }

    public List<Salary> calculateAllSalaries() {
        List<Employee> employees = employeeService.getAllEmployees();
        for (Employee employee : employees) {
            calculateSalary(employee.getEmployeeId(), YearMonth.from(LocalDate.now()));
        }
        return getAllSalariesForCurrentMonth();
    }

    @Transactional
    public boolean deleteSalary(Long positionId) {
        Optional<Employee> employeeOpt = employeeService.getEmployeeByPositionId(positionId);
        if (employeeOpt.isEmpty()) {
            return false;
        }
        salaryRepository.deleteByEmployee(employeeOpt.get());
        return true;
    }

    public boolean existsSalaryForPosition(Long positionId) {
        Optional<Employee> employeeOpt = employeeService.getEmployeeByPositionId(positionId);
        return employeeOpt.map(employee -> salaryRepository.existsByEmployee(employee))
                          .orElse(false);
    }
}
