package com.example.demo.service;


import com.example.demo.model.Market;
import com.example.demo.repository.MarketRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MarketServiceImpl implements MarketService{
    @Autowired
    MarketRepository marketRepository;

    @Override
    public Market getMarketByName(String name) {
        return marketRepository.findByName(name);
    }

    @Override
    public Market createMarket(Market market) {
        return marketRepository.save(market);
    }

    @Override
    public Market getMarketById(Long id) {
        return marketRepository.findById(id).get();
    }

    @Override
    public List<Market> getAllMarkets() {
        return marketRepository.findAll();
    }

    @Override
    public void deleteMarket(Long id) {
        marketRepository.deleteById(id);
    }
}
