package com.it355.MladenStolicProjekat.controller;

import com.it355.MladenStolicProjekat.entity.Admin;
import com.it355.MladenStolicProjekat.service.AdminService;
import com.it355.MladenStolicProjekat.service.impl.AdminServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    final AdminServiceImpl adminService;

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody Admin updatedAdmin) {
        Optional<Admin> existingAdminOptional = adminService.findByUsername(updatedAdmin.getUsername());
        if (!existingAdminOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Admin existingAdmin = existingAdminOptional.get();

        // Ažuriranje podataka
        existingAdmin.setUsername(updatedAdmin.getUsername());
        existingAdmin.setPassword(new BCryptPasswordEncoder().encode(updatedAdmin.getPassword()));

        adminService.save(existingAdmin);
        return ResponseEntity.ok().body("User updated successfully");
    }

}
