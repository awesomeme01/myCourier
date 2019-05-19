package com.example.demo.controller;

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
    public List<User> getAll(){
        return userService.getAllUsers();
    }
    @GetMapping(path = "/getById/{id}")
    public User getById(@PathVariable Long id){
        return userService.getUserById(id);
    }
    @PostMapping(path = "/create")
    public User create(User user){
        return userService.createUser(user);
    }
    @DeleteMapping(path = "/delete/{id}")
    public void delete(@PathVariable Long id){
        userService.deleteUser(id);
    }
}
