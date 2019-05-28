package com.example.demo.controller;

import com.example.demo.enums.Status;
import com.example.demo.model.Courier;
import com.example.demo.model.OrderModel;
import com.example.demo.model.Response;
import com.example.demo.service.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(path = "/couriers")
public class CourierController {
    @Autowired
    private CourierService courierService;
    @GetMapping(path = "/getAll/{status}")
    public Response getAvailable(@PathVariable String status){
        //    ACTIVE,
        //    VACATION,
        //    BANNED;
        return new Response(true, "All courier with status = " + status, courierService.getCouriers(status));
    }
    @GetMapping(path = "/getById/{id}")
    public Response getCourierById(@PathVariable Long id){
        return new Response(true, "Courier with id = " + id, courierService.getCourierById(id));
    }
    @PostMapping(path = "/create")
    public Response createCourier(@RequestBody Courier courier){
        return new Response(true, "Successfully create a new Courier", courierService.createCourier(courier));
    }
    @DeleteMapping(path = "/delete/{id}")
    public Response deleteCourier(@PathVariable Long id){
        courierService.deleteCourier(id);
        return new Response(true, "Courier with id = " + id + " has been deleted", null);
    }
}
