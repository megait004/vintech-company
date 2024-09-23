package com.vintech.salary_management.VinTechCompany.controllers;

import java.io.IOException;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vintech.salary_management.VinTechCompany.models.AdminModel;
import com.vintech.salary_management.VinTechCompany.services.AdminService;
import com.vintech.salary_management.VinTechCompany.types.APIResponse;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/api/admin", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;

    @PostMapping("/sign-up")
    public ResponseEntity<APIResponse> signUp(@RequestBody AdminModel admin) {
        if (admin.getEmail() == null || admin.getEmail().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(APIResponse.error("Email không được để trống"));
        }
        try {
            adminService.signUp(admin.getUsername(), admin.getPassword());
            String code = String.valueOf(new Random().nextInt(1000000));
            adminService.sendCodeToEmail(admin.getEmail(), admin.getUsername(), "Mã xác nhận", code);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(APIResponse.success("Tạo tài khoản thành công"));
        } catch (IllegalArgumentException e) {
            logger.warn("Tạo tài khoản thất bại: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(APIResponse.error(e.getMessage()));
        } catch (MessagingException e) {
            logger.error("Tạo tài khoản thất bại: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(APIResponse.error("Tạo tài khoản thất bại"));
        } catch (IOException e) {
            logger.error("Tạo tài khoản thất bại: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(APIResponse.error("Tạo tài khoản thất bại"));
        } catch (Exception e) {
            logger.error("Tạo tài khoản thất bại: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(APIResponse.error("Tạo tài khoản thất bại"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<APIResponse> login(@RequestBody AdminModel admin,
            HttpServletResponse response) {
        try {
            boolean isMatch = adminService.login(admin.getUsername(), admin.getPassword());
            if (isMatch) {
                Cookie u_id = new Cookie("u_id", BCrypt.hashpw(admin.getUsername(), BCrypt.gensalt()));
                u_id.setMaxAge(3600);
                u_id.setHttpOnly(true);
                u_id.setPath("/");
                response.addCookie(u_id);
                return ResponseEntity
                        .ok(APIResponse.success("Đăng nhập thành công", adminService.getInfo(admin.getUsername())));
            } else {
                logger.warn("Đăng nhập thất bại: {} {}", admin.getUsername(), admin.getPassword());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(APIResponse.error("Tên đăng nhập hoặc mật khẩu không đúng"));
            }
        } catch (Exception e) {
            logger.error("Đăng nhập thất bại: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(APIResponse.error("Đăng nhập thất bại"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<APIResponse> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("u_id", null);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.ok(APIResponse.success("Đã đăng xuất"));
    }

    @PostMapping("/update-info")
    public ResponseEntity<APIResponse> updateInfo(@RequestBody AdminModel admin) {
        boolean isUpdated = adminService.updateInfo(admin.getEmail(), admin.getApp_key(), admin.getPassword());
        if (isUpdated) {
            return ResponseEntity.ok(APIResponse.success("Cập nhật thông tin thành công"));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(APIResponse.error("Cập nhật thông tin thất bại"));
        }
    }
}
