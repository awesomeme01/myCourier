package com.example.demo.controller;

import com.example.demo.Helper.Response;
import com.example.demo.model.Market;
import com.example.demo.service.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;


@RestController
@Secured("ROLE_ADMIN")
@RequestMapping("/admin/market")
public class MarketController {
    @Autowired
    MarketService marketService;
    @GetMapping(path = "/getAll")
    public Response getAllMarkets(){
        return new Response(true, "All markets", marketService.getAllMarkets());
    }
    @GetMapping(path = "/get/{id}")
    public Response getById(@PathVariable Long id){
        return new Response(true, "Market with id = " + id, marketService.getMarketById(id));
    }
    @GetMapping(path = "/getByName/{name}")
    public Response getByName(@PathVariable String name){
        return new Response(true, "Market with name = " + name, marketService.getMarketByName(name));
    }
    @PostMapping(path = "/create")
    public Response create(@RequestBody Market market){
        return new Response(true, "New market created!", marketService.createMarket(market));
    }
    @DeleteMapping(path = "/delete/{id}")
    public Response delete(@PathVariable Long id){
        marketService.deleteMarket(id);
        return new Response(true, "Market with id = " + id + " has been deleted!", null);
    }
}
