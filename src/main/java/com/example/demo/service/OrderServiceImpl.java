package com.example.demo.service;

import com.example.demo.model.Item;
import com.example.demo.model.Order;
import com.example.demo.model.OrderModel;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService{
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ItemRepository itemRepository;
    @Override
    public Order createOrder(OrderModel order) {
        for(Item item:order.getItemList()) {
            item.setOrder(order.getOrder());
            itemRepository.save(item);
        }
        return orderRepository.save(order.getOrder());
    }

    @Override
    public List<OrderModel> getAllOrders() {
        List<OrderModel> orderModels = new ArrayList<>();
        for(Order order: orderRepository.findAll()) {
            orderModels.add(new OrderModel(
                    order,
                    itemRepository
                            .findAll()
                            .stream()
                            .filter(x->x.getOrder().getId().equals(order.getId()))
                            .collect(Collectors.toList())
            ));
        }
        return orderModels;
    }

    @Override
    public OrderModel getOrderById(Long id) {
        return new OrderModel(
                orderRepository.findById(id).get()
                ,itemRepository.findAll().stream()
                    .filter(x->x.getOrder().getId().equals(id))
                    .collect(Collectors.toList()));
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
        for(Item item :itemRepository.findAll().stream().filter(x->x.getOrder().getId().equals(id)).collect(Collectors.toList())){
            itemRepository.delete(item);
        }
    }
}
