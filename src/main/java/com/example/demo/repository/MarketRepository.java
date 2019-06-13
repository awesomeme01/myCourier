package com.example.demo.repository;

import com.example.demo.model.Market;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MarketRepository extends JpaRepository<Market, Long> {
    @Query("SELECT m FROM Market m WHERE LOWER(m.name) = LOWER(:name)")
    public Market findByName(@Param("name") String name);
}
