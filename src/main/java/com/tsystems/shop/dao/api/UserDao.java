package com.tsystems.shop.dao.api;


import com.tsystems.shop.model.User;

import java.util.List;

/**
 * This interface provide us API through which we will communicate with database.
 */
public interface UserDao {

    /**
     * Method should delete user form the database by his ID.
     * @param id of the user.
     */
    void deleteUser(long id);

    /**
     * Method should find all admins from the database.
     * @return list of found users.
     */
    List<User> findAllAdmins();

    /**
     * Method should find user by his ID.
     * @param id of the user.
     * @return reference to an found User object.
     */
    User findUserById(long id);

    /**
     * Method should saves users.
     * @param user - reference to an User object we need to save.
     */
    void saveUser(User user);

    /**
     * Method finds simple admins.
     * @return list of found simple admins.
     */
    List<User> findSimpleAdmins();

    /**
     * Method should find top N users.
     * @param n is input parameter.
     * @return List of found users.
     */
    List<User> findTopNUsers(int n);

    /**
     * Method should calculate final user's total cash. It is needed for top users of the shop.
     * @param id of the user.
     * @return number of total cash of the users by certain ID.
     */
    long findTotalCashById(long id);

    /**
     * Method should find user by his email.
     * @param email unique email of the user.
     * @return reference to an found User object.
     */
    User findUserByEmail(String email);
}
