package com.tsystems.shop.dao.api;


import com.tsystems.shop.model.User;

import java.util.List;

public interface UserDao {
    User findUserByEmail(String email);
    User findUserById(long id);
    void saveNewUser(User user);
    List<User> findAllAdmins();
    List<User> findSimpleAdmins();
    void deleteUser(long id);
}
