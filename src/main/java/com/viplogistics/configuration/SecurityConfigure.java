package com.viplogistics.configuration;

import com.viplogistics.jwt.JwtAuthenticationEntryPoint;
import com.viplogistics.jwt.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SecurityConfigure - Configures security settings for the application.
 * <p>
 * This class sets up JWT-based authentication, disables CSRF, and configures role-based access control.
 * </p>
 *
 * <p><strong>Key Features:</strong></p>
 * - Uses {@link JwtAuthenticationFilter} to handle JWT authentication.
 * - Handles unauthorized requests via {@link JwtAuthenticationEntryPoint}.
 * - Disables CSRF for stateless authentication.
 * - Configures session management to be stateless.
 * - Secures API endpoints and allows public access to authentication-related endpoints.
 *
 * @author Shramik Masti
 * @author Shubham Lohar
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfigure {
    private final JwtAuthenticationFilter jwtRequestFilter;
    private final JwtAuthenticationEntryPoint point;

    /**
     * Configures HTTP security settings.
     *
     * @param security The {@link HttpSecurity} object to configure security settings.
     * @return {@link SecurityFilterChain} - Configured security filter chain.
     * @throws Exception If an error occurs while configuring security.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        return security.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/service/login", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/test", "/chat","/auth/**",
                                    "/api/service/is-token-expired", "/api/user-management/get-all-roles").permitAll()
                            .requestMatchers(HttpMethod.OPTIONS).permitAll()
                            .anyRequest().authenticated();
                })
                .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * Creates a password encoder bean using {@link BCryptPasswordEncoder}.
     *
     * @return {@link PasswordEncoder} - A password encoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures and provides an {@link AuthenticationManager} bean.
     *
     * @param configuration The {@link AuthenticationConfiguration} for authentication management.
     * @return {@link AuthenticationManager} - The configured authentication manager.
     * @throws Exception If an error occurs while obtaining the authentication manager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
