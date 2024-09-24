package com.vintech.salary_management.VinTechCompany.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vintech.salary_management.VinTechCompany.annotations.IsAdmin;
import com.vintech.salary_management.VinTechCompany.models.AccountModel;
import com.vintech.salary_management.VinTechCompany.repositories.AccountRepository;
import com.vintech.salary_management.VinTechCompany.types.APIResponse;
import com.vintech.salary_management.VinTechCompany.types.AccountInfomation;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private AccountRepository accountRepository;

    @IsAdmin
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
                    .body(new APIResponse(true, "Get data successfully", employeeInfomations));
        }
    }

    @IsAdmin
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> getEmployeeById(@PathVariable Long id) {
        Optional<AccountModel> accountOptional = accountRepository.findById(id);
        if (accountOptional.isPresent()) {
            AccountModel account = accountOptional.get();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new APIResponse(true, "Get data successfully", account));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new APIResponse(false, "Employee not found"));
        }
    }

    @IsAdmin
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> deleteEmployeeById(@PathVariable Long id) {
        accountRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new APIResponse(true, "Delete data successfully"));
    }
}
