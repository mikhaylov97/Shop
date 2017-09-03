package com.tsystems.shop.service.api;


import com.tsystems.shop.model.dto.BagProductDto;
import com.tsystems.shop.model.Order;
import com.tsystems.shop.model.Payment;
import com.tsystems.shop.model.User;

import java.util.List;

public interface OrderService {
    Order addNewOrder(String address, String orderStatus, User user,
                      Payment payment, String date, String phone, List<BagProductDto>products);
    List<Order> findOrdersByEmail(String email);
    List<String> findOrderStatusTypes();
    List<Order> findOrderByStatusType(String status);
    List<Order> findAllOrders();
    Order findOrderById(String id);
    Order saveOrder(Order order);
    long findIncomePerLastWeek();
    long findIncomePerLastMonth();
    List<Order> findDoneOrders();
    List<Order> findActiveOrders();
    List<Order> findOrdersForTheLastWeek();
    List<Order> findOrdersForTheLastMonth();
    List<Order> findOrdersForTheLastNDays(int n);
}
