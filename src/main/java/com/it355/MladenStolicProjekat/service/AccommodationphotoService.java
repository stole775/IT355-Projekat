package com.it355.MladenStolicProjekat.service;

import com.it355.MladenStolicProjekat.entity.Accommodationphoto;

import java.util.List;

public interface AccommodationphotoService {
    List<Accommodationphoto> findAllByid(Integer id);
    List<Accommodationphoto> findAll();
    List<String> imageURLbyAccommodationId(Integer accommodationId);
    void savePhoto(int accommodationId, String imageUrl);
}
