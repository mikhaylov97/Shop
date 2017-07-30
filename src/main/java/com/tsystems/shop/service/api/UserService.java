package com.tsystems.shop.service.api;


import com.tsystems.shop.model.User;

public interface UserService {
    User findUserByEmail(String email);
    void saveNewUser(User user);
}
