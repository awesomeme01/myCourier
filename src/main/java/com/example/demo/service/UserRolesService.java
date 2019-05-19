package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.model.UserRoles;

import java.util.List;

public interface UserRolesService {
    List<UserRoles> getAllUserRoles();
    List<UserRoles> getUserRolesByUser(User user);
    UserRoles createUserRole(UserRoles userRoles);
    List<User> getUsersByRoles(String role);
    void deleteUserRoles(Integer id);
}
