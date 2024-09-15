package com.vintech.VinTechCompany.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vintech.VinTechCompany.model.Position;
import com.vintech.VinTechCompany.repository.PositionRepository;

@Service
public class PositionService {

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private SalaryService salaryService;

    public List<Position> getAllPositions() {
        return positionRepository.findAll();
    }

    public Optional<Position> getPositionById(Long positionId) {
        return positionRepository.findById(positionId);
    }

    public Position savePosition(Position position) {
        return positionRepository.save(position);
    }

    @Transactional
    public boolean deletePosition(Long id) {
        Optional<Position> positionOptional = positionRepository.findById(id);
        if (positionOptional.isPresent()) {
            Position position = positionOptional.get();

            if (attendanceService.existsAttendanceForPosition(id)) {
                attendanceService.deleteAttendanceByPositionId(id);
                if (salaryService.existsSalaryForPosition(id)) {
                    salaryService.deleteSalary(id);
                }
            } else if (salaryService.existsSalaryForPosition(id)) {
                salaryService.deleteSalary(id);
            }

            positionRepository.delete(position);
            return true;
        }
        return false;
    }
}
