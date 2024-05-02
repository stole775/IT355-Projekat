package com.it355.MladenStolicProjekat.repository;

import com.it355.MladenStolicProjekat.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
    @Override
    List<Country> findAll();
    List<Country> findByNameContains(String name);
    List<Country> findById(int id);
    @Query(value = "SELECT * FROM Countries WHERE name LIKE %:name% LIMIT 4", nativeQuery = true)
    List<Country> findByNameContainsIgnoreCase(String name);


}
