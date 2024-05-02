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

@Service
@AllArgsConstructor
public class AccommodationphotoServiceImpl implements AccommodationphotoService {
    final AccommodationphotoRepository accommodationphotoRepository;
    final AccomodationService accomodationService;

    @Override
    public List<Accommodationphoto> findAllByid(Integer id) {
        return accommodationphotoRepository.findAllByid(id);
    }


    @Override
    public List<Accommodationphoto> findAll() {
        return accommodationphotoRepository.findAll();
    }

    @Override
    public List<String> imageURLbyAccommodationId(Integer accommodationId) {
        return accommodationphotoRepository.imageURLbyAccommodationId(accommodationId);
    }

    @Override
    @Transactional
    public void savePhoto(int accommodationId, String imageUrl) {
        Accommodation accommodation = accomodationService.findById(accommodationId)
                .orElseThrow(() -> new RuntimeException("Accommodation not found with id: " + accommodationId));

        Accommodationphoto photo = new Accommodationphoto();
        photo.setAccommodation(accommodation);
        photo.setImageUrl(imageUrl);
        accommodationphotoRepository.save(photo);
    }
}
