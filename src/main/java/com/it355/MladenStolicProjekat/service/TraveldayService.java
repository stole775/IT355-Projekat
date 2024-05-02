package com.it355.MladenStolicProjekat.service;

import com.it355.MladenStolicProjekat.entity.Travelday;

import java.util.List;

public interface TraveldayService {
    List<Travelday> findAll();
    List<Travelday> findById(int traveldayId);
}
