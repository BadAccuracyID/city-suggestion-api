package com.github.badaccuracyid.lacak.challenge1.service.city;

import com.github.badaccuracyid.lacak.challenge1.model.City;

import java.util.List;

public interface CityService {

    List<City> findByNameContainingIgnoreCase(String query);

}
