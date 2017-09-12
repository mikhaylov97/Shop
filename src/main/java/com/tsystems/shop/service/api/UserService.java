package com.tsystems.shop.service.api;


import com.tsystems.shop.exception.IncorrectAccountInfoException;
import com.tsystems.shop.exception.IncorrectPasswordException;
import com.tsystems.shop.model.Address;
import com.tsystems.shop.model.User;
import com.tsystems.shop.model.dto.UserDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Interface provide us API we can use to manipulate on product size.
 */
public interface UserService {
    /**
     * Method delete certain User by his ID.
     * @param id of the user that must be deleted.
     */
    void deleteUser(long id);

    /**
     * Method looks for all admins.
     * @return list of found users with admin role. See {@link com.tsystems.shop.model.enums.UserRoleEnum}
     */
    List<User> findAllAdmins();

    /**
     * Method add new user to database.
     * @param user that must be added.
     */
    void saveNewUser(User user);

    /**
     * Method looks for top 10 users. The more money the spent the higher rating they have.
     * @return list of found users.
     */
    List<User> findTop10Users();

    /**
     * Method looks for simple admins with ROLE_ADMIN.
     * For more details see {@link com.tsystems.shop.model.enums.UserRoleEnum}
     * @return list of found admins.
     */
    List<User> findSimpleAdmins();

    /**
     * Method looks for top N users. The more money the spent the higher rating they have.
     * @param n - capacity of the top list.
     * @return list of found users.
     */
    List<User> findTopNUsers(int n);

    /**
     * Method by user ID looks for total cash spent by him.
     * @param id of the user.
     * @return all spent money.
     */
    long findTotalCashById(long id);

    /**
     * Method looks for top 10 users. The more money the spent the higher rating they have.
     * @return list of found users in dto format {@link com.tsystems.shop.model.dto.ProductDto}.
     */
    List<UserDto> findTop10UsersDto();

    /**
     * Method checks whether email is already occupied.
     * @param email that must be checked.
     * @return true if email is already occupied and false otherwise.
     */
    boolean isEmailFree(String email);

    /**
     * Method looks for user by his email.
     * @param email of the user.
     * @return found user object.
     */
    User findUserByEmail(String email);

    /**
     * Method converts User object {@link User} to UserDto object {@link UserDto}.
     * @param user - object that must be converted.
     * @return converted UserDto object.
     */
    UserDto convertUserToUserDto(User user);

    /**
     * Every user have their own SecurityContextHolder which is connected with session.
     * So, this method allow us to get User object from this SecurityContextHolder
     * and it will always return different User objects for all users. Users always will get themselves.
     * @return User object {@link User}
     */
    User findUserFromSecurityContextHolder();

    /**
     * Every user have their own SecurityContextHolder which is connected with session.
     * So, this method allow us to get User object from this SecurityContextHolder
     * and it will always return different User objects for all users. Users always will get themselves.
     * @return Email of the user from SecurityContextHolder.
     */
    String findUserEmailFromSecurityContextHolder();

    /**
     * Method converts User objects list {@link User} to UserDto objects list {@link UserDto}.
     * @param users - list that must be converted.
     * @return converted UserDto objects list.
     */
    List<UserDto> convertUsersToUsersDto(List<User> users);

    /**
     * Every user have their own SecurityContextHolder which is connected with session.
     * So, this method allow us to get User object from this SecurityContextHolder
     * and it will always return different User objects for all users. Users always will get themselves.
     * This method should get user object from SecurityContextHolder and change his password.
     * @throws IncorrectPasswordException when entered old password doesn't math the old value
     * or password(old or new) doesn't satisfy password patter.
     */
    void changePasswordFromSecurityContextHolder(String oldPassword, String newPassword)
            throws IncorrectPasswordException;

    /**
     * Task of the method is to authenticate registered user and to give him simple user rights.
     * @param email of the registered user.
     */
    void authenticateUserAndSetSession(String email, HttpServletRequest request);

    /**
     * Every user have their own SecurityContextHolder which is connected with session.
     * So, this method allow us to get User object from this SecurityContextHolder
     * and it will always return different User objects for all users. Users always will get themselves.
     * This method should get user object from SecurityContextHolder and change his personal information.
     * @throws IncorrectAccountInfoException when in any field was entered incorrect data.
     */
    void changeInformationFromSecurityContextHolder(String phone, String birthday, String name,
                                     String surname, Address address)
            throws IncorrectAccountInfoException;
}
