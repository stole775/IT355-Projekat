package com.it355.MladenStolicProjekat.service;

import com.it355.MladenStolicProjekat.entity.Admin;

import java.util.Optional;

public interface AdminService {
    Optional<Admin> findByUsername(String username);
    void save(Admin admin);
}
