package com.vintech.salary_management.VinTechCompany.services;

import java.text.Normalizer;

import org.springframework.stereotype.Service;
@Service
public class SlugService {
    public String createSlug(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .trim()
                .replaceAll("\\s+", "-");
        return normalized.replaceAll("-+", "-");
    }
}
