package com.example.demo.model;

import com.example.demo.enums.CourierStatus;

import javax.persistence.*;

@Entity
@Table(name = "authorized_couriers")
public class Courier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private CourierStatus status;
    private int rating;


}
