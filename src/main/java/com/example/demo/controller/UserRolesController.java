package com.example.demo.controller;

import com.example.demo.model.Response;
import com.example.demo.model.User;
import com.example.demo.model.UserRoles;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserRolesService;
import com.example.demo.service.UserService;
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
    UserService userService;
    //    List<UserRoles> getAllUserRoles();
    //    List<UserRoles> getUserRolesByUser(User user);
    //    UserRoles createUserRole(UserRoles userRoles);
    //    List<User> getUsersByRoles(String role);
    //    void deleteUserRoles(Integer id);

    @GetMapping(path = "/getAll")
    public Response getAllUserRoles(){
        return new Response (true,"All userRoles that were created!",userRolesService.getAllUserRoles());
    }
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
        //Couldn't add new role. Error when searching for user that doesn't exist!!!!
        return new Response(true,"All roles that belong to user with id = " + id,userRolesService.getUserRolesByUser(user));
    }
    @GetMapping(path = "/getAllByRole/{role}")
    public Response getUsersByRole(@PathVariable String role){
        return new Response (true,"All userRoles with role " + role,userRolesService.getUsersByRoles(role));
    }
    @PostMapping(path = "/createForUser/{userId}")
    public Response create(@RequestBody UserRoles userRole, @PathVariable Long userId){

        if(userService.getUserById(userId)!=null) {
            return new Response(true, "new Role created for user with id = " + userId, userRolesService.createUserRole(userRole));
        }
        else
            return new Response(false, "Error: There's no such user",null);
        //Couldn't add new role. Error when searching for user that doesn't exist!!!!
    }
    @DeleteMapping(path = "/delete/{id}")
    public Response deleteUserRole(@PathVariable Integer id){
        userRolesService.deleteUserRoles(id);
        return new Response(true,"UserRole with id = "+id+" has been successfully deleted",null);
    }
}
