package com.tsystems.shop.service.api;


import com.tsystems.shop.model.Order;
import com.tsystems.shop.model.Payment;
import com.tsystems.shop.model.Product;
import com.tsystems.shop.model.User;

import java.util.List;
import java.util.Set;

public interface OrderService {
    Order addNewOrder(String shippingMethod, User user, Payment payment, Set<Product> products);
    List<Order> findOrdersByEmail(String email);
    List<String> findOrderStatusTypes();
    List<Order> findOrderByStatusType(String status);
    List<Order> findAllOrders();
    Order findOrderById(String id);
    Order saveOrder(Order order);
}
