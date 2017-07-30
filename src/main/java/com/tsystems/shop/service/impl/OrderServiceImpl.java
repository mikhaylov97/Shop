package com.tsystems.shop.service.impl;

import com.tsystems.shop.dao.api.OrderDao;
import com.tsystems.shop.model.Order;
import com.tsystems.shop.model.Payment;
import com.tsystems.shop.model.Product;
import com.tsystems.shop.model.User;
import com.tsystems.shop.model.enums.OrderStatusEnum;
import com.tsystems.shop.service.api.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public Order addNewOrder(String shippingMethod, User user, Payment payment, Set<Product> products) {
        Order order = new Order(shippingMethod, OrderStatusEnum.AWAITING_PAYMENT.name(), user, payment, products);
        orderDao.addNewOrder(order);
        return order;
    }

    @Override
    public List<Order> findOrdersByEmail(String email) {
        return orderDao.findOrdersByEmail(email);
    }

    @Override
    public List<String> findOrderStatusTypes() {
        List<String> types = new ArrayList<>();
        types.add(OrderStatusEnum.AWAITING_PAYMENT.name());
        types.add(OrderStatusEnum.AWAITING_SHIPMENT.name());
        types.add(OrderStatusEnum.SHIPPED.name());
        types.add(OrderStatusEnum.DELIVERED.name());

        return types;
    }

    @Override
    public List<Order> findOrderByStatusType(String status) {
        return orderDao.findOrdersByStatusType(status);
    }

    @Override
    public List<Order> findAllOrders() {
        return orderDao.findAllOrders();
    }

    @Override
    public Order findOrderById(String id) {
        return orderDao.findOrderById(Long.parseLong(id));
    }

    @Override
    public Order saveOrder(Order order) {
        return orderDao.saveOrder(order);
    }
}
