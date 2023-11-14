package com.netflop.be.movie.component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
    @Serial
    private static final long serialVersionUID = -7858869558953243875L;

    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException authException
    ) throws IOException {
        log.error(authException.getMessage());
        authException.printStackTrace();

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
