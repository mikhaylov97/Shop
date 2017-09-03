package com.tsystems.shop.service.impl;

import com.tsystems.shop.dao.api.UserDao;
import com.tsystems.shop.model.User;
import com.tsystems.shop.model.dto.UserDto;
import com.tsystems.shop.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public List<User> findTop10Users() {
        return findTopNUsers(10);
    }

    @Override
    public List<User> findTopNUsers(int n) {
        return userDao.findTopNUsers(n);
    }

    @Override
    public List<UserDto> findTop10UsersDto() {
        return convertUsersToUsersDto(findTopNUsers(10));
    }

    @Override
    public long findTotalCashById(long id) {
        return userDao.findTotalCashById(id);
    }

    @Override
    public List<UserDto> convertUsersToUsersDto(List<User> users) {
        List<UserDto> resultList = new ArrayList<>();
        for (User user : users) {
            resultList.add(convertUserToUserDto(user));
        }
        return resultList;
    }

    @Override
    public UserDto convertUserToUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setAddress(user.getAddress() == null ? "" : user.getAddress().toString());
        dto.setEmail(user.getEmail());
        dto.setBirthday(user.getBirthday() == null ? "" : user.getBirthday());
        dto.setName(user.getName());
        dto.setPhone(user.getPhone() == null ? "" : user.getPhone());
        dto.setSurname(user.getSurname());
        dto.setTotalCash(String.valueOf(userDao.findTotalCashById(user.getId())));
        return dto;
    }
}
