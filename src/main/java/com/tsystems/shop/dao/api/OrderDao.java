package com.tsystems.shop.dao.api;


import com.tsystems.shop.model.Order;

import java.util.List;

public interface OrderDao {
    void addNewOrder(Order order);
    List<Order> findOrdersByEmail(String email);
}
