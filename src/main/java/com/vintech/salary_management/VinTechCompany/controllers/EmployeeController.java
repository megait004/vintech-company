package com.vintech.salary_management.VinTechCompany.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vintech.salary_management.VinTechCompany.models.AccountModel;
import com.vintech.salary_management.VinTechCompany.repositories.AccountRepository;
import com.vintech.salary_management.VinTechCompany.types.APIResponse;
import com.vintech.salary_management.VinTechCompany.types.AccountInfomation;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping
    public ResponseEntity<APIResponse> getAllEmployees() {
        List<AccountModel> accounts = accountRepository.findAll();
        if (accounts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new APIResponse(false, "No content"));
        } else {
            List<AccountInfomation> employeeInfomations = accounts.stream()
                    .filter(account -> !account.getRole().getRole().equalsIgnoreCase("admin"))
                    .map(account -> new AccountInfomation(
                            account.getAvatarId(),
                            account.getEmail(),
                            account.getRole(),
                            account.getUsername()))
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new APIResponse(true, "Lấy danh sách nhân viên thành công", employeeInfomations));
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<APIResponse> getEmployeeById(@PathVariable String username) {
        AccountModel account = accountRepository.findByUsername(username);
        if (account != null) {
            AccountInfomation accountInfo = new AccountInfomation(account.getAvatarId(), account.getEmail(),
                    account.getRole(), username);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new APIResponse(true, "Lấy dữ liệu thành công", accountInfo));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new APIResponse(false, "Không thể lấy dữ liệu"));
        }
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<APIResponse> deleteEmployeeById(@PathVariable String username) {
        AccountModel account = accountRepository.findByUsername(username);
        Long id = account.getId();
        accountRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new APIResponse(true, "Xoá thành công!"));
    }
}
