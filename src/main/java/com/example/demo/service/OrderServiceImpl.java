package com.example.demo.service;

import com.example.demo.enums.Status;
import com.example.demo.model.Courier;
import com.example.demo.model.Item;
import com.example.demo.model.Order;
import com.example.demo.model.OrderModel;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public OrderModel createOrder(OrderModel order) {
        for(Item item:order.getItemList()) {
            item.setOrder(order.getOrder());
            itemRepository.save(item);
        }
        order.getOrder().setStatus(Status.OPEN);
        orderRepository.save(order.getOrder());
        return order;
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
