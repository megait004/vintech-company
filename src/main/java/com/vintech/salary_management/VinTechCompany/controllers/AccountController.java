package com.vintech.salary_management.VinTechCompany.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vintech.salary_management.VinTechCompany.models.AccountModel;
import com.vintech.salary_management.VinTechCompany.models.RoleModel;
import com.vintech.salary_management.VinTechCompany.repositories.AccountRepository;
import com.vintech.salary_management.VinTechCompany.services.SlugService;
import com.vintech.salary_management.VinTechCompany.types.APIResponse;
import com.vintech.salary_management.VinTechCompany.types.AccountInfoAuth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private SlugService slugService;

    @PostMapping("/change-information")
    public ResponseEntity<APIResponse> changeInformation(@RequestBody AccountInfoAuth accountRequest,
            HttpServletRequest request) {

        String logedinUsername = extractUsernameFromCookies(request);
        if (logedinUsername == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new APIResponse(false, "Bạn chưa đăng nhập"));
        }
        AccountModel logedinAccount = accountRepository.findByUsername(logedinUsername);
        if (logedinAccount == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new APIResponse(false, "Tài khoản không tồn tại"));
        }

        boolean isAdmin = logedinAccount.getRole().getRole().equalsIgnoreCase("admin")
                && accountRequest.getUsername().equalsIgnoreCase(logedinUsername);

        if (isAdmin) {
            AccountModel newAccount = accountRepository.findByUsername(accountRequest.getUsername());
            newAccount.setAvatarId(accountRequest.getAvatarId());
            newAccount.setPassword(BCrypt.hashpw(accountRequest.getPassword(), BCrypt.gensalt()));
            newAccount.setEmail(accountRequest.getEmail());
            String roleSlug = slugService.createSlug(accountRequest.getRole().getRole());
            RoleModel role = accountRequest.getRole();
            role.setroleSlug(roleSlug);
            accountRequest.setRole(role);
            newAccount.setRole(accountRequest.getRole());
            accountRepository.save(newAccount);
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new APIResponse(true, "Thay đổi thông tin tài khoản thành công!"));
        } else {
            RoleModel role = logedinAccount.getRole();
            RoleModel roleRequest = accountRequest.getRole();
            boolean isCheating = roleRequest == null || !roleRequest.equals(role);
            if (isCheating) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new APIResponse(false, "Bạn không được phép thay đổi thông tin này!"));
            } else {
                AccountModel newAccount = accountRepository.findByUsername(logedinUsername);
                newAccount.setAvatarId(accountRequest.getAvatarId());
                newAccount.setPassword(BCrypt.hashpw(accountRequest.getPassword(), BCrypt.gensalt()));
                newAccount.setEmail(accountRequest.getEmail());
                accountRepository.save(newAccount);
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new APIResponse(true, "Thay đổi thông tin tài khoản thành công!"));
            }
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
