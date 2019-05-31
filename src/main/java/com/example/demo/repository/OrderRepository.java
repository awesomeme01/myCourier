package com.example.demo.repository;

import com.example.demo.model.Order;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT count(*) FROM Order o WHERE LOWER(o.orderedBy.username) = LOWER(:username) and o.id = :orderId")
    public Integer belongsTo(@Param("orderId") Long orderId ,@Param("username") String username);
}
