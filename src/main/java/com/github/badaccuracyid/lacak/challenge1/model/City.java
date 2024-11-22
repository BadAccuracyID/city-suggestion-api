package com.github.badaccuracyid.lacak.challenge1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Table(
        name = "cities",
        indexes = {
                @Index(name = "name_index", columnList = "name", unique = true),
                @Index(name = "latitude_index", columnList = "latitude"),
                @Index(name = "longitude_index", columnList = "longitude")
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
    @Column(name = "latitude", nullable = false)
    private double latitude;

    @JsonProperty("longitude")
    @Column(name = "longitude", nullable = false)
    private double longitude;

}
