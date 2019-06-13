package com.example.demo.Helper;

import com.example.demo.model.Comment;
import com.example.demo.model.Item;
import com.example.demo.model.Order;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OrderModel {
    private Order order;
    private List<Item> itemList;
    private List<Comment> commentList;

    public OrderModel(){}


    public OrderModel(Order order, List<Item> itemList, List<Comment> commentList) {
        this.order = order;
        this.itemList = itemList;
        this.commentList = commentList;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
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
