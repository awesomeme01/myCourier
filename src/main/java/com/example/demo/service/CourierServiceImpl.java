package com.example.demo.service;

import com.example.demo.enums.CourierStatus;
import com.example.demo.model.Courier;
import com.example.demo.repository.CourierRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class CourierServiceImpl implements CourierService {
    @Autowired
    CourierRepository courierRepository;
    @Override
    public Courier createCourier(Courier courier) {
        return courierRepository.save(courier);
    }

    @Override
    public List<Courier> getCouriers(String status) {
        if(status == null) {
            return courierRepository.findAll();
        }
        //    ACTIVE,
        //    VACATION,
        //    BANNED;
        else
            return courierRepository.findAll().stream().filter(x->{

              if (status.equals("ACTIVE") || status.equals("active")){
                  return x.getStatus()== CourierStatus.ACTIVE;
              }
              else if(status.equals("VACATION")|| status.equals("vacation")){
                  return x.getStatus()== CourierStatus.VACATION;
              }
              else if(status.equals("BANNED")|| status.equals("banned")){
                  return x.getStatus()== CourierStatus.BANNED;
              }
              else{return false;}
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
