package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.model.UserRoles;
import com.example.demo.repository.UserRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class UserRolesServiceImpl implements UserRolesService{
    @Autowired
    UserRolesRepository userRolesRepository;
    @Override
    public UserRoles createUserRole(UserRoles userRoles) {
        return userRolesRepository.save(userRoles);
    }

    @Override
    public List<UserRoles> getAllUserRoles() {
        return userRolesRepository.findAll();
    }

    @Override
    public List<UserRoles> getUserRolesByUser(User user) {
        return userRolesRepository.findAll().stream().filter(x->x.getUser().equals(user)).collect(Collectors.toList());
    }

    @Override
    public List<User> getUsersByRoles(String role) {
        return null;//unfinished
    }

    @Override
    public void deleteUserRoles(Integer id) {
        userRolesRepository.deleteById(id);
    }
}
