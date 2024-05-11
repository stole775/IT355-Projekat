package com.it355.MladenStolicProjekat.service.impl;

import com.it355.MladenStolicProjekat.entity.Admin;
import com.it355.MladenStolicProjekat.repository.AdminRepository;
import com.it355.MladenStolicProjekat.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    final AdminRepository adminRepository;

    @Override
    public Optional<Admin> findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }

    @Override
    public void save(Admin admin) {
        adminRepository.save(admin);
    }
}
