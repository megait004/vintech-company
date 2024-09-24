package com.vintech.salary_management.VinTechCompany.controllers;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vintech.salary_management.VinTechCompany.types.APIResponse;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class GeneralController {
    private static final Logger logger = LoggerFactory.getLogger(GeneralController.class);

    @GetMapping("/avatar/{avatarUrl}")
    public ResponseEntity<?> getAvatar(@PathVariable String avatarUrl) {
        try {
            String filePath = "avatar/avatar_" + avatarUrl + ".png";
            Resource resource = new ClassPathResource(filePath);
            if (resource.exists()) {
                byte[] fileContent = StreamUtils.copyToByteArray(resource.getInputStream());
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_PNG)
                        .body(fileContent);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(APIResponse.error("Ảnh không tồn tại"));
            }
        } catch (IOException e) {
            logger.error("Không thể lấy ảnh: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(APIResponse.error("Không thể lấy ảnh"));
        }
    }
}
