package com.example.demo.service;

import com.example.demo.enums.CourierStatus;
import com.example.demo.model.Courier;
import com.example.demo.repository.CourierRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface CourierService {
    List<Courier> getAll();
    Courier createCourier(Courier courier);
    Courier updateCourierStatus(Long id, CourierStatus courierStatus);
    List<Courier> getCouriersByStatus(String status);
    Courier updateCourierStatus(Courier courier, CourierStatus courierStatus);
    void deleteCourier(Long id);
    Courier getCourierById(Long id);

}
