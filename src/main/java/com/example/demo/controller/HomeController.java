package com.example.demo.controller;

import com.example.demo.Helper.Response;
import com.example.demo.repository.CourierRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(path = "/home")
public class HomeController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    CourierRepository courierRepository;
//    @Secured("ROLE_ADMIN")
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @RequestMapping(value = "/myUser", method = RequestMethod.GET)
    @ResponseBody
    public Response currentUserName(Principal principal) {
        return new Response( true,"Current user information", userRepository.findByUsername(principal.getName()));
    }

    @Secured("ROLE_COURIER")
    @RequestMapping(value = "/myCourier", method = RequestMethod.GET)
    @ResponseBody
    public Response currentCourier(Principal principal){
        return new Response(true, "Current courier information", courierRepository.findByUsername(principal.getName()));
    }
}
