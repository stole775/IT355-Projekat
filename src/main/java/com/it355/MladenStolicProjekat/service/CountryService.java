package com.it355.MladenStolicProjekat.service;

import com.it355.MladenStolicProjekat.entity.Country;

import java.util.List;
import java.util.Optional;

public interface CountryService {
    List<Country> findAll();
    List<Country> findByNameContains(String name);//ispravi da radi contains
    Optional<Country> findById(int id);
    List<Country> findByNameContainsIgnoreCase(String name);
    void deleteById(int id);
}
