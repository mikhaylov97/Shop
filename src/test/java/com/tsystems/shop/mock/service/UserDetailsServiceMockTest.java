package com.tsystems.shop.mock.service;

import com.tsystems.shop.model.User;
import com.tsystems.shop.service.impl.UserServiceImpl;
import com.tsystems.shop.service.impl.security.UserDetailsServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceMockTest {

    private User user;

    @Mock
    UserServiceImpl userService;

    @InjectMocks
    UserDetailsServiceImpl userDetailsService;

    @Before
    public void init() {
        user = new User();
        user.setId(1L);
        user.setEmail("mi.mi.mikhaylov97@gmail.com");
        user.setRole("ROLE_USER");
        user.setPassword("123456");
    }

    @Test
    public void loadUserByUsernameMockTest1() {
        //prepare
        when(userService.findUserByEmail(user.getEmail())).thenReturn(user);
        //do
        UserDetails result = userDetailsService.loadUserByUsername(user.getEmail());
        //check
        verify(userService).findUserByEmail(user.getEmail());
        Assert.assertNotNull(result);
        Assert.assertTrue(result.getPassword().equals(user.getPassword()));
        Assert.assertTrue(result.getUsername().equals(user.getEmail()));
        Assert.assertTrue(result.getAuthorities().contains(new SimpleGrantedAuthority(user.getRole())));
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsernameMockTest2() {
        //prepare
        when(userService.findUserByEmail(user.getEmail())).thenReturn(null);
        //do
        UserDetails result = userDetailsService.loadUserByUsername(user.getEmail());
        //check
        verify(userService).findUserByEmail(user.getEmail());
    }
}
