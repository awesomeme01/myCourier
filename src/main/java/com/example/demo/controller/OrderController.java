package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.repository.CourierRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserRolesService;
import com.example.demo.service.UserService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(path = "/api/orders")
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    UserRolesService userRolesService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CourierRepository courierRepository;
    @Autowired
    UserService userService;
    //-------------------------------
    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public User currentUserName(Principal principal) {
        return userRepository.findByUsername(principal.getName());
    }
    //-------------------------------
    @Secured("ROLE_ADMIN")
    @GetMapping(path = "/getAll")
    public Response getAll(){
        return new Response(true, "All orders", orderService.getAllOrders());
    }
    @Secured("ROLE_ADMIN")
    @GetMapping(path = "/getById/{id}")
    public Response getById(@PathVariable Long id){
        return new Response(true,"Order with id = " + id, orderService.getOrderById(id));
    }
    @Secured("ROLE_USER")
    @PostMapping(path = "/create")
    public Response createOrder(Principal principal, @RequestBody Order order){
        order.setOrderedBy(userRepository.findByUsername(principal.getName()));
        return new Response(true, "Order created successfully", orderService.createOrder(order));
    }
    @PutMapping(path = "/addItemToOrder/{orderId}")
    public Response addItemToOrder(@PathVariable Long orderId, ItemWrapper itemWrapper){
        return new Response(true, "New items were added to order", orderService.addNewItemsToOrder(itemWrapper,orderId));
    }
    //tested - need's one more test
    @Secured("ROLE_COURIER")
    @PutMapping(path = "/takeOrder/{id}")
    public Response takeOrder(Principal principal, @PathVariable Long id){
        return new Response(true, "Order taken by courier with username = " + principal.getName() , orderService.takeOrder(id, courierRepository.findByUsername(principal.getName())));
    }
    @PostAuthorize("#id == returnObject")
    @DeleteMapping(path = "/delete/{id}")
    public Response deleteOrder(@PathVariable Long id){
        orderService.deleteOrder(id);
        return new Response(true, "Order with id = " + id + " deleted!!", null);
    }
}
