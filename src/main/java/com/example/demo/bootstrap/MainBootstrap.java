package com.example.demo.bootstrap;

import com.example.demo.enums.Gender;
import com.example.demo.model.User;
import com.example.demo.model.UserRoles;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRolesRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MainBootstrap implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRolesRepository userRolesRepository;

    @Override
    public void run(String... args) throws Exception {
        BCryptPasswordEncoder passwordEncoder = new  BCryptPasswordEncoder();
        User user1 = new User.Builder("admin").withEmail("admin@gmail.com").withPassword(passwordEncoder.encode("123admin")).withAge(20).withGender(Gender.MALE).isActive(1).build();
        User user2 = new User.Builder("user1").withEmail("user@gmail.com").withPassword(passwordEncoder.encode("123user")).withGender(Gender.FEMALE).withAge(18).isActive(1).build();

        userRepository.save(user1);
        userRepository.save(user2);

        UserRoles userRole1 = new UserRoles("ROLE_ADMIN",user1);
        UserRoles userRole2 = new UserRoles("ROLE_USER",user2);

        userRolesRepository.save(userRole1);
        userRolesRepository.save(userRole2);

    }
}
