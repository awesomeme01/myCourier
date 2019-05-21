package com.example.demo.service;

import com.example.demo.model.Courier;

import java.util.List;

public interface CourierService {
    Courier createCourier(Courier courier);
    List<Courier> getCouriers(String status);
    void deleteCourier(Long id);
}
