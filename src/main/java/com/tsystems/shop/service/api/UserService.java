package com.tsystems.shop.service.api;


import com.tsystems.shop.model.User;

import java.util.List;

public interface UserService {
    User findUserByEmail(String email);
    void saveNewUser(User user);
    List<User> findAllAdmins();
    List<User> findSimpleAdmins();
    boolean isEmailFree(String email);
    void deleteUser(long id);
}
