package com.tsystems.shop.service.impl;

import com.tsystems.shop.dao.api.OrderDao;
import com.tsystems.shop.model.*;
import com.tsystems.shop.model.dto.BagProductDto;
import com.tsystems.shop.model.enums.OrderStatusEnum;
import com.tsystems.shop.model.enums.PaymentStatusEnum;
import com.tsystems.shop.model.enums.PaymentTypeEnum;
import com.tsystems.shop.service.api.BagService;
import com.tsystems.shop.service.api.OrderService;
import com.tsystems.shop.service.api.ProductService;
import com.tsystems.shop.service.api.UserService;
import com.tsystems.shop.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * Order service. It is used to order manipulations.
 */
@Service
public class OrderServiceImpl implements OrderService {

    /**
     * Injected by spring orderDao bean.
     */
    private final OrderDao orderDao;

    /**
     * Injected by spring productService bean.
     */
    private final ProductService productService;

    /**
     * Injected by spring bagService bean.
     */
    private final BagService bagService;

    /**
     * Injected by spring userService bean.
     */
    private final UserService userService;

    /**
     * Injecting constructor.
     * @param orderDao that must be injected.
     * @param productService that must be injected.
     * @param bagService that must be injected.
     * @param userService that must be injected.
     */
    @Autowired
    public OrderServiceImpl(OrderDao orderDao, ProductService productService,
                            BagService bagService, UserService userService) {
        this.orderDao = orderDao;
        this.productService = productService;
        this.bagService = bagService;
        this.userService = userService;
    }

    /**
     * Method saves new orders with necessary parameters.
     *
     * @param shippingCost - order shipping cost
     * @param country      - delivery country name
     * @param city         - delivery city name
     * @param street       - delivery street name
     * @param postcode     - delivery postcode number
     * @param house        - delivery house number
     * @param apartment    - delivery apartment number
     * @param phone        - customer phone number.
     * @param bag          - list with bag products.
     * @param creditCard   - tells is it credit card payment type.
     * @return reference to a saved object.
     */
    @Override
    public Order saveOrder(String shippingCost, String country, String city,
                           String street, String postcode, String house,
                           String apartment, String phone, List<BagProductDto> bag,
                           boolean creditCard) {
        String totalCost = String.valueOf(Long.parseLong(bagService.calculateTotalPrice(bag)) + Long.parseLong(shippingCost));
        String date = DateUtil.getLocalDateNowInDtfFormat();
        String address = country + ", " + city + " (" + postcode + "), " + street + " " + house + ", " + apartment;

        Payment payment;
        if (creditCard) {
            payment = new Payment(PaymentTypeEnum.CREDIT.name(), totalCost,
                    shippingCost, PaymentStatusEnum.PAID.name());
        } else {
            payment = new Payment(PaymentTypeEnum.CASH.name(), totalCost,
                    shippingCost, PaymentStatusEnum.AWAITING_PAYMENT.name());
        }

        Order order = new Order(address, OrderStatusEnum.AWAITING_SHIPMENT.name(),
                userService.findUserFromSecurityContextHolder(), payment, date, phone);

        for (BagProductDto product : bag) {
            Product p = bagService.convertBagProductDtoToProduct(product);
            Size size = productService.findSizeById(product.getSizeId());
            OrdersProducts ordersProducts = new OrdersProducts(order, p, String.valueOf(product.getAmount()), size);
            order.getProducts().add(ordersProducts);
        }

        return orderDao.saveOrder(order);
    }

    /**
     * Method finds orders of certain user by his email.
     *
     * @param email of user.
     * @return list of found orders.
     */
    @Override
    public List<Order> findOrdersByEmail(String email) {
        return orderDao.findOrdersByEmail(email);
    }

    /**
     * Method finds order by his status type.
     * For more information about status type see {@link OrderStatusEnum}.
     *
     * @param status for searching.
     * @return list of found orders.
     */
    @Override
    public List<Order> findOrderByStatusType(String status) {
        return orderDao.findOrdersByStatusType(status);
    }

    /**
     * Method finds all orders in database.
     *
     * @return list of found orders.
     */
    @Override
    public List<Order> findAllOrders() {
        return orderDao.findAllOrders();
    }

    /**
     * Method finds order by his ID.
     *
     * @param id of the product that must be found.
     * @return found Order object.
     */
    @Override
    public Order findOrderById(String id) {
        return orderDao.findOrderById(Long.parseLong(id));
    }

    /**
     * Method saves any order in database.
     *
     * @param order that must be saved.
     * @return reference to a saved object.
     */
    @Override
    public Order saveOrder(Order order) {
        return orderDao.saveOrder(order);
    }

    /**
     * Method finds shop income for the last week.
     *
     * @return calculated income.
     */
    @Override
    public long findIncomePerLastWeek() {
        long amount = 0;
        List<Order> orders = findOrdersForTheLastWeek();
        for (Order order : orders) {
            amount += Long.parseLong(order.getPayment().getTotalPrice());
        }
        return amount;
    }

    /**
     * Method finds shop income for the last month.
     *
     * @return calculated income.
     */
    @Override
    public long findIncomePerLastMonth() {
        long amount = 0;
        List<Order> orders = findOrdersForTheLastMonth();
        for (Order order : orders) {
            amount += Long.parseLong(order.getPayment().getTotalPrice());
        }
        return amount;
    }

    /**
     * Method finds orders with done status. See {@link OrderStatusEnum}
     *
     * @return list with found orders.
     */
    @Override
    public List<Order> findDoneOrders() {
        return orderDao.findDoneOrders();
    }

    /**
     * Method finds orders only with active status(should not be done).
     * See {@link OrderStatusEnum}.
     *
     * @return list with found orders.
     */
    @Override
    public List<Order> findActiveOrders() {
        return orderDao.findActiveOrders();
    }

    /**
     * Method finds orders for the last week.
     *
     * @return list with found orders.
     */
    @Override
    public List<Order> findOrdersForTheLastWeek() {
        return findOrdersForTheLastNDays(8);
    }

    /**
     * Method finds orders for the last month.
     *
     * @return list with found orders.
     */
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

    /**
     * Method finds orders for the last N days.
     *
     * @param n days.
     * @return list of found orders.
     */
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
