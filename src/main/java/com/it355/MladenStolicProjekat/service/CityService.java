package com.it355.MladenStolicProjekat.service;

import com.it355.MladenStolicProjekat.entity.City;


import java.util.List;
import java.util.Optional;

public interface CityService {
    List<City> findByNameContains(String name);
    List<City> findAll();
    Optional<City> findById(int id);
    List<City> findAllByCountryId(int country_id);
}
