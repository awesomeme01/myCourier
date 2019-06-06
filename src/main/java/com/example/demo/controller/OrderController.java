package com.example.demo.controller;

import com.example.demo.enums.CourierStatus;
import com.example.demo.enums.Status;
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
    @Secured("ROLE_USER")
    @GetMapping(path = "/getMyOrderHistory")
    public Response getMyOrderHistory(Principal principal){
        return null;
    }
    @Secured("ROLE_USER")
    @GetMapping(path = "getMyOrderById/{id}")
    public Response getMyOrderById(Principal principal, @PathVariable Long id){
        return null;
    }
    //tested - needs one more final test
    @Secured("ROLE_USER")
    @PostMapping(path = "/create")
    public Response createOrder(Principal principal, @RequestBody Order order){
        order.setOrderedBy(userRepository.findByUsername(principal.getName()));
        return new Response(true, "Order created successfully", orderService.createOrder(order));
    }
    //tested - needs one more final test
    @Secured("ROLE_USER")
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
    @Secured("ROLE_COURIER")
    @GetMapping(path = "/getOpenOrders")
    public Response getOpenOrders(){
        return new Response(true, "Orders that are open now", orderService.getOpenOrders());
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
        if(orderRepository.findById(id).get().getStatus().equals(Status.CLOSED) || orderRepository.findById(id).get().getStatus().equals(Status.CLOSED)){
            return new Response(false,"This order is already being delivered by another courier", null);
        }
        return new Response(true, "Order taken by courier with username = " + principal.getName() , orderService.takeOrder(id, courierRepository.findByUsername(principal.getName())));
    }
    //tested
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @DeleteMapping(path = "/delete/{id}")
    public Response deleteOrder(Principal principal, @PathVariable Long id){
        if(!orderRepository.existsById(id)){
            return new Response(false, "Such order doesn't exist", null);
        }
        if(orderRepository.belongsTo(id,principal.getName()).equals(1)){
            orderService.deleteOrder(id);
            return new Response(true, "Order with id = " + id + " deleted!!", null);
        }
        else{
            return new Response(false, "Order with id = " + id + " doesn't belong to current user", null);
        }
    }
    @Secured("ROLE_USER")
    @PutMapping(path = "/cancelOrder/{id}")
    public Response cancelOrder(Principal principal, @PathVariable Long id){
        User user = userRepository.findByUsername(principal.getName());
        if(!orderRepository.existsById(id)){
            return new Response(false, "There is no such order with id = " + id, null);
        }
        if(orderRepository.belongsTo(id,user.getUsername())==1){
            return new Response(true, "Order has been canceled by User!", orderService.cancelOrder(id));
        }
        return new Response(false, "Order with id = " + id + " doesn't belong to the current User", null);
    }
    @Secured("ROLE_COURIER")
    @PutMapping(path = "/declineOrder/{id}")
    public Response declineOrder(Principal principal, @PathVariable Long id){
        if(orderRepository.existsById(id)){
           if(orderRepository.findById(id).get().getCourier().equals(courierRepository.findByUsername(principal.getName()))){
               return new Response(true, "Order has been declined by the courier, this order will be returned to the main order-Stack, and the status will be changed to Status.OPEN", orderService.declineOrder(id));
           }
           return new Response(false, "Error: This order is taken by another Courier", null);
        }
        return new Response(false, "There is no such Order with id = " + id, null );
    }
    @Secured("ROLE_COURIER")
    @PutMapping(path = "/finishOrder/{id}")
    public Response finishOrder(Principal principal, @PathVariable Long id){
        if(orderRepository.existsById(id)){
            if(orderRepository.findById(id).get().getCourier().equals(courierRepository.findByUsername(principal.getName()))){
                return new Response(true, "Order completed by Courier. Awaiting confirmation from User", orderService.finishOrder(id));
            }
            else if(orderRepository.findById(id).get() == null){
                return new Response(false, "Error: This order has no active courier at the moment", null);
            }
            return new Response(false, "Error: This order is taken by another Courier", null);
        }
        return new Response(false, "There is no such Order with id = " + id, null);
    }
    //unfinished
    @Secured("ROLE_USER")
    @PutMapping(path = "/confirmOrder/{id}")
    public Response confirmOrder(Principal principal, @PathVariable Long id){
        if(orderRepository.belongsTo(id,principal.getName())==1){
            return new Response(true, "Order has been confirmed, money will be transferred to te Courier. This process cannot be reversed", orderService.confirmOrder(id));
        }
        return new Response(false, "Error: Order confirmation failed. This order probably doesn't belong to current user", null);
    }
}
