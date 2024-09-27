package com.vintech.salary_management.VinTechCompany.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vintech.salary_management.VinTechCompany.models.AccountModel;
import com.vintech.salary_management.VinTechCompany.repositories.AccountRepository;
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
    private AccountRepository accountRepository;

    @GetMapping
    public ResponseEntity<APIResponse> getAllSalary(HttpServletRequest request) {
        String username = extractUsernameFromCookies(request);
        AccountModel accountModel = accountRepository.findByUsername(username);
        String role = accountModel.getRole().getRole();
        if (role.equalsIgnoreCase("admin")) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new APIResponse(true, "Lấy thông tin thành công!",
                            salaryService.getAllSalary()));
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new APIResponse(true, "Lấy thông tin thành công!",
                            salaryService.getSalaryByUsername(accountModel)));
        }
    }

    @PostMapping()
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

    @GetMapping("/{username}")
    public ResponseEntity<APIResponse> getSalaryByUsername(@PathVariable String username) {
        AccountModel accountModel = accountRepository.findByUsername(username);
        if (accountModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new APIResponse(false, "Không tìm thấy tài khoản!"));
        }
        {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new APIResponse(true, "Lấy thông tin thành công!",
                            salaryService.getSalaryByUsername(accountModel)));
        }
    }

    private String extractUsernameFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("u_id")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
