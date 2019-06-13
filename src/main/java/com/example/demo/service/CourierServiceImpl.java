package com.example.demo.service;

import com.example.demo.enums.CourierStatus;
import com.example.demo.model.Courier;
import com.example.demo.repository.CourierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class CourierServiceImpl implements CourierService {
    @Autowired
    CourierRepository courierRepository;
    public Courier updateCourierStatus(Courier courier, CourierStatus courierStatus){
        courier.setStatus(courierStatus);
        courierRepository.save(courier);
        return courier;
    }

    @Override
    public Courier createCourier(Courier courier) {
        courier.setStatus(CourierStatus.ACTIVE);
        courier.setRating(1000);
        courier.setFeedbackCount(0);
        return courierRepository.save(courier);
    }

    @Override
    public Courier updateCourierStatus(Long id, CourierStatus courierStatus) {
        if(courierRepository.existsById(id)){
            Courier courier = courierRepository.findById(id).get();
            courier.setStatus(courierStatus);
            courierRepository.save(courier);
            return courier;
        }
        return null;
    }

    @Override
    public List<Courier> getAll() {
        return courierRepository.findAll();
    }

    @Override
    public List<Courier> getCouriersByStatus(String status) {
        if(status==null){
            return null;
        }
        return courierRepository.findAll().stream().filter(x->{
            if(x.getStatus()==null){
                return false;
            }
            else if(x.getStatus().name().equals(status.toUpperCase())){
                return true;
            }
            else return false;
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteCourier(Long id) {
        courierRepository.deleteById(id);
    }

    @Override
    public Courier getCourierById(Long id) {
        return courierRepository.findById(id).get();
    }
}
