package com.github.badaccuracyid.lacak.challenge1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "cities",
        indexes = {
                @Index(name = "name_index", columnList = "name")
        }
)
@Entity
@Getter
@Setter
public class City {

    @Id
    @JsonProperty("id")
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("name")
    @Column(name = "name", nullable = false)
    private String name;

    @JsonProperty("latitude")
    @Column(name = "latitude", nullable = false, columnDefinition = "DOUBLE PRECISION")
    private double latitude;

    @JsonProperty("longitude")
    @Column(name = "longitude", nullable = false, columnDefinition = "DOUBLE PRECISION")
    private double longitude;

    @JsonProperty
    @Column(name = "population", nullable = false, columnDefinition = "BIGINT")
    private BigInteger population;

}
