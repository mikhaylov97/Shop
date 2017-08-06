package com.tsystems.shop.service.impl;

import com.tsystems.shop.dao.api.OrderDao;
import com.tsystems.shop.model.*;
import com.tsystems.shop.model.enums.OrderStatusEnum;
import com.tsystems.shop.service.api.BagService;
import com.tsystems.shop.service.api.OrderService;
import com.tsystems.shop.service.api.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Order addNewOrder(String shippingMethod, User user, Payment payment, List<BagProduct> products) {
        Order order = new Order("address", shippingMethod, OrderStatusEnum.AWAITING_PAYMENT.name(), user, payment);
        for (BagProduct product : products) {
            Product p = bagService.findProductByBagProduct(product);
//            for (Long sizeId : product.getSizes()) {
//                Size size = productService.findSizeById(sizeId);
//                OrdersProducts ordersProducts = new OrdersProducts(order, p, String.valueOf(product.getAmount()), size);
//                order.getProducts().add(ordersProducts);
//                //orderDao.savePartOfOrder(ordersProducts);
//            }
        }
        orderDao.saveOrder(order);
        //Order order = new Order(shippingMethod, OrderStatusEnum.AWAITING_PAYMENT.name(), user, payment, products);
//        orderDao.addNewOrder(order);
//        return order;
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
