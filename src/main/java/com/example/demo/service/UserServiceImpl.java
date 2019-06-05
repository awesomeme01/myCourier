package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.model.UserRoles;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRolesService userRolesService;
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User createUser(User user) {
        BCryptPasswordEncoder passwordEncoder = new  BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        userRolesService.createUserRole(new UserRoles("ROLE_USER",user));
        return user;
    }

    @Override
    public void deleteUser(Long id) {
        for (UserRoles userRoles :userRolesService.getUserRolesByUser(userRepository.findById(id).get())) {
            userRolesService.deleteUserRoles(userRoles.getId());
        }
        userRepository.deleteById(id);
    }
}
