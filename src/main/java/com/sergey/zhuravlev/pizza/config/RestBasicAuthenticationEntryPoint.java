package com.sergey.zhuravlev.pizza.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sergey.zhuravlev.pizza.dto.error.ErrorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@RequiredArgsConstructor
public class RestBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        response.addHeader("WWW-Authenticate", "Basic realm=\"" + getRealmName() +  "\"");
        response.addHeader("Content-Type", "application/json; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = response.getWriter();
        objectMapper.writeValue(writer, new ErrorDto(getCode(authException), authException.getMessage()));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName("Pizza");
        super.afterPropertiesSet();
    }

    private String getCode(AuthenticationException authException) {
        if (authException instanceof BadCredentialsException) {
            return "BAD_CREDENTIALS";
        } else {
            return "UNAUTHORIZED";
        }
    }

}
