package com.tsystems.shop.service.api;


import com.tsystems.shop.model.Order;
import com.tsystems.shop.model.User;
import com.tsystems.shop.model.dto.BagProductDto;

import java.util.List;

/**
 * Interface provide us API we can use to manipulate categories.
 */
public interface OrderService {
    /**
     * Method finds all orders in database.
     * @return list of found orders.
     */
    List<Order> findAllOrders();

    /**
     * Method saves any order in database.
     * @param order that must be saved.
     * @return reference to a saved object.
     */
    Order saveOrder(Order order);

    /**
     * Method finds shop income for the last week.
     * @return calculated income.
     */
    long findIncomePerLastWeek();

    /**
     * Method finds orders with done status. See {@link com.tsystems.shop.model.enums.OrderStatusEnum}
     * @return list with found orders.
     */
    List<Order> findDoneOrders();

    /**
     * Method finds shop income for the last month.
     * @return calculated income.
     */
    long findIncomePerLastMonth();

    /**
     * Method finds order by his ID.
     * @param id of the product that must be found.
     * @return found Order object.
     */
    Order findOrderById(String id);

    /**
     * Method finds orders only with active status(should not be done).
     * See {@link com.tsystems.shop.model.enums.OrderStatusEnum}.
     * @return list with found orders.
     */
    List<Order> findActiveOrders();

    /**
     * This method sends message to the customer email.
     */
    void sendMessage(Order order, User user, List<BagProductDto> bag, String address,
                     String source, String target, String title);

    /**
     * Method finds orders for the last week.
     * @return list with found orders.
     */
    List<Order> findOrdersForTheLastWeek();

    /**
     * Method finds orders for the last month.
     * @return list with found orders.
     */
    List<Order> findOrdersForTheLastMonth();

    /**
     * Method finds orders of certain user by his email.
     * @param email of user.
     * @return list of found orders.
     */
    List<Order> findOrdersByEmail(String email);

    /**
     * Method finds orders for the last N days.
     * @param n days.
     * @return list of found orders.
     */
    List<Order> findOrdersForTheLastNDays(int n);

    /**
     * Method finds order by his status type.
     * For more information about status type see {@link com.tsystems.shop.model.enums.OrderStatusEnum}.
     * @param status for searching.
     * @return list of found orders.
     */
    List<Order> findOrderByStatusType(String status);

    /**
     * Method filters orders by custom admins parameters.
     * @param dateFrom - left bound of order creating date.
     * @param dateTo - right bound of order creating date.
     * @param paymentStatus of orders which must be found.
     * @param orderStatus of orders which must be found.
     * @return filtered list with found orders.
     */
    List<Order> filterActiveOrdersByParameters(String type, String dateFrom, String dateTo, String paymentStatus, String orderStatus);

    /**
     * Method saves new orders with necessary parameters.
     * @param shippingCost - order shipping cost
     * @param country - delivery country name
     * @param city - delivery city name
     * @param street - delivery street name
     * @param postcode - delivery postcode number
     * @param house - delivery house number
     * @param apartment - delivery apartment number
     * @param phone - customer phone number.
     * @param bag - list with bag products.
     * @param creditCard - tells is it credit card payment type.
     * @return reference to a saved object.
     */
    Order saveOrder(String shippingCost, String country, String city,
                    String street, String postcode, String house,
                    String apartment, String phone, List<BagProductDto> bag,
                    boolean creditCard);
}
