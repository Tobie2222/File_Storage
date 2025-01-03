package com.example.filestorage.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.core.annotation.Order;

import java.io.IOException;
import java.util.Enumeration;

@Component
@Order(1) // Đảm bảo filter chạy trước
public class RequestLoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        System.out.println("----- Request Details -----");
        System.out.println("Method: " + req.getMethod());
        System.out.println("Request URI: " + req.getRequestURI());
        System.out.println("Protocol: " + req.getProtocol());
        System.out.println("Content Type: " + req.getContentType());

        Enumeration<String> headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            System.out.println("Header: " + headerName + " = " + req.getHeader(headerName));
        }

        System.out.println("----- End Request Details -----");

        chain.doFilter(request, response);
    }
}