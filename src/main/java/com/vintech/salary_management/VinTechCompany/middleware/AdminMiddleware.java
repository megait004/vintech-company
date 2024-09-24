package com.vintech.salary_management.VinTechCompany.middleware;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.vintech.salary_management.VinTechCompany.annotations.IsAdmin;
import com.vintech.salary_management.VinTechCompany.models.AccountModel;
import com.vintech.salary_management.VinTechCompany.repositories.AccountRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AdminMiddleware implements HandlerInterceptor {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull Object handler) throws Exception {
        if (handler instanceof HandlerMethod handlerMethod) {
            IsAdmin isAdminAnnotation = handlerMethod.getMethodAnnotation(IsAdmin.class);

            if (isAdminAnnotation != null) {
                String username = extractUsernameFromCookies(request);
                if (username == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return false;
                }

                AccountModel account = accountRepository.findByUsername(username);
                if (account == null || !account.getRole().getRole().equalsIgnoreCase("admin")) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    return false;
                }
            }
        }
        return true;
    }

    private String extractUsernameFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}