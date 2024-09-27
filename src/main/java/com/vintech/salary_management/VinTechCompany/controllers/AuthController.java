package com.vintech.salary_management.VinTechCompany.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vintech.salary_management.VinTechCompany.models.AccountModel;
import com.vintech.salary_management.VinTechCompany.models.RoleModel;
import com.vintech.salary_management.VinTechCompany.repositories.AccountRepository;
import com.vintech.salary_management.VinTechCompany.services.AuthService;
import com.vintech.salary_management.VinTechCompany.services.OtpService;
import com.vintech.salary_management.VinTechCompany.types.APIResponse;
import com.vintech.salary_management.VinTechCompany.types.AccountInfomation;
import com.vintech.salary_management.VinTechCompany.types.LoginRequest;
import com.vintech.salary_management.VinTechCompany.types.OtpRequest;
import com.vintech.salary_management.VinTechCompany.types.RegisterRequest;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private OtpService otpService;

    @PostMapping("/login")
    public ResponseEntity<APIResponse> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        if (username == null || password == null) {
            return ResponseEntity.badRequest()
                    .body(APIResponse.error("Tên đăng nhập hoặc mật khẩu không được để trống"));
        }
        if (authService.checkLogin(username, password)) {
            String token = authService.generateToken(username);
            Cookie tokenCookie = new Cookie("u_t_id", token);
            Cookie u_idCookie = new Cookie("u_id", username);
            tokenCookie.setMaxAge(3600);
            u_idCookie.setMaxAge(3600);
            tokenCookie.setPath("/");
            u_idCookie.setPath("/");
            response.addCookie(tokenCookie);
            response.addCookie(u_idCookie);
            AccountModel account = accountRepository.findByUsername(username);
            RoleModel role = account.getRole();
            AccountInfomation accountInfomation = new AccountInfomation(account.getAvatarId(), account.getEmail(),
                    role, account.getUsername());
            return ResponseEntity.ok(APIResponse.success("Đăng nhập thành công", accountInfomation));
        }
        return ResponseEntity.badRequest()
                .body(APIResponse.error("Tên đăng nhập hoặc mật khẩu không đúng"));
    }

    @PostMapping("/register")
    public ResponseEntity<APIResponse> register(@RequestBody RegisterRequest registerRequest,
            @RequestParam(required = false) String otp) {
        String username = registerRequest.getUsername();
        String password = registerRequest.getPassword();
        String email = registerRequest.getEmail();
        String avatarId = registerRequest.getAvatarId();
        if (authService.userExists(username)) {
            return ResponseEntity.badRequest().body(APIResponse.error("Tên đăng nhập đã tồn tại"));
        }
        if (authService.emailExists(email)) {
            return ResponseEntity.badRequest().body(APIResponse.error("Email đã tồn tại"));
        }
        try {
            if (otp != null) {
                if (otpService.validateOtp(email, otp)) {
                    AccountInfomation account = authService.createAccount(username, password, email, avatarId);
                    return ResponseEntity.ok(APIResponse.success("Đăng ký thành công", account));
                }
                return ResponseEntity.badRequest().body(APIResponse.error("OTP không đúng"));
            } else {
                OtpRequest otpRequest = new OtpRequest(email, username);
                otpService.sendOtp(otpRequest);
                return ResponseEntity.ok(APIResponse.success("OTP đã được gửi đến email", otpRequest));
            }
        } catch (MessagingException | IOException e) {
            return ResponseEntity.badRequest().body(APIResponse.error("Đăng ký thất bại"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<APIResponse> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("u_t_id", null);
        Cookie usernameCookie = new Cookie("u_id", null);
        cookie.setMaxAge(0);
        usernameCookie.setMaxAge(0);
        cookie.setPath("/");
        usernameCookie.setPath("/");
        response.addCookie(cookie);
        response.addCookie(usernameCookie);
        return ResponseEntity.ok(APIResponse.success("Đăng xuất thành công", null));
    }
}
