package com.tsystems.shop.service.api;


import com.tsystems.shop.model.BagProduct;
import com.tsystems.shop.model.Order;
import com.tsystems.shop.model.Payment;
import com.tsystems.shop.model.User;

import java.util.List;

public interface OrderService {
    Order addNewOrder(String address, String orderStatus, User user,
                      Payment payment, String date, String phone, List<BagProduct>products);
    List<Order> findOrdersByEmail(String email);
    List<String> findOrderStatusTypes();
    List<Order> findOrderByStatusType(String status);
    List<Order> findAllOrders();
    Order findOrderById(String id);
    Order saveOrder(Order order);
    List<Order> findDoneOrders();
    List<Order> findActiveOrders();
}
