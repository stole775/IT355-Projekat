package com.it355.MladenStolicProjekat.service;

import com.it355.MladenStolicProjekat.entity.City;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CityService {
    List<City> findByNameContains(String name);
    List<City> findByCountryId(int country_id);
    List<City> findAll();
    Optional<City> findById(int id);
    List<City> findAllByCountryId(int country_id);
    City saveOrUpdateCity(City accommodation);
    void deleteCityById(int id);
}
