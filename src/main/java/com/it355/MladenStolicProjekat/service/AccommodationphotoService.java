package com.it355.MladenStolicProjekat.service;

import com.it355.MladenStolicProjekat.entity.Accommodationphoto;

import java.util.List;

public interface AccommodationphotoService {

    List<String> findImageUrlByAccommodationId(int accommodationId);
    List<Accommodationphoto> findAll();
    void savePhoto(int accommodationId, String imageUrl);
}
