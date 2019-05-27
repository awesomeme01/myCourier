package com.example.demo.controller;

import com.example.demo.model.CustomUser;
import com.example.demo.model.OrderModel;
import com.example.demo.model.Response;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/orders")
public class OrderController {
    @Autowired
    OrderService orderService;
    @GetMapping(path = "/getAll")
    public Response getAll(){
        return new Response(true, "All orders", orderService.getAllOrders());
    }
    @GetMapping(path = "/getById/{id}")
    public Response getById(@PathVariable Long id){
        return new Response(true,"Order with id = " + id, orderService.getOrderById(id));
    }
    @PostMapping(path = "/create")
    public Response createOrder(@AuthenticationPrincipal CustomUser customUser, @RequestBody OrderModel orderModel){
        System.out.println(customUser);
        return new Response(true, "Order created successfully", orderService.createOrder(orderModel));
    }
    @DeleteMapping(path = "/delete/{id}")
    public Response deleteOrder(@PathVariable Long id){
        orderService.deleteOrder(id);
        return new Response(true, "Order with id = " + id + " deleted!!", null);
    }
}
