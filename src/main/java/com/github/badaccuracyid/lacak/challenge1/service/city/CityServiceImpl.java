package com.github.badaccuracyid.lacak.challenge1.service.city;

import com.github.badaccuracyid.lacak.challenge1.model.City;
import com.github.badaccuracyid.lacak.challenge1.repository.CityRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    /**
     * Find cities by name containing the query
     *
     * @param query The query string to search for city names
     * @return The list of {@link City} containing the query
     */
    @Override
    @Cacheable("cities")
    public List<City> findByNameContainingIgnoreCase(String query) {
        return cityRepository.findByNameContainingIgnoreCase(query);
    }

}
