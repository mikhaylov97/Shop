package com.tsystems.shop.dao.impl;

import com.tsystems.shop.dao.api.OrderDao;
import com.tsystems.shop.model.Order;
import com.tsystems.shop.model.enums.OrderStatusEnum;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Class which implements all necessary methods which allow us
 * to work with database and orders.
 */
@Repository
public class OrderDaoImpl implements OrderDao {

    /**
     * It's an exemplar created by EntityManagerFactory we created and configured in DatabaseConfig class.
     * EntityManager provide us special API for working with database.
     * In particular, we can create different HQL queries. After executing that queries will give us
     * all necessary data.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * See {@link OrderDao}
     * @param email of the user.
     * @return list of found orders.
     */
    @Override
    public List<Order> findOrdersByEmail(String email) {
        Query query = em.createQuery("SELECT o FROM Order o WHERE user.email = :email");
        query.setParameter("email", email);
        return (List<Order>) query.getResultList();
    }

    /**
     * See {@link OrderDao}
     * @param status of the orders which should be found.
     * @return list of found orders
     */
    @Override
    public List<Order> findOrdersByStatusType(String status) {
        Query query = em.createQuery("SELECT o FROM Order o WHERE orderStatus = :status");
        query.setParameter("status", status);
        return (List<Order>) query.getResultList();
    }

    /**
     * See {@link OrderDao}
     * @return list of found orders.
     */
    @Override
    public List<Order> findAllOrders() {
        Query query = em.createQuery("SELECT o FROM Order o");
        return (List<Order>) query.getResultList();
    }

    /**
     * See {@link OrderDao}
     * @param id of the order we want to find
     * @return reference to an found Order object
     */
    @Override
    public Order findOrderById(long id) {
        Query query = em.createQuery("SELECT o FROM Order o WHERE id = :id");
        query.setParameter("id", id);
        return (Order) query.getSingleResult();
    }

    /**
     * See {@link OrderDao}
     * @param order - directly, the mapped object we need to save.
     * @return reference to an saved Order object.
     */
    @Override
    @Transactional
    public Order saveOrder(Order order) {
        Order saved = em.merge(order);
        em.flush();
        return saved;
    }

    /**
     * See {@link OrderDao}
     * @return list of found orders.
     */
    @Override
    public List<Order> findDoneOrders() {
        Query query = em.createQuery("SELECT o FROM Order o WHERE orderStatus = :status");
        query.setParameter("status", OrderStatusEnum.DONE.toString());
        return (List<Order>) query.getResultList();
    }

    /**
     * See {@link OrderDao}
     * @return list of found orders.
     */
    @Override
    public List<Order> findActiveOrders() {
        Query query = em.createQuery("SELECT o FROM Order o WHERE orderStatus != :status");
        query.setParameter("status", OrderStatusEnum.DONE.toString());
        return (List<Order>) query.getResultList();
    }
}
