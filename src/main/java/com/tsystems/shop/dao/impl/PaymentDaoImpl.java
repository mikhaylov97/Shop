package com.tsystems.shop.dao.impl;

import com.tsystems.shop.dao.api.PaymentDao;
import com.tsystems.shop.model.Payment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class PaymentDaoImpl implements PaymentDao {

    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional
    public void addNewPayment(Payment payment) {
        em.merge(payment);
        em.flush();
    }
}
