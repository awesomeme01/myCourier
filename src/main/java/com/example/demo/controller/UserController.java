package com.example.demo.controller;

import com.example.demo.model.Response;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping(path = "/getAll")
    public Response getAll(){
        return new Response(true, "All users that were created!",userService.getAllUsers());
    }
    @GetMapping(path = "/getById/{id}")
    public Response getById(@PathVariable Long id){
        return new Response(true,"User with id = " + id ,userService.getUserById(id));
    }
    @PostMapping(path = "/create")
    public Response create(User user){
        return new Response(true,"User created successfully!",userService.createUser(user));
    }
    @DeleteMapping(path = "/delete/{id}")
    public Response delete(@PathVariable Long id){
        userService.deleteUser(id);
        return new Response(true,"User with id = " + id + " has been deleted",null);
    }
}
