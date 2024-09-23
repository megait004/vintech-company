package com.vintech.salary_management.VinTechCompany.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.vintech.salary_management.VinTechCompany.dto.AdminDTO;
import com.vintech.salary_management.VinTechCompany.models.AdminModel;
import com.vintech.salary_management.VinTechCompany.repositories.AdminRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private JavaMailSender mailSender;

    public boolean signUp(String username, String password) {
        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
        AdminModel admin = new AdminModel();
        admin.setUsername(username);
        admin.setPassword(passwordHash);
        adminRepository.save(admin);
        return true;
    }

    public boolean login(String username, String password) {
        AdminModel admin = adminRepository.findByUsername(username);
        if (admin == null) {
            return false;
        }
        boolean isMatch = BCrypt.checkpw(password, admin.getPassword());
        return isMatch;
    }

    public AdminDTO getInfo(String username) {
        AdminModel admin = adminRepository.findByUsername(username);
        if (admin == null) {
            return new AdminDTO("", "", "");
        }
        AdminDTO adminDTO = new AdminDTO(admin.getUsername(), admin.getEmail(), admin.getApp_key());
        return adminDTO;
    }

    public void sendCodeToEmail(String toEmail, String username, String subject, String code)
            throws MessagingException, IOException {
        ClassPathResource resource = new ClassPathResource("templates/email.html");
        String template = new String(Files.readAllBytes(Paths.get(resource.getURI())));
        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put("code", code);
        valuesMap.put("username", username);
        StringSubstitutor sub = new StringSubstitutor(valuesMap);
        String htmlContent = sub.replace(template);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setFrom("Vintech <nguyengiap2004bg@gmail.com>");
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }

    public boolean updateInfo(String email, String app_key, String password) {
        AdminModel admin = adminRepository.findByEmail(email);
        if (admin == null) {
            return false;
        }
        admin.setApp_key(app_key);
        admin.setEmail(email);
        admin.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        adminRepository.save(admin);
        return true;
    }
}
