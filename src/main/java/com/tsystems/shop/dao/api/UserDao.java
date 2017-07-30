package com.tsystems.shop.dao.api;


import com.tsystems.shop.model.User;

public interface UserDao {
    User findUserByEmail(String email);
    void saveNewUser(User user);
}
