package com.it355.MladenStolicProjekat.service.impl;

import com.it355.MladenStolicProjekat.entity.Accommodation;
import com.it355.MladenStolicProjekat.entity.Accommodationphoto;
import com.it355.MladenStolicProjekat.repository.AccommodationphotoRepository;
import com.it355.MladenStolicProjekat.service.AccommodationphotoService;
import com.it355.MladenStolicProjekat.service.AccomodationService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccommodationphotoServiceImpl implements AccommodationphotoService {
    final AccommodationphotoRepository accommodationphotoRepository;
    final AccomodationService accomodationService;


    @Override
    public List<String> findImageUrlByAccommodationId(int accommodationId) {
        return accommodationphotoRepository.findImageUrlByAccommodationId(accommodationId);
    }

    @Override
    public List<Accommodationphoto> findAll() {
        return accommodationphotoRepository.findAll();
    }

    @Override
    @Transactional
    public void savePhoto(int accommodationId, String imageUrl) {
        Accommodation accommodation = accomodationService.findById(accommodationId)
                .orElseThrow(() -> new RuntimeException("Accommodation nepostoji sa ovim  id: " + accommodationId));

        Accommodationphoto photo = new Accommodationphoto();
        photo.setAccommodation(accommodation);
        photo.setImageUrl(imageUrl);
        accommodationphotoRepository.save(photo);
    }

    @Override
    public List<Accommodationphoto> findAllByAccommodationId(int accommodationId) {
        return accommodationphotoRepository.findAllByAccommodationId(accommodationId);
    }

    @Override
    public void delete(int id) {
        accommodationphotoRepository.deleteById(id);
    }

    @Override
    public Optional<?> getById(int id) {
        return accommodationphotoRepository.findById(id);
    }

    @Override
    public void deleteByImageUrl(String imageUrl) {
        accommodationphotoRepository.deleteByImageUrl(imageUrl);
    }


}
