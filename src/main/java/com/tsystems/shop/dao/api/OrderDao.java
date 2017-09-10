package com.tsystems.shop.dao.api;


import com.tsystems.shop.model.Order;

import java.util.List;

/**
 * This interface provide us API through which we will communicate with database.
 */
public interface OrderDao {
    /**
     * Method find in database Order by ID
     * @param id of the order we want to find
     * @return necessary Order
     */
    Order findOrderById(long id);

    /**
     * Method save order in database.
     * @param order - directly, the mapped object we need to save.
     * @return reference to an saved object.
     */
    Order saveOrder(Order order);

    /**
     * Method find all orders from the database.
     * @return list of found orders.
     */
    List<Order> findAllOrders();

    /**
     * Method find all orders which is already in archive.
     * @return list of found orders.
     */
    List<Order> findDoneOrders();

    /**
     * Method find all active orders which is processing yet.
     * @return list of found orders.
     */
    List<Order> findActiveOrders();

    /**
     * Method find all orders of some user by his email.
     * @param email of the user.
     * @return list of found orders.
     */
    List<Order> findOrdersByEmail(String email);

    /**
     * Method find all orders by status.
     * @param status of the orders which should be found.
     * @return list of found orders.
     */
    List<Order> findOrdersByStatusType(String status);
}
