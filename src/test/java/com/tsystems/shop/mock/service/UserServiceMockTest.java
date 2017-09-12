package com.tsystems.shop.mock.service;

import com.tsystems.shop.dao.impl.UserDaoImpl;
import com.tsystems.shop.model.Address;
import com.tsystems.shop.model.User;
import com.tsystems.shop.model.dto.UserDto;
import com.tsystems.shop.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceMockTest {

    private User user = new User();
    private Address userAddress = new Address();

    @Mock
    UserDaoImpl userDao;

    @InjectMocks
    UserServiceImpl userService;

    @Before
    public void setup() {
        userAddress.setId(1L);
        userAddress.setApartment("271");
        userAddress.setHouse("15");
        userAddress.setStreet("Torzhkovskaya");
        userAddress.setCity("Saint-Petersburg");
        userAddress.setPostcode("197343");
        userAddress.setCountry("Russia");

        user.setAddress(userAddress);
        user.setBirthday("14-04-1997");
        user.setName("Artyom");
        user.setSurname("Mikhaylov");
        user.setPhone("89217841538");
        user.setPassword("123456");
        user.setEmail("mi.mi.mikhaylov97@gmail.com");
        user.setRole("ROLE_ADMIN");
        user.setId(1L);
    }

    @Test
    public void findUserByEmailMockTest1() {
        //prepare
        when(userDao.findUserByEmail(user.getEmail())).thenReturn(user);
        //do
        userService.findUserByEmail(user.getEmail());
        //check
        verify(userDao).findUserByEmail(user.getEmail());
    }

    @Test
    public void findUserByEmailMockTest2() {
        //do
        User result = userService.findUserByEmail(null);
        //check
        assertNull(result);
    }

    @Test
    public void findUserByEmailMockTest3() {
        //do
        User result = userService.findUserByEmail("");
        //check
        assertNull(result);
    }

    @Test
    public void saveNewUserMockTest1() {
        //do
        userService.saveNewUser(user);
        //check
        verify(userDao).saveUser(user);
    }

    @Test
    public void saveNewUserMockTest2() {
        //do
        userService.saveNewUser(null);
        //check
        verifyZeroInteractions(userDao);
    }

    @Test
    public void findAllAdminsMockTest1() {
        List<User> users = new ArrayList<>();
        //prepare
        when(userDao.findAllAdmins()).thenReturn(users);
        //do
        userService.findAllAdmins();
        //check
        verify(userDao).findAllAdmins();
    }

    @Test
    public void findSimpleAdminsMockTest1() {
        List<User> users = new ArrayList<>();
        //prepare
        when(userDao.findSimpleAdmins()).thenReturn(users);
        //do
        userService.findSimpleAdmins();
        //check
        verify(userDao).findSimpleAdmins();
    }

    @Test
    public void isEmailFreeMockTest1() {
        //prepare
        when(userDao.findUserByEmail("mi.mi.mikhaylov97@gmail.com")).thenReturn(user);
        //do
        boolean result = userService.isEmailFree("mi.mi.mikhaylov97@gmail.com");
        //check
        verify(userDao).findUserByEmail("mi.mi.mikhaylov97@gmail.com");
        assertFalse(result);
    }

    @Test
    public void isEmailFreeMockTest2() {
        //prepare
        when(userDao.findUserByEmail("mi.mi.mikhaylov97@gmail.com")).thenReturn(null);
        //do
        boolean result = userService.isEmailFree("mi.mi.mikhaylov97@gmail.com");
        //check
        verify(userDao).findUserByEmail("mi.mi.mikhaylov97@gmail.com");
        assertTrue(result);
    }

    @Test
    public void isEmailFreeMockTest3() {
        //do
        boolean result = userService.isEmailFree("");
        //check
        verifyZeroInteractions(userDao);
        assertFalse(result);
    }

    @Test
    public void isEmailFreeMockTest4() {
        //do
        boolean result = userService.isEmailFree(null);
        //check
        verifyZeroInteractions(userDao);
        assertFalse(result);
    }

    @Test
    public void deleteUserMockTest1() {
        //do
        userService.deleteUser(1L);
        //check
        verify(userDao).deleteUser(1L);
    }

    @Test
    public void findTop10UsersMockTest1() {
        List<User> users = new ArrayList<>();
        //prepare
        when(userDao.findTopNUsers(10)).thenReturn(users);
        //do
        userService.findTop10Users();
        //check
        verify(userDao).findTopNUsers(10);
    }

    @Test
    public void findTopNUsersMockTest1() {
        List<User> users = new ArrayList<>();
        //prepare
        when(userDao.findTopNUsers(11)).thenReturn(users);
        //do
        userService.findTopNUsers(11);
        //check
        verify(userDao).findTopNUsers(11);
    }

    @Test
    public void findTotalCashByIdMockTest1() {
        //prepare
        when(userDao.findTotalCashById(1L)).thenReturn(109L);
        //do
        long result = userService.findTotalCashById(1L);
        //check
        verify(userDao).findTotalCashById(1L);
        assertEquals(109L, result);
    }

    @Test
    public void convertUsersToUsersDtoMockTest1() {
        //prepare
        when(userDao.findTotalCashById(user.getId())).thenReturn(109L);
        //do
        List<UserDto> result = userService.convertUsersToUsersDto(new ArrayList<>(Collections.singletonList(user)));
        //check
        verify(userDao).findTotalCashById(user.getId());
        assertTrue(result.get(0).getEmail().equals(user.getEmail()));
        assertTrue(result.get(0).getTotalCash().equals(String.valueOf(109L)));
        assertTrue(result.get(0).getBirthday().equals(user.getBirthday()));
        assertTrue(result.get(0).getAddress().equals(user.getAddress().toString()));
        assertTrue(result.get(0).getPhone().equals(user.getPhone()));
    }

    @Test
    public void convertUsersToUsersDtoMockTest2() {
        //do
        List<UserDto> result = userService.convertUsersToUsersDto(new ArrayList<>());
        //check
        verifyZeroInteractions(userDao);
        assertNotNull(result);
        assertTrue(result.size() == 0);
    }

    @Test
    public void convertUserToUserDtoMockTest1() {
        //prepare
        when(userDao.findTotalCashById(1L)).thenReturn(109L);
        //do
        UserDto userDto = userService.convertUserToUserDto(user);
        //check
        verify(userDao).findTotalCashById(1L);
        assertTrue(userDto.getEmail().equals(user.getEmail()));
        assertTrue(userDto.getName().equals(user.getName()));
        assertTrue(userDto.getTotalCash().equals(String.valueOf(109L)));
        assertTrue(userDto.getBirthday().equals(user.getBirthday()));
    }

    @Test
    public void convertUserToUserDtoMockTest2() {
        //do
        UserDto result = userService.convertUserToUserDto(null);
        //check
        verifyZeroInteractions(userDao);
        assertNull(result);
    }

}
