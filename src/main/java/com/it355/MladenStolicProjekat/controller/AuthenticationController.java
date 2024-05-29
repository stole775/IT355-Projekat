package com.it355.MladenStolicProjekat.controller;


import com.it355.MladenStolicProjekat.authentication.AuthenticationService;
import com.it355.MladenStolicProjekat.dto.LoginDTO;
import com.it355.MladenStolicProjekat.dto.LoginResponse;
import com.it355.MladenStolicProjekat.entity.Admin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Authentication Controller", description = "Upravljanje autentifikacijom")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Ova metoda omogućava korisnicima da se prijave")
    public ResponseEntity<LoginResponse> loginResponseResponseEntity(@RequestBody LoginDTO loginDTO){
        return ResponseEntity.ok(authenticationService.login(loginDTO));
    }

    @PostMapping("/register")
    @Operation(summary = "Registracija", description = "Ova metoda omogućava korisnicima da se registruju")
    public ResponseEntity<LoginResponse> registerResponseResponseEntity(@RequestBody Admin admin){
        return ResponseEntity.ok(authenticationService.register(admin));
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout", description = "Ova metoda omogućava korisnicima da se odjave")
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
