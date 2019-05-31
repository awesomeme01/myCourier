package com.example.demo.service;

import com.example.demo.model.*;

import java.util.List;

public interface OrderService {
    OrderModel takeOrder(Long orderId, Courier courier);
    OrderModel finishOrder(Long orderId, Courier courier);
    Order createOrder(Order order);
    OrderModel addNewItemsToOrder(ItemWrapper itemWrapper, Long orderId);
    List<OrderModel> getAllOrders();

    OrderModel getOrderById(Long id);
    void deleteOrder(Long id);
}
