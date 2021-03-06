package com.example.demo.bootstrap;

import com.example.demo.Helper.ItemWrapper;
import com.example.demo.enums.CourierStatus;
import com.example.demo.enums.Gender;
import com.example.demo.model.*;
import com.example.demo.repository.CourierRepository;
import com.example.demo.repository.MarketRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRolesRepository;
import com.example.demo.service.CommentService;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MainBootstrap implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRolesRepository userRolesRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    CourierRepository courierRepository;
    @Autowired
    MarketRepository marketRepository;
    @Autowired
    CommentService commentService;
    @Override
    public void run(String... args) throws Exception {
        BCryptPasswordEncoder passwordEncoder = new  BCryptPasswordEncoder();
        User admin = new User.Builder("admin").withPhoneNumber((long)550523209).withEmail("admin@gmail.com").withPassword(passwordEncoder.encode("123admin")).withAge(20).withGender(Gender.MALE).isActive(1).build();
        User user1 = new User.Builder("user1").withPhoneNumber((long)770324353).withEmail("user@gmail.com").withPassword(passwordEncoder.encode("123user")).withGender(Gender.FEMALE).withAge(18).isActive(1).build();
        User user2 = new User.Builder("user2").withPhoneNumber((long)770321314).withEmail("user2@gmail.com").withPassword(passwordEncoder.encode("123user2")).withGender(Gender.MALE).withAge(18).isActive(1).build();
        userRepository.save(admin);
        userRepository.save(user1);
        userRepository.save(user2);

//        UserRoles userRole3 = new UserRoles("ROLE_COURIER",user1);
        UserRoles userRole1 = new UserRoles("ROLE_ADMIN",admin);
        UserRoles userRole2 = new UserRoles("ROLE_USER",user1);
        UserRoles userRole3 = new UserRoles("ROLE_USER",user2);
        UserRoles userRole4 = new UserRoles("ROLE_COURIER",user2);
        userRolesRepository.save(userRole1);
        userRolesRepository.save(userRole2);
        userRolesRepository.save(userRole3);
        userRolesRepository.save(userRole4);
        Courier courier = new Courier();
//        courier.setFeedbackCount();
        //String name, String description, Double maxPrice, Double minPrice
        courier.setUser(user2);
        courier.setFeedbackCount(0);
        courier.setPin((long)121314);
        courier.setStatus(CourierStatus.ACTIVE);
        courierRepository.save(courier);
//        courier.set

        List<Item> itemList = new ArrayList<>();
        itemList.add(new Item("Apple","1kilo",100.0,50.0));
        itemList.add(new Item("Orange","1kilo",200.0,50.0));
        itemList.add(new Item("Carrot","2kilo",100.0,50.0));
        itemList.add(new Item("Milk","1L",100.0,50.0));
        itemList.add(new Item("Juice","1L",100.0,50.0));
        itemList.add(new Item("Burger","2pcs",300.0,150.0));
        //(User orderedBy, Courier courier, Double moneyAmount, String market)
        Market market = new Market("GLOBUS");
        marketRepository.save(market);
        Order order = new Order(user1,courier,900.0,market);
        Order order2 = new Order(user2,courier,1000.0,market);


        orderService.createOrder(order);
        orderService.createOrder(order2);
        orderService.addNewItemsToOrder(new ItemWrapper(itemList),order.getId());
        orderService.addNewItemsToOrder(new ItemWrapper(itemList),order2.getId());
        //(String commentText, int feedbackValue, Order order, User createdBy)
        commentService.createComment(new Comment("This is one comment excellent", 1600,order,user1));
    }
}
