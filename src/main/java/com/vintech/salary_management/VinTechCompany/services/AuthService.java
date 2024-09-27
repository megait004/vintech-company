package com.vintech.salary_management.VinTechCompany.services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.vintech.salary_management.VinTechCompany.models.AccountModel;
import com.vintech.salary_management.VinTechCompany.models.RoleModel;
import com.vintech.salary_management.VinTechCompany.repositories.AccountRepository;
import com.vintech.salary_management.VinTechCompany.repositories.RoleRepository;
import com.vintech.salary_management.VinTechCompany.types.AccountInfomation;

@Service
public class AuthService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SlugService slugService;

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    public String generateToken(String username) {
        return BCrypt.hashpw(username, BCrypt.gensalt());
    }

    public boolean verifyToken(String username, String hashedToken) {
        return BCrypt.checkpw(username, hashedToken);
    }

    public boolean userExists(String username) {
        return accountRepository.findByUsername(username) != null;
    }

    public boolean emailExists(String email) {
        return accountRepository.findByEmail(email) != null;
    }

    public Boolean checkLogin(String username, String password) {
        AccountModel account = accountRepository.findByUsername(username);
        if (account == null) {
            return false;
        }
        return checkPassword(password, account.getPassword());
    }

    public AccountInfomation createAccount(String username, String password, String email, String avatarId) {
        AccountModel account = new AccountModel();
        RoleModel role = roleRepository.findByRole("Xét duyệt");
        if (role == null) {
            role = new RoleModel();
            role.setRole("Xét duyệt");
            String roleSlug = slugService.createSlug("Xét duyệt");
            role.setroleSlug(roleSlug);
            role.setHourlySalary(new BigDecimal("0"));
            role = roleRepository.save(role);
        }
        account.setUsername(username);
        account.setPassword(hashPassword(password));
        account.setEmail(email);
        account.setAvatarId(avatarId);
        account.setRole(role);
        accountRepository.save(account);
        AccountInfomation accountInfomation = new AccountInfomation();
        accountInfomation.setUsername(username);
        accountInfomation.setEmail(email);
        accountInfomation.setAvatarId(avatarId);
        accountInfomation.setRole(role);
        return accountInfomation;
    }
}
