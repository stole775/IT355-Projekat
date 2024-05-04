package com.it355.MladenStolicProjekat.service.impl;


import com.it355.MladenStolicProjekat.entity.City;
import com.it355.MladenStolicProjekat.repository.CityRepository;
import com.it355.MladenStolicProjekat.service.CityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CityServiceImpl implements CityService {
    final CityRepository cityRepository;

    @Override
    public List<City> findByNameContains(String name) {
        return cityRepository.findByNameContains(name);
    }


    @Override
    public List<City> findAll() {
        return cityRepository.findAll();
    }

    @Override
    public Optional<City> findById(int id) {
        return cityRepository.findById(id);
    }



    @Override
    public List<City> findAllByCountryId(int country_id) {
        return cityRepository.findAllByCountryId(country_id);
    }


    public City saveOrUpdateCity(City city) {
        return cityRepository.save(city);
    }

    @Override
    public List<String> findImageUrlByCityId(int accommodationId) {
        return cityRepository.findImageUrlByCityId(accommodationId);
    }


    public void deleteCityById(int id) {
          cityRepository.deleteById(id);
    }


}
