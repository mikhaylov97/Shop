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
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceMockTest {

    public User user = new User();
    public Address userAddress = new Address();

    @Mock
    UserDaoImpl userDao;

    @InjectMocks
    UserServiceImpl userService;

    @Before
    public void setup() {
        //userAddress = mock(Address.class,  Mockito.RETURNS_DEEP_STUBS);
        //user = mock(User.class,  Mockito.RETURNS_DEEP_STUBS);
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
    public void testMockFindUserByEmail() {
        //prepare
        when(userDao.findUserByEmail("mi.mi.mikhaylov97@gmail.com")).thenReturn(user);
        //do
        userService.findUserByEmail(user.getEmail());
        //check
        verify(userDao).findUserByEmail(user.getEmail());
    }

    @Test
    public void testMockIsEmailFree1() {
        //prepare
        when(userDao.findUserByEmail(user.getEmail())).thenReturn(user);
        //do
        boolean result = userService.isEmailFree(user.getEmail());
        //check
        assertFalse(result);
    }

    @Test
    public void testMockIsEmailFree2() {
        //prepare
        when(userDao.findUserByEmail("test@gmail.com")).thenReturn(null);
        //do
        boolean result = userService.isEmailFree("test@gmail.com");
        //check
        assertTrue(result);
    }

    @Test
    public void testMockFindTop10Users() {
        //prepare
        when(userDao.findTopNUsers(10)).thenReturn(new ArrayList<>());
        //do
        List<User> result = userService.findTop10Users();
        //check
        verify(userDao).findTopNUsers(10);
    }

    @Test
    public void testMockFindTop10UsersDto() {
        //prepare
        List<User> topList = new ArrayList<>();
        when(userDao.findTopNUsers(10)).thenReturn(topList);
        //do
        List<UserDto> result = userService.findTop10UsersDto();
        //check
        verify(userDao).findTopNUsers(10);

    }

}
