package com.vintech.salary_management.VinTechCompany.controllers;

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

import com.vintech.salary_management.VinTechCompany.annotations.IsAdmin;
import com.vintech.salary_management.VinTechCompany.models.RoleModel;
import com.vintech.salary_management.VinTechCompany.repositories.RoleRepository;
import com.vintech.salary_management.VinTechCompany.types.APIResponse;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @IsAdmin
    @GetMapping
    public ResponseEntity<APIResponse> getAllRoles() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new APIResponse(true, "Lấy thông tin thành công!", roleRepository.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse> getRoleById(@PathVariable Long id) {
        RoleModel role = roleRepository.findById(id).orElse(null);
        if (role == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new APIResponse(false, "Không tìm thấy role!"));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new APIResponse(true, "Lấy thông tin thành công!", role));
    }

    @IsAdmin
    @PostMapping
    public ResponseEntity<APIResponse> createRole(@RequestBody RoleModel role) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new APIResponse(true, "Tạo role thành công!", roleRepository.save(role)));
    }

    @IsAdmin
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse> updateRole(@PathVariable Long id, @RequestBody RoleModel role) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new APIResponse(true, "Cập nhật role thành công!", roleRepository.save(role)));
    }

    @IsAdmin
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse> deleteRole(@PathVariable Long id) {
        roleRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new APIResponse(true, "Xóa role thành công!"));
    }
}
