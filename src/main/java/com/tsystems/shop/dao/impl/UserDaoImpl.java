package com.tsystems.shop.dao.impl;

import com.tsystems.shop.dao.api.UserDao;
import com.tsystems.shop.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional
    public User findUserByEmail(String email) {
        Query query = em.createQuery("SELECT u FROM User u WHERE email = :email", User.class);
        query.setParameter("email", email);
        User user = (User) query.getSingleResult();
        return (User) query.getSingleResult();
    }

    @Override
    @Transactional
    public void saveNewUser(User user) {
        em.merge(user);
        em.flush();
    }
}
