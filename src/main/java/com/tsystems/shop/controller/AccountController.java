package com.tsystems.shop.controller;

import com.tsystems.shop.model.Address;
import com.tsystems.shop.model.User;
import com.tsystems.shop.service.api.OrderService;
import com.tsystems.shop.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "")
    public ModelAndView showSettingsPage() {
        ModelAndView modelAndView = new ModelAndView("account");
        modelAndView.addObject("user",
                userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
        modelAndView.addObject("orders",
                orderService.findOrdersByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
        return modelAndView;
    }

    @RequestMapping(value = "/settings/password", method = RequestMethod.POST)
    public ModelAndView changePassword(@RequestParam(name = "old-password") String oldPassword,
                                       @RequestParam(name = "new-password") String newPassword) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        modelAndView.addObject("user", user);
        if (user.getPassword().equals(oldPassword)) {
            user.setPassword(newPassword);
            modelAndView.setViewName("manage-account");
            modelAndView.addObject("msg", "Password was successfully changed.");
            modelAndView.addObject("status", "success");
            userService.saveNewUser(user);
        } else {
            modelAndView.setViewName("manage-account");
            modelAndView.addObject("msg", "Your old password incorrect.");
            modelAndView.addObject("status", "error");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/settings")
    public ModelAndView showChangeSettingsPage() {
        ModelAndView modelAndView = new ModelAndView("manage-account");
        User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        modelAndView.addObject("user", user);

        return modelAndView;
    }

    @RequestMapping(value = "/settings", method = RequestMethod.POST)
    public ModelAndView changeSettings(@RequestParam(name = "name") String name,
                                       @RequestParam(name = "surname") String surname,
                                       @RequestParam(name = "birthday") String birthday,
                                       @RequestParam(name = "phone") String phone,
                                       @RequestParam(name = "country") String country,
                                       @RequestParam(name = "city") String city,
                                       @RequestParam(name = "street") String street,
                                       @RequestParam(name = "house") String house,
                                       @RequestParam(name = "apartment") String apartment,
                                       @RequestParam(name = "postcode") String postcode) {
        Address address = new Address(city, country, postcode, street, house, apartment);
        User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        user.setName(name);
        user.setBirthday(birthday);
        user.setSurname(surname);
        user.setAddress(address);
        user.setPhone(phone);
        userService.saveNewUser(user);
        ModelAndView modelAndView = new ModelAndView("manage-account");
        modelAndView.addObject("msg2", "Settings successfully changed.");
        modelAndView.addObject("status2", "success");
        modelAndView.addObject("user", user);
        return modelAndView;
    }
}
