package org.ts.techsieciowelista2.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security configuration
 */
//W bazie nazwa roli ROLE_NAZWA
@Configuration
@EnableMethodSecurity
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
public class SecurityConfig {
    @Value("${jwt.token.key}")
    private String key;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JWTTokenFilter(key), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                        .requestMatchers("/Login").permitAll()
                        .requestMatchers("/users/**").permitAll()//hasRole("LIBRARIAN")
                        .requestMatchers("/Book/Add").permitAll()//hasRole("LIBRARIAN")
                        .requestMatchers("/Book/GetAll").permitAll()
                        .requestMatchers("/Book/SearchBy/**").permitAll()
                        .requestMatchers("/Book/deleteBook/**").hasRole("LIBRARIAN")
                        .requestMatchers("/Book/updateBook/**").hasRole("LIBRARIAN")
                        .requestMatchers("/Loan/**").permitAll())//hasRole("LIBRARIAN"))

                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)).build();

    }
}
