package com.github.badaccuracyid.lacak.challenge1.repository;

import com.github.badaccuracyid.lacak.challenge1.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<Long, City> {

    @Query(value = "SELECT c FROM City c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<City> findByNameContainingIgnoreCase(@Param("query") String query);

}
