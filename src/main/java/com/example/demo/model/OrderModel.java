package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OrderModel {
    private Order order;
    private List<Item> itemList;

    public OrderModel(){}

    public OrderModel(Order order, List<Item> itemList) {
        this.order = order;
        this.itemList = itemList;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }
}
