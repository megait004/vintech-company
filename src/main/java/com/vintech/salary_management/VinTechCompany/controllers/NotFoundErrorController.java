package com.vintech.salary_management.VinTechCompany.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class NotFoundErrorController implements ErrorController {
    private static final Logger logger = LoggerFactory.getLogger(NotFoundErrorController.class);

    @RequestMapping("/error")
    public ResponseEntity<String> handleError(HttpServletRequest request, WebRequest webRequest) {
        HttpStatus status = getStatus(request);
        if (status == HttpStatus.NOT_FOUND) {
            return new ResponseEntity<>(
                    "<div style='color: red; display: flex; justify-content: center; align-items: center; height: 100vh;'><h1 style='font-size:200px; font-family:inter;'>??????</h1></div>",
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(
                "<div style='color: red; display: flex; justify-content: center; align-items: center; height: 100vh;'><h1 style='font-size:200px; font-family:inter;'>??????</h1></div>",
                status);
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
        if (statusCode != null) {
            try {
                return HttpStatus.valueOf(statusCode);
            } catch (Exception ex) {
                logger.error("Lỗi: ", ex);
            }
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}