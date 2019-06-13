package com.example.demo.service;

import com.example.demo.Helper.ItemWrapper;
import com.example.demo.Helper.OrderModel;
import com.example.demo.enums.CourierStatus;
import com.example.demo.enums.Status;
import com.example.demo.model.*;
import com.example.demo.repository.CommentRepository;
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
    @Autowired
    CourierService courierService;
    @Autowired
    CommentRepository commentRepository;
    @Override
    public Order createOrder(Order order) {
        order.setStatus(Status.OPEN);
        order.setTimeCreated(LocalDateTime.now());
        orderRepository.save(order);
        return order;
    }

    @Override
    public OrderModel addNewItemsToOrder(ItemWrapper itemWrapper, Long orderId) {
        Order order = orderRepository.findById(orderId).get();
        for (Item item : itemWrapper.getItemList()
             ) {
            item.setOrder(order);
            itemRepository.save(item);
        }
        return getOrderById(orderId);
    }

    @Override
    public OrderModel finishOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).get();
        order.setStatus(Status.AWAITING_CONFIRMATION);
        orderRepository.save(order);
        return getOrderById(orderId);
    }

    @Override
    public OrderModel declineOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).get();
        order.setSuccessful(false);
        order.setStatus(Status.DECLINED);
//        order.setCourier(null);
        order.setCourier(null);
        orderRepository.save(order);
        return getOrderById(orderId);

    }

    @Override
    public OrderModel cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).get();
        order.setStatus(Status.CLOSED);
        order.setSuccessful(false);
        orderRepository.save(order);
        return getOrderById(orderId);
    }

    @Override
    public OrderModel confirmOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).get();
        order.setSuccessful(true);
        order.setStatus(Status.COMPLETED);
        return getOrderById(orderId);
    }

    @Override
    public OrderModel takeOrder(Long orderId, Courier courier) {
        OrderModel current = getOrderById(orderId);
        if(current.getOrder().getCourier()==null){
            if(courier.getStatus().equals(CourierStatus.BANNED)){
                return null;
            }
            else if(courier.getStatus().equals(CourierStatus.VACATION)){
                courierService.updateCourierStatus(courier,CourierStatus.ACTIVE);
            }
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
                            .collect(Collectors.toList()),
                    commentRepository.getCommentsByOrder(order)
                    ));
        }
        return orderModels;
    }

    @Override
    public List<Order> getOrderByUser(User user) {
        return orderRepository.findOrdersByOrderedBy(user);
    }

    @Override
    public List<Order> getOpenOrders() {
        return orderRepository.findAll().stream().filter(x->x.getStatus().equals(Status.OPEN)).collect(Collectors.toList());
    }

    @Override
    public OrderModel getOrderById(Long id) {
//        orderRepository;
        if(orderRepository.findById(id).isPresent()) {
            return new OrderModel(
                    orderRepository.findById(id).get()
                    , itemRepository.findAll().stream()
                    .filter(x -> x.getOrder().getId().equals(id))
                    .collect(Collectors.toList()),commentRepository.getCommentsByOrder(orderRepository.findById(id).get()));
        }
        return null;
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
        for(Item item :itemRepository.findAll().stream().filter(x->x.getOrder().getId().equals(id)).collect(Collectors.toList())){
            itemRepository.delete(item);
        }
    }
}
