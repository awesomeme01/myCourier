package com.example.demo.repository;

import com.example.demo.model.Courier;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourierRepository extends JpaRepository<Courier, Long> {
    @Query("SELECT c FROM Courier c WHERE LOWER(c.user.username) = LOWER(:username)")
    public Courier findByUsername(@Param("username") String username);
}
