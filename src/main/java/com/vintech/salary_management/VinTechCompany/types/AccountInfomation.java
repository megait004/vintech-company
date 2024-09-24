package com.vintech.salary_management.VinTechCompany.types;

import com.vintech.salary_management.VinTechCompany.models.RoleModel;

public class AccountInfomation {
    private String avatarId;
    private String email;
    private RoleModel role;
    private String username;

    public String getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(String avatarId) {
        this.avatarId = avatarId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleModel getRole() {
        return role;
    }

    public void setRole(RoleModel role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public AccountInfomation() {

    }

    public AccountInfomation(String avatarId, String email, RoleModel role, String username) {
        this.avatarId = avatarId;
        this.email = email;
        this.role = role;
        this.username = username;
    }

}
