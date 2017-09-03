package com.tsystems.shop.service.impl;

import com.tsystems.shop.dao.api.OrderDao;
import com.tsystems.shop.model.*;
import com.tsystems.shop.model.dto.BagProductDto;
import com.tsystems.shop.model.enums.OrderStatusEnum;
import com.tsystems.shop.service.api.BagService;
import com.tsystems.shop.service.api.OrderService;
import com.tsystems.shop.service.api.ProductService;
import com.tsystems.shop.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductService productService;

    @Autowired
    private BagService bagService;

    @Override
    public Order addNewOrder(String address, String orderStatus, User user,
                             Payment payment, String date, String phone, List<BagProductDto> products) {
        Order order = new Order(address, orderStatus, user, payment, date, phone);
        for (BagProductDto product : products) {
            Product p = bagService.findProductByBagProduct(product);
            Size size = productService.findSizeById(product.getSizeId());
            OrdersProducts ordersProducts = new OrdersProducts(order, p, String.valueOf(product.getAmount()), size);
            order.getProducts().add(ordersProducts);
        }
        orderDao.saveOrder(order);
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

    @Override
    public long findIncomePerLastWeek() {
        long amount = 0;
        List<Order> orders = findOrdersForTheLastWeek();
        for (Order order : orders) {
            amount += Long.parseLong(order.getPayment().getTotalPrice());
        }
        return amount;
    }

    @Override
    public long findIncomePerLastMonth() {
        long amount = 0;
        List<Order> orders = findOrdersForTheLastMonth();
        for (Order order : orders) {
            amount += Long.parseLong(order.getPayment().getTotalPrice());
        }
        return amount;
    }

    @Override
    public List<Order> findDoneOrders() {
        return orderDao.findDoneOrders();
    }

    @Override
    public List<Order> findActiveOrders() {
        return orderDao.findActiveOrders();
    }

    @Override
    public List<Order> findOrdersForTheLastWeek() {
        return findOrdersForTheLastNDays(8);
    }

    @Override
    public List<Order> findOrdersForTheLastMonth() {
        LocalDate now = DateUtil.getLocalDateNow();

        List<Order> orders = orderDao.findDoneOrders();
        List<Order> lastWeekOrders = new ArrayList<>();
        LocalDate orderDate;
        for (Order order : orders) {
            orderDate = LocalDate.parse(order.getDate(), DateUtil.getDtf());
            if (now.minusMonths(1).minusDays(1).isBefore(orderDate)) {
                lastWeekOrders.add(order);
            }
        }
        return lastWeekOrders;
    }

    @Override
    public List<Order> findOrdersForTheLastNDays(int n) {
        LocalDate now = DateUtil.getLocalDateNow();

        List<Order> orders = orderDao.findDoneOrders();
        List<Order> lastWeekOrders = new ArrayList<>();
        LocalDate orderDate;
        for (Order order : orders) {
            orderDate = LocalDate.parse(order.getDate(), DateUtil.getDtf());
            if (now.minusDays(n).isBefore(orderDate)) {
                lastWeekOrders.add(order);
            }
        }
        return lastWeekOrders;
    }
}
