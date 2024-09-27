package com.vintech.salary_management.VinTechCompany.controllers;

import java.util.List;

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

import com.vintech.salary_management.VinTechCompany.models.RoleModel;
import com.vintech.salary_management.VinTechCompany.repositories.RoleRepository;
import com.vintech.salary_management.VinTechCompany.services.SlugService;
import com.vintech.salary_management.VinTechCompany.types.APIResponse;
import com.vintech.salary_management.VinTechCompany.types.RoleRequest;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    SlugService slugService;

    @GetMapping
    public ResponseEntity<APIResponse> getAllRoles() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new APIResponse(true, "Lấy thông tin thành công!", roleRepository.findAll()));
    }

    @GetMapping("/{roleSlug}")
    public ResponseEntity<APIResponse> getRoleByName(@PathVariable("roleSlug") String roleName) {
        RoleModel role = roleRepository.findByroleSlug(roleName);
        if (role == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new APIResponse(false, "Không tìm thấy role!"));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new APIResponse(true, "Lấy thông tin thành công!", role));
    }

    @PostMapping
    public ResponseEntity<APIResponse> createRole(@RequestBody RoleRequest role) {
        String roleSlug = slugService.createSlug(role.getRole());
        RoleModel existingRole = roleRepository.findByroleSlug(roleSlug);

        if (existingRole == null) {
            try {
                RoleModel newRole = new RoleModel();
                newRole.setroleSlug(roleSlug);
                newRole.setRole(role.getRole());
                newRole.setHourlySalary(role.getHourlySalary());
                RoleModel savedRole = roleRepository.save(newRole);
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(new APIResponse(true, "Tạo role mới thành công!", savedRole));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new APIResponse(false, "Lỗi khi tạo role: " + e.getMessage()));
            }
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new APIResponse(false, "Vị trí này đã tồn tại!"));
        }
    }

    @PutMapping()
    public ResponseEntity<APIResponse> updateRole(@RequestBody RoleRequest role) {
        String roleSlug = slugService.createSlug(role.getRole());
        RoleModel newRole = roleRepository.findByroleSlug(roleSlug);
        newRole.setHourlySalary(role.getHourlySalary());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new APIResponse(true, "Cập nhật role thành công!", roleRepository.save(newRole)));
    }

    @DeleteMapping("/{roleSlug}")
    public ResponseEntity<APIResponse> deleteRole(@PathVariable("roleSlug") String roleSlug) {
        RoleModel role = roleRepository.findByroleSlug(roleSlug);
        if (role != null) {
            try {
                List<String> linkedAccounts = roleRepository.findAccountsByRoleId(role.getId());
                if (!linkedAccounts.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body(new APIResponse(false,
                                    "Vị trí đang liên kết với các tài khoản sau, không thể xoá",
                                    linkedAccounts));
                }
                roleRepository.delete(role);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new APIResponse(true, "Xóa role thành công!"));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new APIResponse(false, "Không thể xóa role: " + e.getMessage()));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new APIResponse(false, "Role không tồn tại!"));
        }
    }
}
