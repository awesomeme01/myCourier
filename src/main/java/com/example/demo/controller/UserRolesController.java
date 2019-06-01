package com.example.demo.controller;

import com.example.demo.model.Response;
import com.example.demo.model.User;
import com.example.demo.model.UserRoles;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserRolesService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Secured("ROLE_ADMIN")
@RequestMapping(path = "/admin/userRoles")
public class UserRolesController {
    @Autowired
    UserRolesService userRolesService;
    @Autowired
    UserService userService;
    //    List<UserRoles> getAllUserRoles();
    //    List<UserRoles> getUserRolesByUser(User user);
    //    UserRoles createUserRole(UserRoles userRoles);
    //    List<User> getUsersByRoles(String role);
    //    void deleteUserRoles(Integer id);

    //tested
    @GetMapping(path = "/getAll")
    public Response getAllUserRoles(){
        return new Response (true,"All userRoles that were created!",userRolesService.getAllUserRoles());
    }
    //tested
    @GetMapping(path = "/getAllByUserId/{id}")
    public Response getUserRolesByUser(@PathVariable Long id){
        User user;
        try{
            user = userService.getUserById(id);
        }
        catch (NoSuchElementException e){
            e.printStackTrace();
            return new Response(false,"Error: There is no such user with id = " + id,null );
        }
        return new Response(true,"All roles that belong to user with id = " + id,userRolesService.getUserRolesByUser(user));
    }
    //tested
    @GetMapping(path = "/getAllByRole/{role}")
    public Response getUsersByRole(@PathVariable String role){
        return new Response (true, "All userRoles with role " + role,userRolesService.getUsersByRoles(role));
    }
    //tested
    @PostMapping(path = "/createForUser/{userId}")
    public Response create(@RequestBody UserRoles userRole, @PathVariable Long userId){
        User user = userService.getUserById(userId);
        if(user!=null) {
            userRole.setUser(user);
            return new Response(true, "new Role created for user with id = " + userId, userRolesService.createUserRole(userRole));
        }
        else
            return new Response(false, "Error: There's no such user",null);
    }
    //tested
    @DeleteMapping(path = "/delete/{id}")
    public Response deleteUserRole(@PathVariable Integer id){
        try {
            userRolesService.deleteUserRoles(id);
            return new Response(true, "UserRole with id = " + id + " has been successfully deleted", null);
        }
        catch (EmptyResultDataAccessException exception){
            exception.printStackTrace();
            return new Response(false, "User not found!",exception.getMessage());
        }
    }
}
