package com.example.demo.service;

import com.example.demo.enums.CourierStatus;
import com.example.demo.model.Courier;

import java.util.List;

public interface CourierService {
    Courier createCourier(Courier courier);
    Courier updateCourierStatus(Long id, CourierStatus courierStatus);
    List<Courier> getCouriers(String status);
    void deleteCourier(Long id);
    Courier getCourierById(Long id);
}
