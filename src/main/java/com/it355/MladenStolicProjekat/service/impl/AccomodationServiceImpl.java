package com.it355.MladenStolicProjekat.service.impl;

import com.it355.MladenStolicProjekat.entity.Accommodation;
import com.it355.MladenStolicProjekat.repository.AccommodationRepository;
import com.it355.MladenStolicProjekat.service.AccomodationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccomodationServiceImpl implements AccomodationService {

    final AccommodationRepository accomodationRepository;

    @Override
    public List<Accommodation> findAll() {
        return accomodationRepository.findAll();
    }

    @Override
    public List<Accommodation> findByName(String name) {
        return accomodationRepository.findByName(name);
    }

    @Override
    public List<Accommodation> findByNameContaining(String name) {
        return accomodationRepository.findByNameContaining(name);
    }

    @Override
    public List<Accommodation> istaknutiOglasi(){
        return accomodationRepository.istaknutiOglasi();
    }

    @Override
    public List<Accommodation> findByCityId(int city_id) {
        return accomodationRepository.findByCityId(city_id);
    }
    @Override
    public Optional<Accommodation> findById(int id) {
        return accomodationRepository.findById(id);
    }

    @Override
    public List<Accommodation> findByNameContainsIgnoreCase(String name) {
        return accomodationRepository.findByNameContainsIgnoreCase(name);
    }

}
