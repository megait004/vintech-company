package com.vintech.salary_management.VinTechCompany.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vintech.salary_management.VinTechCompany.models.AccountModel;
import com.vintech.salary_management.VinTechCompany.models.RoleModel;
import com.vintech.salary_management.VinTechCompany.repositories.AccountRepository;
import com.vintech.salary_management.VinTechCompany.types.APIResponse;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @PostMapping("/change-information")
    public ResponseEntity<APIResponse> changeInformation(@RequestBody AccountModel accountRequest,
            HttpServletRequest request) {

        String username = extractUsernameFromCookies(request);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new APIResponse(false, "Tài khoản không tồn tại"));
        }
        AccountModel account = accountRepository.findByUsername(username);
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new APIResponse(false, "Tài khoản không tồn tại"));
        }

        boolean isAdmin = account.getRole().getRole().equalsIgnoreCase("admin");

        if (isAdmin) {
            accountRepository.save(accountRequest);
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new APIResponse(true, "Thay đổi thông tin tài khoản thành công!"));
        } else {
            RoleModel role = account.getRole();
            boolean isCheating = !accountRequest.getRole().getRole().equalsIgnoreCase(role.getRole());
            if (isCheating) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new APIResponse(false, "Bạn không được phép thay đổi thông tin này!"));
            } else {
                accountRepository.save(accountRequest);
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new APIResponse(true, "Thay đổi thông tin tài khoản thành công!"));
            }
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
