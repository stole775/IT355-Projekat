package com.it355.MladenStolicProjekat.service.impl;

import com.it355.MladenStolicProjekat.entity.City;
import com.it355.MladenStolicProjekat.repository.CityRepository;
import com.it355.MladenStolicProjekat.service.CityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
@AllArgsConstructor
public class CityServiceImpl implements CityService {
    final CityRepository cityRepository;

    @Override
    public List<City> findByNameContains(String name) {
        return cityRepository.findByNameContains(name);
    }

    @Override
    public List<City> findByCountryId(int country_id) {
        return cityRepository.findByCountryId(country_id);
    }

    @Override
    public List<City> findAll() {
        return cityRepository.findAll();
    }

    @Override
    public List<City> findByNameContainsIgnoreCase(String name) {
        return cityRepository.findByNameContainsIgnoreCase(name);
    }

    @Override
    public List<City> findAllByCountryId(int country_id) {
        return cityRepository.findAllByCountryId(country_id);
    }
}
