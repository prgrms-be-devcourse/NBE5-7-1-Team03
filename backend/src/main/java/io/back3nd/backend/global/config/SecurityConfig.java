package io.back3nd.backend.global.config;

import io.back3nd.backend.domain.entity.Role;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form
                        .usernameParameter("email")
                        .failureHandler((request, response, exception) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setCharacterEncoding("UTF-8");
                            response.setContentType("application/json; charset=UTF-8");
                            response.getWriter().write("{\"message\": \"로그인 실패, " + exception.getMessage() + "\"}");
                        })
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/"))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/items/**").hasAuthority(Role.ADMIN.name())
                        .anyRequest().permitAll()
                )
                .build();
    }
}
