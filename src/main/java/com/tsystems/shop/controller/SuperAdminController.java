package com.tsystems.shop.controller;

import com.tsystems.shop.model.User;
import com.tsystems.shop.model.enums.UserRoleEnum;
import com.tsystems.shop.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value = "/super/admin")
public class SuperAdminController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/management")
    public ModelAndView showManageAdminsPage() {
        ModelAndView modelAndView = new ModelAndView("manage-admins");
        List<User> list = userService.findSimpleAdmins();
        modelAndView.addObject("admins", userService.findSimpleAdmins());

        return modelAndView;
    }

    @RequestMapping(value = "/management/add", method = RequestMethod.POST)
    public ModelAndView addNewAdmin(@RequestParam(name = "name") String name,
                                    @RequestParam(name = "surname") String surname,
                                    @RequestParam(name = "email") String email,
                                    @RequestParam(name = "password") String password) {
        ModelAndView modelAndView = new ModelAndView("manage-admins");
        if (!userService.isEmailFree(email)) {
            modelAndView.addObject("errorMsg", "User with such email already exists");
            modelAndView.addObject("admins", userService.findSimpleAdmins());
            modelAndView.addObject("name", name);
            modelAndView.addObject("surname", surname);
            modelAndView.addObject("email", email);
            return modelAndView;
        } else {
            User admin = new User(UserRoleEnum.ROLE_ADMIN.name(), name, surname, email, password);
            userService.saveNewUser(admin);
            modelAndView.addObject("successMsg", "New admin was successfully added");
            modelAndView.addObject("admins", userService.findSimpleAdmins());
            return modelAndView;
        }
    }

    @RequestMapping(value = "/management/add/ajax", method = RequestMethod.POST)
    @ResponseBody
    public String addNewAdminAjax(@RequestParam(name = "name") String name,
                                  @RequestParam(name = "surname") String surname,
                                  @RequestParam(name = "email") String email,
                                  @RequestParam(name = "password") String password) {
        if (!userService.isEmailFree(email)) {
            return "declined";
        } else {
            User admin = new User(UserRoleEnum.ROLE_ADMIN.name(), name, surname, email, password);
            userService.saveNewUser(admin);
            return "saved";
        }
    }

    @RequestMapping(value = "/management/get/admins", method = RequestMethod.POST)
    public ModelAndView getAdminsList() {
        ModelAndView modelAndView = new ModelAndView("admins-list");
        modelAndView.addObject("admins", userService.findSimpleAdmins());
        return modelAndView;
    }

    @RequestMapping(value = "/management/delete/{id}", method = RequestMethod.POST)
    public ModelAndView deleteAdminById(@PathVariable(name = "id") String id) {
        userService.deleteUser(Long.parseLong(id));
        ModelAndView modelAndView = new ModelAndView("admins-list");
        modelAndView.addObject("admins", userService.findSimpleAdmins());

        return modelAndView;
    }
}
