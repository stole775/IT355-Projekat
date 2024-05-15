package com.it355.MladenStolicProjekat.service.impl;

import com.it355.MladenStolicProjekat.entity.Country;
import com.it355.MladenStolicProjekat.repository.CountryRepository;
import com.it355.MladenStolicProjekat.service.CountryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CountryServiceImpl implements CountryService {
    final CountryRepository countryRepository;


    @Override
    public List<Country> findAll() {
        return countryRepository.findAll();
    }

    @Override
    public List<Country> findByNameContains(String name) {
        return countryRepository.findByNameContains(name);
    }

    @Override
    public Optional<Country> findById(int id) {
        return countryRepository.findById(id);
    }

    @Override
    public List<Country> findByNameContainsIgnoreCase(String name) {
        return countryRepository.findByNameContainsIgnoreCase(name);
    }

    @Override
    public void deleteById(int id) {
        countryRepository.deleteById(id);
    }
}
