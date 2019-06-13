package com.example.demo.repository;

import com.example.demo.model.Order;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT count(*) FROM Order o WHERE LOWER(o.orderedBy.username) = LOWER(:username) and o.id = :orderId")
    public Integer belongsTo(@Param("orderId") Long orderId ,@Param("username") String username);
//    @Query("SELECT o FROM Order o WHERE LOWER(o.orderedBy.username) = LOWER(:username)")
    public List<Order> findOrdersByOrderedBy(User user);
}
