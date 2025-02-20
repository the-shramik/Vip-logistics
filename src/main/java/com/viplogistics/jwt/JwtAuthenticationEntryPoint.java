package com.viplogistics.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * {@link JwtAuthenticationEntryPoint} is a custom implementation of {@link AuthenticationEntryPoint} used to handle
 * unauthorized access attempts to secure endpoints.
 * <p>
 * This class is invoked when an unauthenticated request attempts to access a protected resource. It sends an
 * HTTP 401 Unauthorized response with a custom error message.
 * </p>
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * This method is invoked when an unauthenticated request is made to a secured resource. It sends a response
     * with HTTP status code 401 (Unauthorized) to indicate that the user is not authorized to access the resource.
     *
     * @param request The HTTP request that triggered the exception.
     * @param response The HTTP response to send back to the client.
     * @param authException The authentication exception that triggered the entry point.
     * @throws IOException If an input or output error occurs during processing the response.
     * @throws ServletException If the request cannot be handled due to some servlet-related issues.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized : Server");
    }
}
