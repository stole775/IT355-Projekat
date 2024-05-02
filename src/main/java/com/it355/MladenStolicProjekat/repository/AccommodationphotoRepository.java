package com.it355.MladenStolicProjekat.repository;

import com.it355.MladenStolicProjekat.entity.Accommodationphoto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccommodationphotoRepository extends JpaRepository<Accommodationphoto, Integer> {
    List<Accommodationphoto> findAllByid(Integer id);
    List<Accommodationphoto> findAll();
    @Query(value = " SELECT imageUrl FROM AccommodationPhotos\n" +
            "        WHERE accommodationId = ?;",nativeQuery = true)
    List<String> imageURLbyAccommodationId(Integer id);


}
