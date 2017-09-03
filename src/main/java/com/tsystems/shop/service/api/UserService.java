package com.tsystems.shop.service.api;


import com.tsystems.shop.model.User;
import com.tsystems.shop.model.dto.UserDto;

import java.util.List;

public interface UserService {
    User findUserByEmail(String email);
    void saveNewUser(User user);
    List<User> findAllAdmins();
    List<User> findSimpleAdmins();
    boolean isEmailFree(String email);
    void deleteUser(long id);
    List<User> findTop10Users();
    List<User> findTopNUsers(int n);
    List<UserDto> findTop10UsersDto();
    long findTotalCashById(long id);
    List<UserDto> convertUsersToUsersDto(List<User> users);
    UserDto convertUserToUserDto(User user);
}
