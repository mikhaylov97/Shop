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
import java.util.ArrayList;
import java.util.List;

/**
 * Class which implements all necessary methods which allow us
 * to work with database and users.
 */
@Repository
public class UserDaoImpl implements UserDao {

    /**
     * It's an exemplar created by EntityManagerFactory we created and configured in DatabaseConfig class.
     * EntityManager provide us special API for working with database.
     * In particular, we can create different HQL queries. After executing that queries will give us
     * all necessary data.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * See {@link UserDao}
     * @param email unique email of the user.
     * @return reference to a found user.
     */
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

    /**
     * See {@link UserDao}
     * @param id of the user.
     * @return reference to a found user by his id.
     */
    @Override
    public User findUserById(long id) {
        Query query = em.createQuery("SELECT u FROM User u WHERE id = :id");
        query.setParameter("id", id);
//        User user = (User) query.getSingleResult();
        return (User) query.getSingleResult();
    }

    /**
     * See {@link UserDao}
     * @param user - reference to an User object we need to save.
     */
    @Override
    @Transactional
    public void saveUser(User user) {
        em.merge(user);
        em.flush();
    }

    /**
     * See {@link UserDao}
     * @return list of found admins.
     */
    @Override
    public List<User> findAllAdmins() {
        Query query = em.createQuery("SELECT u FROM User u WHERE role = :role1 OR role = :role2");
        query.setParameter("role1", UserRoleEnum.ROLE_ADMIN.name());
        query.setParameter("role2", UserRoleEnum.ROLE_SUPER_ADMIN.name());
        List<User> result = (List<User>) query.getResultList();
        return result;
    }

    /**
     * See {@link UserDao}
     * @return list of found simple admins.
     */
    @Override
    public List<User> findSimpleAdmins() {
        Query query = em.createQuery("SELECT u FROM User u WHERE role = :role");
        query.setParameter("role", UserRoleEnum.ROLE_ADMIN.name());
        List<User> result = (List<User>) query.getResultList();
        return result;
    }

    /**
     * This method allow us to delete User.
     * @param id of the user.
     */
    @Override
    @Transactional
    public void deleteUser(long id) {
        em.remove(findUserById(id));
        em.flush();
    }

    /**
     * See {@link UserDao}
     * @param n is input parameter.
     * @return list of found top N users.
     */
    @Override
    public List<User> findTopNUsers(int n) {
        Query listOfUsersIdQuery = em.createQuery("SELECT o.user.id FROM Order o GROUP BY o.user.id ORDER BY SUM(CAST(o.payment.totalPrice as long)) DESC");
        listOfUsersIdQuery.setMaxResults(n);
        List<User> topUsers = new ArrayList<>();
        for (Long id : (List<Long>)listOfUsersIdQuery.getResultList()) {
            topUsers.add(findUserById(id));
        }
        return topUsers;
    }

    /**
     * See {@link UserDao}
     * @param id of the user.
     * @return final user's total cash. It is needed for top users of the shop.
     */
    @Override
    public long findTotalCashById(long id) {
        Query totalCashQuery = em.createQuery("SELECT SUM(CAST(o.payment.totalPrice as long)) FROM Order o WHERE o.user.id = :id");
        totalCashQuery.setParameter("id", id);
        long cash = (Long)totalCashQuery.getSingleResult();
        return cash;
    }
}
