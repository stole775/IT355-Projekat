package com.it355.MladenStolicProjekat.service;

import com.it355.MladenStolicProjekat.entity.Country;

import java.util.List;

public interface CountryService {
    List<Country> findAll();
    List<Country> findByNameContains(String name);//ispravi da radi contains
    List<Country> findById(int id);
    List<Country> findByNameContainsIgnoreCase(String name);
}
