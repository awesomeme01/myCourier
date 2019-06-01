package com.example.demo.controller;

import com.example.demo.enums.CourierStatus;
import com.example.demo.model.Courier;
import com.example.demo.model.Response;
import com.example.demo.model.StatusWrapper;
import com.example.demo.repository.CourierRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Secured("ROLE_ADMIN")
@RequestMapping(path = "admin/couriers")
public class CourierController {
    @Autowired
    private CourierService courierService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourierRepository courierRepository;
    @GetMapping(path = "/getAll")
    public Response getAll(){
        return new Response(true,"All couriers", courierRepository.findAll());
    }
    @GetMapping(path = "/getAll/{status}")
    public Response getByStatus(@PathVariable String status){
        //    ACTIVE,
        //    VACATION,
        //    BANNED;
        return new Response(true, "All courier with status = " + status, courierService.getCouriers(status));
    }
    @GetMapping(path = "/getById/{id}")
    public Response getCourierById(@PathVariable Long id){
        if(courierRepository.existsById(id)){
            return new Response(true, "Courier with id = " + id, courierService.getCourierById(id));
        }
        return new Response(false, "Courier with id = " + id + " doesn't exist!", null);
    }
    @PostMapping(path = "/createForUser/{userId}")
    public Response createCourier(@RequestBody Courier courier,@PathVariable Long userId) {
        if(userRepository.existsById(userId)){
            courier.setUser(userRepository.findById(userId).get());
        }
        if(String.valueOf(courier.getPin()).length() == 14){
            if(String.valueOf(courier.getId()).startsWith("2")||String.valueOf(courier.getId()).startsWith("1")){
                return new Response(true, "Successfully create a new Courier", courierService.createCourier(courier));
            }
        }
        return new Response(false, "Courier creation failed: invalid PIN", null);
    }
    @PutMapping(path = "/updateStatus/{id}")
    public Response updateCourierStatus(@RequestBody StatusWrapper statusWrapper, @PathVariable Long id){
        try {
            if(!courierRepository.existsById(id)){
                return new Response(false, "Status update failed. There is no such user with id = " + id, null);
            }
            return new Response(true, "Successfully updated status of courier with id = " + id + " to status = " + statusWrapper.getStatus(), courierService.updateCourierStatus(id, CourierStatus.valueOf(statusWrapper.getStatus().toUpperCase())));
        }
        catch (IllegalArgumentException ex){
            return new Response(false, "Status update failed. Check if the spell is correct", null);
        }
    }
    @DeleteMapping(path = "/delete/{id}")
    public Response deleteCourier(@PathVariable Long id){
        courierService.deleteCourier(id);
        return new Response(true, "Courier with id = " + id + " has been deleted", null);
    }
}
