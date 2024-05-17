package com.it355.MladenStolicProjekat.service;

import com.it355.MladenStolicProjekat.entity.Accommodationphoto;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccommodationphotoService {

    List<String> findImageUrlByAccommodationId(int accommodationId);
    List<Accommodationphoto> findAll();
    void savePhoto(int accommodationId, String imageUrl);
    List<Accommodationphoto> findAllByAccommodationId(int accommodationId);
    void delete(int id);
    Optional<?> getById(int id);
    void deleteByImageUrl(@Param("imageUrl") String imageUrl);
}
