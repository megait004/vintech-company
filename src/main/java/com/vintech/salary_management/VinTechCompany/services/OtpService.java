package com.vintech.salary_management.VinTechCompany.services;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.text.StringSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.vintech.salary_management.VinTechCompany.types.OtpDetails;
import com.vintech.salary_management.VinTechCompany.types.OtpRequest;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class OtpService {

    private final Map<String, OtpDetails> otpStore = new ConcurrentHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(OtpService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ResourceLoader resourceLoader;

    @Value("${email.from}")
    private String emailFromAddress;
    @Value("${otp.ttl}")
    private int otpTtlSeconds;

    public String generateAndStoreOtp(String email) {
        Random random = new Random();
        String otp = String.format("%06d", random.nextInt(1000000));
        storeOtp(email, otp);
        return otp;
    }

    private void storeOtp(String email, String otp) {
        LocalDateTime expiresAt = LocalDateTime.now().plusSeconds(otpTtlSeconds);
        otpStore.put(email, new OtpDetails(expiresAt, otp));
    }

    public boolean validateOtp(String email, String otp) {
        OtpDetails otpDetails = otpStore.get(email);
        if (otpDetails == null || otpDetails.isExpired()) {
            otpStore.remove(email);
            return false;
        }
        if (otpDetails.getOtp().equals(otp)) {
            otpStore.remove(email);
            return true;
        }
        return false;
    }

    private String generateEmailContent(String username, String otp) {
        String templatePath = "classpath:templates/email.html";
        try {
            org.springframework.core.io.Resource resource = resourceLoader.getResource(templatePath);
            String content = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            Map<String, String> replacements = new HashMap<>();
            replacements.put("username", username);
            replacements.put("code", otp);
            StringSubstitutor substitutor = new StringSubstitutor(replacements);
            return substitutor.replace(content);
        } catch (IOException e) {
            logger.error("Lỗi: " + templatePath, e);
            return null;
        }
    }

    public void sendOtp(OtpRequest request) throws MessagingException, IOException {
        String otp = generateAndStoreOtp(request.getEmail());
        String htmlContent = generateEmailContent(request.getUsername(), otp);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(request.getEmail());
        helper.setSubject(request.getSubject());
        helper.setFrom(emailFromAddress);
        helper.setText(htmlContent, true);

        mailSender.send(message);
        logger.info("Email sent to {}", request.getEmail());
    }

    @Scheduled(fixedRateString = "${otp.cleanup.interval}")
    public void cleanupExpiredOtps() {
        otpStore.entrySet().removeIf(entry -> entry.getValue().isExpired());
    }
}
