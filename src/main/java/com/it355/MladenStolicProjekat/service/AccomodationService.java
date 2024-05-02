package com.it355.MladenStolicProjekat.service;

import com.it355.MladenStolicProjekat.entity.Accommodation;

import java.util.List;
import java.util.Optional;

public interface AccomodationService {
    List<Accommodation> findAll();
    List<Accommodation> findByName(String name);
    List<Accommodation> findByNameContaining(String name);
    List<Accommodation> istaknutiOglasi();
    List<Accommodation> findByCityId(int city_id);
    Optional<Accommodation> findById(int id);
    List<Accommodation> findByNameContainsIgnoreCase(String name);
}
