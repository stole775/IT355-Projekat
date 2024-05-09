package com.it355.MladenStolicProjekat.controller;


import com.it355.MladenStolicProjekat.authentication.AuthenticationService;
import com.it355.MladenStolicProjekat.dto.LoginDTO;
import com.it355.MladenStolicProjekat.dto.LoginResponse;
import com.it355.MladenStolicProjekat.entity.Admin;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginResponseResponseEntity(@RequestBody LoginDTO loginDTO){
        return ResponseEntity.ok(authenticationService.login(loginDTO));
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> registerResponseResponseEntity(@RequestBody Admin admin){
        return ResponseEntity.ok(authenticationService.register(admin));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(value = "Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            authenticationService.logout(token);
            return ResponseEntity.ok(Map.of("message", "Logged out successfully."));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid authorization header.");
        }
    }




}
