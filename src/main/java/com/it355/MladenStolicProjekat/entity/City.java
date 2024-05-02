package com.it355.MladenStolicProjekat.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@Table(name = "cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "country_id")
    private Country country;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "opisGrada", nullable = false)
    private String opisGrada;

    @Lob
    @Column(name = "slikaGradaURL", nullable = false)
    private String slikaGradaURL;

}