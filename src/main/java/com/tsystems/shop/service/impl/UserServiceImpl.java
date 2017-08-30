package com.tsystems.shop.service.impl;

import com.tsystems.shop.dao.api.UserDao;
import com.tsystems.shop.model.User;
import com.tsystems.shop.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Override
    public User findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    @Override
    public void saveNewUser(User user) {
        userDao.saveNewUser(user);
    }

    @Override
    public List<User> findAllAdmins() {
        return userDao.findAllAdmins();
    }

    @Override
    public List<User> findSimpleAdmins() {
        return userDao.findSimpleAdmins();
    }

    @Override
    public boolean isEmailFree(String email) {
        return userDao.findUserByEmail(email) == null;
    }

    @Override
    public void deleteUser(long id) {
        userDao.deleteUser(id);
    }
}
