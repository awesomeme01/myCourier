package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.model.UserRoles;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "/admin/userRoles")
public class UserRolesController {
    @Autowired
    UserRolesService userRolesService;
    @Autowired
    UserRepository userRepository;
    //    List<UserRoles> getAllUserRoles();
    //    List<UserRoles> getUserRolesByUser(User user);
    //    UserRoles createUserRole(UserRoles userRoles);
    //    List<User> getUsersByRoles(String role);
    //    void deleteUserRoles(Integer id);

    @GetMapping(path = "/getAll")
    public List<UserRoles> getAllUserRoles(){
        return userRolesService.getAllUserRoles();
    }
    @GetMapping(path = "/getAllbyUserId/{id}")
    public List<UserRoles> getUserRolesByUser(@PathVariable Long id){
        User user;
        try{
            user = userRepository.findById(id).get();
        }
        catch (NoSuchElementException e){
            e.printStackTrace();
            return null;
        }
        return userRolesService.getUserRolesByUser(user);
    }
    @GetMapping(path = "/getAllByRole/{role}")
    public List<User> getUsersByRole(@PathVariable String role){
        return userRolesService.getUsersByRoles(role);
    }
    @PostMapping(path = "/createForUser/{userId}")
    public UserRoles create(@RequestBody UserRoles userRole, @PathVariable Long userId){
        User user;
        try{
            user = userRepository.findById(userId).get();
        }
        catch (NoSuchElementException e){
            e.printStackTrace();
            return null;
        }
        userRole.setUser(user);
        return userRolesService.createUserRole(userRole);
    }
    @DeleteMapping(path = "/delete/{id}")
    public void deleteUserRole(@PathVariable Integer id){
        userRolesService.deleteUserRoles(id);
    }
}
