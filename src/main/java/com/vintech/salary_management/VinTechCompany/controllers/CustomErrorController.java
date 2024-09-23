package com.vintech.salary_management.VinTechCompany.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity<String> handleError(HttpServletRequest request, WebRequest webRequest) {
        HttpStatus status = getStatus(request);
        if (status == HttpStatus.NOT_FOUND) {
            return new ResponseEntity<>(
                    "<div style='color: red; display: flex; justify-content: center; align-items: center; height: 100vh;'><h1 style='font-size:100px; font-family:inter;'>??????</h1></div>",
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(
                "<div style='color: red; display: flex; justify-content: center; align-items: center; height: 100vh;'><h1 style='font-size:100px; font-family:inter;'>??????</h1></div>",
                status);
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
        if (statusCode != null) {
            try {
                return HttpStatus.valueOf(statusCode);
            } catch (Exception ex) {
                // Ignore
            }
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}