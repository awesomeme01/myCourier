package com.example.demo.service;

import com.example.demo.model.Market;

import java.util.List;

public interface MarketService {
    Market createMarket(Market market);
    Market getMarketById(Long id);
    Market getMarketByName(String name);
    List<Market> getAllMarkets();
    void deleteMarket(Long id);

}
