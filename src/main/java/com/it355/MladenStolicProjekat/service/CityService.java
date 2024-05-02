package com.it355.MladenStolicProjekat.service;

import com.it355.MladenStolicProjekat.entity.City;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CityService {
    List<City> findByNameContains(String name);
    List<City> findByCountryId(int country_id);
    List<City> findAll();
    List<City> findByNameContainsIgnoreCase(String name);
    List<City> findAllByCountryId(int country_id);
}
