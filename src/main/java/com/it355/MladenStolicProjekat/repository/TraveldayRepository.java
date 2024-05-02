package com.it355.MladenStolicProjekat.repository;

import com.it355.MladenStolicProjekat.entity.Travelday;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TraveldayRepository extends CrudRepository<Travelday, Integer> {

    List<Travelday> findAll();
    List<Travelday> findById(int id);
}
