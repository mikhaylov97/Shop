package com.tsystems.shop.controller;

import com.tsystems.shop.exception.IncorrectAccountInfoException;
import com.tsystems.shop.exception.IncorrectPasswordException;
import com.tsystems.shop.model.Address;
import com.tsystems.shop.service.api.OrderService;
import com.tsystems.shop.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Secured controller for admins and authorised users actions.
 * There they can change their own account settings (Name, surname, address data, phone, password).
 * Users also can see their order history.
 * Admins instead of this have a list of different actions:
 * 1) Manage categories.
 * 2) Manage orders.
 * 3) Statistics.
 * 4) Add product.
 * Super admin has both of simple admin and super admin roles. He has access for actions
 * above and also has option - manage admins.
 */
@Controller
@RequestMapping(value = "/account")
public class AccountController {

    /**
     * User service. It is necessary for working with users.
     */
    private final UserService userService;

    /**
     * Order service. It is necessary for working with orders.
     */
    private final OrderService orderService;

    /**
     * Injecting users service into this controller by spring tools.
     * @param userService is our service which provide API to work with users and DB
     * @param orderService is our service which provide API to work with orders and DB
     */
    @Autowired
    public AccountController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    /**
     * This Method show account page. It returns put into model orders list (it's empty for admins)
     * and user personal information.
     * @return ModelAndView object with account.jsp inside.
     */
    @RequestMapping(value = "")
    public ModelAndView showAccountPage() {
        //view
        ModelAndView modelAndView = new ModelAndView("account");

        //model
        modelAndView.addObject("user",
                userService.findUserFromSecurityContextHolder());
        modelAndView.addObject("orders",
                orderService.findOrdersByEmail(userService.findUserEmailFromSecurityContextHolder()));

        return modelAndView;
    }

    /**
     * POST request. Method tries to change user password if it's possible.
     * It compares old password with current password. If they are same it change the
     * account password to new received from user.
     * @param oldPassword - old account password. User shows that he knows current password.
     * @param newPassword - suggested by user password we need to set if typed old password is correct.
     * @return String in JSON format with result of method processing
     */
    @RequestMapping(value = "/settings/password", method = RequestMethod.POST)
    public @ResponseBody String changePasswordPostRequest(@RequestParam(name = "old-password") String oldPassword,
                                                          @RequestParam(name = "new-password") String newPassword) {
        try {
            userService.changePasswordFromSecurityContextHolder(oldPassword, newPassword);
        } catch (IncorrectPasswordException e) {
            return e.getMessage();
        }
        return "saved";
    }

    /**
     * Method shows settings page where user can change his personal information such as
     * Name, Surname, phone, address info, birthday and set new password.
     * @return ModelAndView with manage-account.jsp view inside.
     */
    @RequestMapping(value = "/settings")
    public ModelAndView showChangeSettingsPage() {
        //view
        ModelAndView modelAndView = new ModelAndView("manage-account");

        //model
        modelAndView.addObject("user",
                userService.findUserFromSecurityContextHolder());

        return modelAndView;
    }

    /**
     * POST request. Method change personal user data. List of user attributes you can see below.
     * @param name - User's name
     * @param surname - User's surname
     * @param birthday - User's birthday
     * @param phone - User's phone number
     * Address parameters:
     * @param country - User's country
     * @param city - User's city
     * @param street - User's street
     * @param house - User's house number
     * @param apartment - User's apartment number
     * @param postcode - User's region postcode
     * @return String in JSON format with the result of method processing
     */
    @RequestMapping(value = "/settings", method = RequestMethod.POST)
    public @ResponseBody String changeSettingsPostRequest(@RequestParam(name = "name") String name,
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
        try {
            userService.changeInformationFromSecurityContextHolder(phone, birthday, name, surname, address);
        } catch (IncorrectAccountInfoException e) {
            return e.getMessage();
        }
        return "saved";
    }
}
