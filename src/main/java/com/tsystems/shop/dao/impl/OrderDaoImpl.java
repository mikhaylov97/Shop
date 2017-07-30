package com.tsystems.shop.dao.impl;

import com.tsystems.shop.dao.api.OrderDao;
import com.tsystems.shop.model.Order;
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
}
