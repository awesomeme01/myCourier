package com.example.demo.controller;

import com.example.demo.Helper.Response;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Secured("ROLE_ADMIN")
@RequestMapping("/admin/users")
public class UserController {
    @Autowired
    UserService userService;
    //tested
    @GetMapping(path = "/getAll")
    public Response getAll(){
        return new Response(true, "All users that were created!",userService.getAllUsers());
    }
    //tested
    @GetMapping(path = "/getById/{id}")
    public Response getById(@PathVariable Long id){
        try{
            if(id<=0){
                return new Response(false, "id has to be greater than zero", null);
            }
            return new Response(true,"User with id = " + id ,userService.getUserById(id));
        }
        catch (NoSuchElementException exception){
            exception.printStackTrace();
            return new Response(false, "User not found!", exception.getMessage());
        }
    }
    //tested - couldn't catch PSQLException, why can't i find it
    @PostMapping(path = "/create")
    public Response create(@RequestBody User user){
//        try{
//
//        }
//        catch (SQLException ex)
        return new Response(true,"User created successfully!",userService.createUser(user));
    }
    //tested
    @DeleteMapping(path = "/delete/{id}")
    public Response delete(@PathVariable Long id){
        try {
            userService.deleteUser(id);
            return new Response(true, "User with id = " + id + " has been deleted", null);
        }
        catch (EmptyResultDataAccessException ex){
            ex.printStackTrace();
            return new Response(false, "User not found!", ex.getMessage());
        }
    }
}
