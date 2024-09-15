package com.vintech.VinTechCompany.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vintech.VinTechCompany.dto.ApiResponse;
import com.vintech.VinTechCompany.model.Position;
import com.vintech.VinTechCompany.services.PositionService;

@RestController
@RequestMapping("/api/positions")
public class PositionController {

    @Autowired
    private PositionService positionService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Position>>> getAllPositions() {
        List<Position> positions = positionService.getAllPositions();
        return ResponseEntity.ok(new ApiResponse<>(true, "POSITIONS RETRIEVED SUCCESSFULLY", positions));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Position>> getPositionById(@PathVariable Long id) {
        return positionService.getPositionById(id)
                .map(position -> ResponseEntity.ok(new ApiResponse<>(true, "POSITION RETRIEVED SUCCESSFULLY", position)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Position>> createPosition(@RequestBody Position position) {
        Position createdPosition = positionService.savePosition(position);
        return ResponseEntity.ok(new ApiResponse<>(true, "POSITION CREATED SUCCESSFULLY", createdPosition));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Position>> updatePosition(@PathVariable Long id, @RequestBody Position position) {
        return positionService.getPositionById(id)
                .map(existingPosition -> {
                    position.setPositionId(id);
                    Position updatedPosition = positionService.savePosition(position);
                    return ResponseEntity.ok(new ApiResponse<>(true, "POSITION UPDATED SUCCESSFULLY", updatedPosition));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePosition(@PathVariable Long id) {
        boolean deleted = positionService.deletePosition(id);
        if (deleted) {
            return ResponseEntity.ok(new ApiResponse<>(true, "POSITION DELETED SUCCESSFULLY", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "POSITION NOT FOUND", null));
        }
    }

}
