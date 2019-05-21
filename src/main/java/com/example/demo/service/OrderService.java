package com.example.demo.service;

import com.example.demo.model.Order;
import com.example.demo.model.OrderModel;

import java.util.List;

public interface OrderService {
    Order createOrder(OrderModel order);
    List<OrderModel> getAllOrders();
    OrderModel getOrderById(Long id);
    void deleteOrder(Long id);
}
