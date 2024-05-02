package com.it355.MladenStolicProjekat.service.impl;

import com.it355.MladenStolicProjekat.entity.Travelday;
import com.it355.MladenStolicProjekat.repository.TraveldayRepository;
import com.it355.MladenStolicProjekat.service.TraveldayService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TraveldayServiceImpl implements TraveldayService {
    final TraveldayRepository traveldayRepository;

    @Override
    public List<Travelday> findAll() {
        return traveldayRepository.findAll();
    }

    public List<Travelday> findById(int traveldayId) {
        return traveldayRepository.findById(traveldayId);
    }
}
