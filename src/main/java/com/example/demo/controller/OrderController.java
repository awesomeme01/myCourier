package com.example.demo.controller;

import com.example.demo.enums.CourierStatus;
import com.example.demo.model.*;
import com.example.demo.repository.CourierRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserRolesService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.NoSuchElementException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(path = "/api/orders")
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    UserRolesService userRolesService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CourierRepository courierRepository;
    @Autowired
    UserService userService;
    //-------------------------------

    //-------------------------------
    //tested
    @Secured("ROLE_ADMIN")
    @GetMapping(path = "/getAll")
    public Response getAll(){
        return new Response(true, "All orders", orderService.getAllOrders());
    }
    //tested - needs one more final test
    @Secured("ROLE_ADMIN")
    @GetMapping(path = "/getById/{id}")
    public Response getById(Principal principal, @PathVariable Long id){
        try {
            return new Response(true, "Order with id = " + id, orderService.getOrderById(id));
        }
        catch (NoSuchElementException exception){
            System.out.println(exception.getMessage());
            return new Response(false, "Order with id = "+id+ " doesn't exist", exception.getMessage());
        }
    }
    //tested - needs one more final test
    @Secured("ROLE_USER")
    @PostMapping(path = "/create")
    public Response createOrder(Principal principal, @RequestBody Order order){
        order.setOrderedBy(userRepository.findByUsername(principal.getName()));
        return new Response(true, "Order created successfully", orderService.createOrder(order));
    }
    //tested - needs one more final test
    @PutMapping(path = "/addItemToOrder/{orderId}")
    public Response addItemToOrder(Principal principal, @PathVariable Long orderId, @RequestBody ItemWrapper itemWrapper){
        if(!orderRepository.existsById(orderId)){
            return new Response(false, "Such order doesn't exits", null);
        }
        if(orderRepository.belongsTo(orderId,principal.getName()).equals(1)){
            return new Response(true, "New items were added to order", orderService.addNewItemsToOrder(itemWrapper,orderId));
        }
        else {
            return new Response(false, "Order with id = " + orderId + " doesn't belong to the current user", null);
        }
    }
    //tested - need's one more test
    @Secured("ROLE_COURIER")
    @PutMapping(path = "/takeOrder/{id}")
    public Response takeOrder(Principal principal, @PathVariable Long id){
        if(!orderRepository.existsById(id)){
            return new Response(false, "Such order doesn't exist",null);
        }
        if(courierRepository.findByUsername(principal.getName()).getStatus().equals(CourierStatus.BANNED)){
            return new Response(false, "Current courier has the status = BANNED, which doesn't allow to take any orders",null);
        }
        return new Response(true, "Order taken by courier with username = " + principal.getName() , orderService.takeOrder(id, courierRepository.findByUsername(principal.getName())));
    }
    //tested
    @DeleteMapping(path = "/delete/{id}")
    public Response deleteOrder(Principal principal, @PathVariable Long id){
        if(!orderRepository.existsById(id)){
            return new Response(false, "Such order doensn't exist", null);
        }
        if(orderRepository.belongsTo(id,principal.getName()).equals(1)){
            orderService.deleteOrder(id);
            return new Response(true, "Order with id = " + id + " deleted!!", null);
        }
        else{
            return new Response(false, "Order with id = " + id + " doesn't belong to current user", null);
        }
    }
}
