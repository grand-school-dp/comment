package com.school.commentmanager.config;

import com.school.commentmanager.storage.IPStorage;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RequestInterceptor extends OncePerRequestFilter {

    @Value("${security.admin.secret}")
    private String adminSecret;

    private final IPStorage ipStorage;

    public RequestInterceptor(IPStorage ipStorage) {
        this.ipStorage = ipStorage;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (request.getMethod() == null
                || !request.getMethod().equalsIgnoreCase("POST")
                || (request.getHeader("secret") != null && request.getHeader("secret").equals(adminSecret))) {
            filterChain.doFilter(request, response);
            return;
        }
        String ipAddress = request.getRemoteAddr();
        ipStorage.incrementRequestCount(ipAddress);
        int requestCountByTime = ipStorage.getRequestCountByTime(ipAddress);
        int requestCount = ipStorage.getRequestCount(ipAddress);

        if (requestCountByTime >= 3 || requestCount >= 5) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            return;
        }

        filterChain.doFilter(request, response);
    }
}

