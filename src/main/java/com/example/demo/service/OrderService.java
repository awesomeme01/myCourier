package com.example.demo.service;

import com.example.demo.model.*;

import java.util.List;

public interface OrderService {
    OrderModel takeOrder(Long orderId, Courier courier);
    OrderModel declineOrder(Long orderId);
    OrderModel cancelOrder(Long orderId);
    OrderModel finishOrder(Long orderId);
    OrderModel confirmOrder(Long orderId);
    Order createOrder(Order order);
    OrderModel addNewItemsToOrder(ItemWrapper itemWrapper, Long orderId);
    List<OrderModel> getAllOrders();
    OrderModel getOrderById(Long id);
    void deleteOrder(Long id);
}
