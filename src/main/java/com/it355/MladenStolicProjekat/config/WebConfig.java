package com.it355.MladenStolicProjekat.config;

import com.it355.MladenStolicProjekat.jwt.filters.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200"));
                    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                    corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
                    corsConfiguration.setAllowCredentials(true);
                    return corsConfiguration;
                }))
                .sessionManagement(sessionMangConfig -> sessionMangConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authConfig -> authConfig
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()  // Allow everyone to access login
                        .requestMatchers("/actuator/health", "/actuator/info").permitAll()  // Allow all to access health and info endpoints
                        .requestMatchers(HttpMethod.GET, "/**").permitAll()  // Allow all to access GET requests
                        .requestMatchers(HttpMethod.POST, "/**").hasAuthority("FULL_ACCESS")  // Allow only ADMIN to perform POST
                        .requestMatchers(HttpMethod.PUT, "/**").hasAuthority("FULL_ACCESS")  // Allow only ADMIN to perform PUT
                        .requestMatchers(HttpMethod.DELETE, "/**").hasAuthority("FULL_ACCESS")  // Allow only ADMIN to perform DELETE
                        .anyRequest().authenticated()  // All other requests need to be authenticated
                );


        return http.build();
    }
}
