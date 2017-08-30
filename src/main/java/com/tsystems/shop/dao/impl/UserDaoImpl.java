package com.tsystems.shop.dao.impl;

import com.tsystems.shop.dao.api.UserDao;
import com.tsystems.shop.model.User;
import com.tsystems.shop.model.enums.UserRoleEnum;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional
    public User findUserByEmail(String email) {
        Query query = em.createQuery("SELECT u FROM User u WHERE email = :email");
        query.setParameter("email", email);
        try {
            User user = (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return (User) query.getSingleResult();
    }

    @Override
    public User findUserById(long id) {
        Query query = em.createQuery("SELECT u FROM User u WHERE id = :id");
        query.setParameter("id", id);
        User user = (User) query.getSingleResult();
        return (User) query.getSingleResult();
    }

    @Override
    @Transactional
    public void saveNewUser(User user) {
        em.merge(user);
        em.flush();
    }

    @Override
    public List<User> findAllAdmins() {
        Query query = em.createQuery("SELECT u FROM User u WHERE role = :role1 OR role = :role2");
        query.setParameter("role1", UserRoleEnum.ROLE_ADMIN.name());
        query.setParameter("role2", UserRoleEnum.ROLE_SUPER_ADMIN.name());
        List<User> result = (List<User>) query.getResultList();
        return result;
    }

    @Override
    public List<User> findSimpleAdmins() {
        Query query = em.createQuery("SELECT u FROM User u WHERE role = :role");
        query.setParameter("role", UserRoleEnum.ROLE_ADMIN.name());
        List<User> result = (List<User>) query.getResultList();
        return result;
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        em.remove(findUserById(id));
        em.flush();
    }
}
