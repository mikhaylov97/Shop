package com.tsystems.shop.service.impl;

import com.tsystems.shop.dao.api.UserDao;
import com.tsystems.shop.exception.IncorrectAccountInfoException;
import com.tsystems.shop.exception.IncorrectPasswordException;
import com.tsystems.shop.model.Address;
import com.tsystems.shop.model.User;
import com.tsystems.shop.model.dto.ProductDto;
import com.tsystems.shop.model.dto.UserDto;
import com.tsystems.shop.model.enums.UserRoleEnum;
import com.tsystems.shop.service.api.UserService;
import com.tsystems.shop.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Bag service. It is used to bag manipulations.
 */
@Service
public class UserServiceImpl implements UserService{

    /**
     * Injected by spring userDao bean.
     */
    private final UserDao userDao;

    /**
     * Injecting controller.
     * @param userDao that must be injected.
     */
    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Method looks for user by his email.
     *
     * @param email of the user.
     * @return found user object.
     */
    @Override
    public User findUserByEmail(String email) {
        if (email == null || email.isEmpty()) return null;
        return userDao.findUserByEmail(email);
    }

    /**
     * Method add new user to database.
     *
     * @param user that must be added.
     */
    @Override
    public void saveNewUser(User user) {
        if (user != null) userDao.saveUser(user);
    }

    /**
     * Method looks for all admins.
     *
     * @return list of found users with admin role. See {@link UserRoleEnum}
     */
    @Override
    public List<User> findAllAdmins() {
        return userDao.findAllAdmins();
    }

    /**
     * Method looks for simple admins with ROLE_ADMIN.
     * For more details see {@link UserRoleEnum}
     *
     * @return list of found admins.
     */
    @Override
    public List<User> findSimpleAdmins() {
        return userDao.findSimpleAdmins();
    }

    /**
     * Method checks whether email is already occupied.
     *
     * @param email that must be checked.
     * @return true if email is already occupied and false otherwise.
     */
    @Override
    public boolean isEmailFree(String email) {
        if (email == null || email.isEmpty()) return false;
        return userDao.findUserByEmail(email) == null;
    }

    /**
     * Method delete certain User by his ID.
     *
     * @param id of the user that must be deleted.
     */
    @Override
    public void deleteUser(long id) {
        userDao.deleteUser(id);
    }

    /**
     * Method looks for top 10 users. The more money they spent the higher rating they have.
     *
     * @return list of found users.
     */
    @Override
    public List<User> findTop10Users() {
        return findTopNUsers(10);
    }

    /**
     * Method looks for top N users. The more money the spent the higher rating they have.
     *
     * @param n - capacity of the top list.
     * @return list of found users.
     */
    @Override
    public List<User> findTopNUsers(int n) {
        return userDao.findTopNUsers(n);
    }

    /**
     * Method looks for top 10 users. The more money the spent the higher rating they have.
     *
     * @return list of found users in dto format {@link ProductDto}.
     */
    @Override
    public List<UserDto> findTop10UsersDto() {
        return convertUsersToUsersDto(findTopNUsers(10));
    }

    /**
     * Method by user ID looks for total cash spent by him.
     *
     * @param id of the user.
     * @return all spent money.
     */
    @Override
    public long findTotalCashById(long id) {
        return userDao.findTotalCashById(id);
    }

    /**
     * Method converts User objects list {@link User} to UserDto objects list {@link UserDto}.
     *
     * @param users - list that must be converted.
     * @return converted UserDto objects list.
     */
    @Override
    public List<UserDto> convertUsersToUsersDto(List<User> users) {
        List<UserDto> resultList = new ArrayList<>();
        for (User user : users) {
            resultList.add(convertUserToUserDto(user));
        }
        return resultList;
    }

    /**
     * Method converts User object {@link User} to UserDto object {@link UserDto}.
     *
     * @param user - object that must be converted.
     * @return converted UserDto object.
     */
    @Override
    public UserDto convertUserToUserDto(User user) {
        if (user == null) return null;
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

    /**
     * Every user have their own SecurityContextHolder which is connected with session.
     * So, this method allow us to get User object from this SecurityContextHolder
     * and it will always return different User objects for all users. Users always will get themselves.
     *
     * @return User object {@link User}
     */
    @Override
    public User findUserFromSecurityContextHolder() {
        return findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    /**
     * Every user have their own SecurityContextHolder which is connected with session.
     * So, this method allow us to get User object from this SecurityContextHolder
     * and it will always return different User objects for all users. Users always will get themselves.
     *
     * @return Email of the user from SecurityContextHolder.
     */
    @Override
    public String findUserEmailFromSecurityContextHolder() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    /**
     * Every user have their own SecurityContextHolder which is connected with session.
     * So, this method allow us to get User object from this SecurityContextHolder
     * and it will always return different User objects for all users. Users always will get themselves.
     * This method should get user object from SecurityContextHolder and change his password.
     *
     * @param oldPassword entered by user present password.
     * @param newPassword entered by user password that he would like to set.
     * @throws IncorrectPasswordException when entered old password doesn't math the old value
     *                                    or password(old or new) doesn't satisfy password patter.
     */
    @Override
    public void changePasswordFromSecurityContextHolder(String oldPassword, String newPassword) {
        String passwordRegexp = "^[A-Za-z1-9]{6,10}$";
        User user = findUserFromSecurityContextHolder();
        if (user.getPassword().equals(oldPassword)) {
            if (!newPassword.matches(passwordRegexp)) throw new IncorrectPasswordException("Entered incorrect new password");
            user.setPassword(newPassword);
            userDao.saveUser(user);
        } else {
            throw new IncorrectPasswordException("Entered incorrect old password");
        }
    }

    /**
     * Every user have their own SecurityContextHolder which is connected with session.
     * So, this method allow us to get User object from this SecurityContextHolder
     * and it will always return different User objects for all users. Users always will get themselves.
     * This method should get user object from SecurityContextHolder and change his personal information.
     *
     * @param phone number of the user.
     * @param birthday of the user.
     * @param name of the user.
     * @param surname of the user.
     * @param address of the user.
     * @throws IncorrectAccountInfoException when in any field was entered incorrect data.
     */
    @Override
    public void changeInformationFromSecurityContextHolder(String phone, String birthday, String name,
                                                           String surname, Address address) {
        String phoneRegexp = "^\\d(\\-)?[0-9]{3}\\-?[0-9]{3}\\-?[0-9]{2}-?[0-9]{2}$";
        String charactersRegexp = "^[A-Za-z]{1,10}(\\s|\\.|-)?[A-Za-z]{0,10}(\\s|-)?[A-Za-z]{0,10}$";
        User user = findUserFromSecurityContextHolder();
        if (!phone.matches(phoneRegexp)) throw new IncorrectAccountInfoException("Entered incorrect phone number");
        user.setPhone(phone);
        if (!birthday.equals("")) {
            user.setBirthday(DateUtil.getDateFromStringDtfFormat(birthday));
        } else {
            user.setBirthday(birthday);
        }
        if (!name.matches(charactersRegexp)) {
            throw new IncorrectAccountInfoException("Entered incorrect name");
        }
        user.setName(name);
        if (!surname.matches(charactersRegexp)) {
            throw new IncorrectAccountInfoException("Entered incorrect surname");
        }
        user.setSurname(surname);

        changeAddressFieldsToAnotherAddressFields(user.getAddress(), address);

        userDao.saveUser(user);
    }

    @Override
    public Address changeAddressFieldsToAnotherAddressFields(Address toChange, Address fromWhereChange) {
        String charactersRegexp = "^[A-Za-z]{1,10}(\\s|\\.|-)?[A-Za-z]{0,10}(\\s|-)?[A-Za-z]{0,10}$";
        String houseAndApartmentRegexp = "^[1-9]{1,6}[A-Za-z]?$";
        String postcodeRegexp = "^[1-9]{6,8}$";
        if (!fromWhereChange.getCountry().equals("") && !fromWhereChange.getCountry().matches(charactersRegexp)) {
            throw new IncorrectAccountInfoException("Entered incorrect country name");
        }
        toChange.setCountry(fromWhereChange.getCountry());
        if (!fromWhereChange.getCity().equals("") && !fromWhereChange.getCity().matches(charactersRegexp)) {
            throw new IncorrectAccountInfoException("Entered incorrect city name");
        }
        toChange.setCity(fromWhereChange.getCity());
        if (!fromWhereChange.getHouse().equals("") && !fromWhereChange.getHouse().matches(houseAndApartmentRegexp)) {
            throw new IncorrectAccountInfoException("Entered incorrect house number");
        }
        toChange.setHouse(fromWhereChange.getHouse());
        if (!fromWhereChange.getStreet().equals("") && !fromWhereChange.getStreet().matches(charactersRegexp)) {
            throw new IncorrectAccountInfoException("Entered incorrect street name");
        }
        toChange.setStreet(fromWhereChange.getStreet());
        if (!fromWhereChange.getPostcode().equals("") && !fromWhereChange.getPostcode().matches(postcodeRegexp)) {
            throw new IncorrectAccountInfoException("Entered incorrect postcode number");
        }
        toChange.setPostcode(fromWhereChange.getPostcode());
        if (!fromWhereChange.getApartment().equals("") && !fromWhereChange.getApartment().matches(houseAndApartmentRegexp)) {
            throw new IncorrectAccountInfoException("Entered incorrect apartment number");
        }
        toChange.setApartment(fromWhereChange.getApartment());

        return toChange;
    }
}
