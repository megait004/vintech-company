package com.vintech.salary_management.VinTechCompany.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vintech.salary_management.VinTechCompany.models.AccountModel;
import com.vintech.salary_management.VinTechCompany.models.AttendanceModel;
import com.vintech.salary_management.VinTechCompany.repositories.AccountRepository;
import com.vintech.salary_management.VinTechCompany.repositories.AttendanceRepository;
import com.vintech.salary_management.VinTechCompany.services.AttendanceService;
import com.vintech.salary_management.VinTechCompany.types.APIResponse;
import com.vintech.salary_management.VinTechCompany.types.AttendanceResponse;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping
    public ResponseEntity<APIResponse> getAllAttendance() {
        List<AttendanceModel> attendance = attendanceRepository.findAll();
        List<AttendanceResponse> attendanceResponse = new ArrayList<>();
        for (AttendanceModel attendanceModel : attendance) {
            attendanceResponse.add(new AttendanceResponse(attendanceModel.getCheckIn(), attendanceModel.getCheckOut(),
                    attendanceModel.getDate(), attendanceModel.getTotalHours(), attendanceModel.getAccountUsername()));
        }
        return ResponseEntity.ok(new APIResponse(true, "Lấy thông tin thành công", attendanceResponse));
    }

    @PostMapping("/check-in")
    public ResponseEntity<APIResponse> checkIn(HttpServletRequest request) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM");
        String today = currentDate.format(dateFormatter);
        String username = extractUsernameFromCookies(request);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new APIResponse(false, "Bạn chưa đăng nhập"));
        }
        AttendanceModel attendanceModel = attendanceRepository.findByDate(today);
        if (attendanceModel == null) {
            LocalTime currentTime = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH");
            String hour = currentTime.format(formatter);
            AccountModel accountModel = accountRepository.findByUsername(username);
            AttendanceModel newAttendance = new AttendanceModel();
            newAttendance.setCheckIn(hour);
            newAttendance.setAccountUsername(accountModel);
            newAttendance.setDate(today);
            attendanceService.checkIn(newAttendance);
            return ResponseEntity.ok(new APIResponse(true, "Check-in thành công!"));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new APIResponse(false, "Đã chấm công ngày hôm nay!"));
    }

    @PostMapping("/check-out")
    public ResponseEntity<APIResponse> checkOut(HttpServletRequest request) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM");
        String today = currentDate.format(dateFormatter);
        String username = extractUsernameFromCookies(request);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new APIResponse(false, "Bạn chưa đăng nhập"));
        }
        AttendanceModel attendanceModel = attendanceRepository.findByDate(today);
        if (attendanceModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new APIResponse(false, "Không tìm thấy bản ghi chấm công cho ngày hôm nay"));
        }
        if (attendanceModel.getCheckOut() != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new APIResponse(false, "Đã chấm công về ngày hôm nay!"));
        } else {
            LocalTime currentTime = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH");
            String checkOutTime = currentTime.format(formatter);
            attendanceModel.setCheckOut(checkOutTime);
            attendanceService.checkOut(attendanceModel);
            return ResponseEntity.ok(new APIResponse(true, "Check-out thành công!"));
        }
    }

    @GetMapping("/{accountUsername}")
    public ResponseEntity<APIResponse> getAttendanceById(@PathVariable("accountUsername") String accountUsername) {
        AccountModel accountModel = accountRepository.findByUsername(accountUsername);
        List<AttendanceModel> attendance = attendanceRepository.findAllByaccountUsername(accountModel);
        List<AttendanceResponse> attendanceResponse = new ArrayList<>();
        for (AttendanceModel attendanceModel : attendance) {
            attendanceResponse.add(new AttendanceResponse(attendanceModel.getCheckIn(), attendanceModel.getCheckOut(),
                    attendanceModel.getDate(), attendanceModel.getTotalHours(), attendanceModel.getAccountUsername()));
        }
        if (attendance != null) {
            return ResponseEntity.ok(new APIResponse(true, "Lấy thông tin thành công", attendanceResponse));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new APIResponse(false, "Không tìm thấy bảng chấm công"));
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
