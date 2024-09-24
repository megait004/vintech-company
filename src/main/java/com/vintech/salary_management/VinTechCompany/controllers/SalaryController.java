package com.vintech.salary_management.VinTechCompany.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.vintech.salary_management.VinTechCompany.annotations.IsAdmin;
import com.vintech.salary_management.VinTechCompany.repositories.SalaryRepository;
import com.vintech.salary_management.VinTechCompany.services.SalaryService;
import com.vintech.salary_management.VinTechCompany.types.APIResponse;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/salary")
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @Autowired
    private SalaryRepository salaryRepository;

    @IsAdmin
    @GetMapping
    public ResponseEntity<APIResponse> getAllSalary(HttpServletRequest request) {
        if (extractUsernameFromCookies(request).equals("admin")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new APIResponse(true, "Lấy thông tin thành công!",
                            salaryService.getAllSalary()));
        } else {
            String username = extractUsernameFromCookies(request);
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new APIResponse(true, "Lấy thông tin thành công!",
                            salaryRepository.findByAccountUsername(username)));
        }
    }

    @IsAdmin
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> getSalaryById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new APIResponse(true, "Lấy thông tin thành công!",
                        salaryRepository.findById(id)
                                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                        "Salary not found"))));
    }

    @IsAdmin
    @PostMapping("/update-report")
    public ResponseEntity<APIResponse> updateReport() {
        try {
            salaryService.updateReport();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new APIResponse(true, "Cập nhật thành công!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse(false, "Cập nhật thất bại!"));
        }
    }

    private String extractUsernameFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
