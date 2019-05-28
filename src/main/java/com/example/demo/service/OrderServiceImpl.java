package com.example.demo.service;

import com.example.demo.enums.Status;
import com.example.demo.model.*;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ItemRepository itemRepository;
    @Override
    public Order createOrder(Order order) {
        order.setStatus(Status.OPEN);
        order.setTimeCreated(LocalDateTime.now());
        orderRepository.save(order);
        return order;
    }

    @Override
    public List<Item> addNewItemsToOrder(ItemWrapper itemWrapper, Long orderId) {
        return null;
    }

    @Override
    public OrderModel finishOrder(Long orderId, Courier courier) {
        return null;
    }

    @Override
    public OrderModel takeOrder(Long orderId, Courier courier) {
//        Order current = orderRepository.findById(orderId).get();
        OrderModel current = getOrderById(orderId);
        if(current.getOrder().getCourier()==null){
            current.getOrder().setCourier(courier);
            current.getOrder().setStatus(Status.TAKEN);
            orderRepository.save(current.getOrder());
            return current;
        }
        else
            return null;
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
