package com.tsystems.shop.dao.impl;

import com.tsystems.shop.dao.api.OrderDao;
import com.tsystems.shop.model.Order;
import com.tsystems.shop.model.OrdersProducts;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {

    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional
    public void addNewOrder(Order order) {
        em.merge(order);
        em.flush();
    }

    @Override
    public List<Order> findOrdersByEmail(String email) {
        Query query = em.createQuery("SELECT o FROM Order o WHERE user.email = :email");
        query.setParameter("email", email);
        List<Order> orders = (List<Order>) query.getResultList();
        return orders;
    }

    @Override
    public List<Order> findOrdersByStatusType(String status) {
        Query query = em.createQuery("SELECT o FROM Order o WHERE orderStatus = :status");
        query.setParameter("status", status);
        List<Order> orders = (List<Order>) query.getResultList();
        return orders;
    }

    @Override
    public List<Order> findAllOrders() {
        Query query = em.createQuery("SELECT o FROM Order o");
        List<Order> orders = (List<Order>) query.getResultList();
        return orders;
    }

    @Override
    public Order findOrderById(long id) {
        Query query = em.createQuery("SELECT o FROM Order o WHERE id = :id");
        query.setParameter("id", id);
        Order order = (Order) query.getSingleResult();
        return order;
    }

    @Override
    @Transactional
    public Order saveOrder(Order order) {
        em.merge(order);
        em.flush();
        return order;
    }

    @Override
    @Transactional
    public OrdersProducts savePartOfOrder(OrdersProducts ordersProducts) {
        em.merge(ordersProducts);
        em.flush();
        return ordersProducts;
    }

    @Override
    public List<Order> findDoneOrders() {
        Query query = em.createQuery("SELECT o FROM Order o WHERE orderStatus = 'DONE'");
        List<Order> orders = (List<Order>) query.getResultList();
        return orders;
    }

    @Override
    public List<Order> findActiveOrders() {
        Query query = em.createQuery("SELECT o FROM Order o WHERE orderStatus != 'DONE'");
        List<Order> orders = (List<Order>) query.getResultList();
        return orders;
    }
}
