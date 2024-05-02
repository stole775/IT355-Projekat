package com.it355.MladenStolicProjekat.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@ToString
@Table(name = "accommodations")
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id")
    private City city;

    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @ColumnDefault("0")
    @Column(name = "featured")
    private Boolean featured;

    @Lob
    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "price_from", precision = 10, scale = 2)
    private BigDecimal priceFrom;

    @Column(name = "number_of_nights")
    private Integer numberOfNights;

    @ColumnDefault("1")
    @Column(name = "available")
    private Boolean available;

    @Column(name = "priceListImageUrl")
    private String priceListImageUrl;

    @Lob
    @Column(name = "not_included")
    private String notIncluded;

}