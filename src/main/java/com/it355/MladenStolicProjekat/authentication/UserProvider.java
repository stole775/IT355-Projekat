package com.it355.MladenStolicProjekat.authentication;

import com.it355.MladenStolicProjekat.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserProvider {

    private final AdminService adminService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return username -> {
            return adminService.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
        };
    }
}
