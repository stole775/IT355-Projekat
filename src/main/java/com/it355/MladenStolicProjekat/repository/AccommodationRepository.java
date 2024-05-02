package com.it355.MladenStolicProjekat.repository;

import com.it355.MladenStolicProjekat.entity.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Integer> {
    @Query(value = "SELECT * FROM accommodations", nativeQuery = true)
    List<Accommodation> findAll();

    List<Accommodation> findByName(String name);
    List<Accommodation> findByNameContaining(String name);
    @Query(value="SELECT * FROM Accommodations WHERE featured = TRUE",nativeQuery = true)
    List<Accommodation> istaknutiOglasi();
    @Query(value = "SELECT * FROM Accommodations WHERE city_id = :city_id",nativeQuery = true)
    List<Accommodation> findByCityId(int city_id);
    Optional<Accommodation> findById(int id);
    @Query(value = "SELECT * FROM Accommodations WHERE name LIKE :name LIMIT 4",nativeQuery = true)
    List<Accommodation> findByNameContainsIgnoreCase(String name);
}
