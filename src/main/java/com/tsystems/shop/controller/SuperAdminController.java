package com.tsystems.shop.controller;

import com.tsystems.shop.model.User;
import com.tsystems.shop.model.enums.UserRoleEnum;
import com.tsystems.shop.service.api.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * Secured controller for super admin actions.
 * Super admin can add or remove admins. Simple admin cannot do it.
 */
@Controller
@RequestMapping(value = "/super/admin")
public class SuperAdminController {

    /**
     * Apache log4j object is used to logging all important info.
     */
    private static final Logger log = Logger.getLogger(SuperAdminController.class);

    /**
     * User service. It is necessary for working with users.
     */
    private final UserService userService;

    /**
     * Injecting users service into this controller by spring tools.
     * @param userService is our service which provide API to work with users and DB
     */
    @Autowired
    public SuperAdminController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Render the html from manage-admins.jsp and send it as a response for users(super admin in this case).
     * @return ModelAndView object with manage-admins.jsp view inside.
     */
    @RequestMapping(value = "/management")
    public ModelAndView showManageAdminsPage() {
        //view
        ModelAndView modelAndView = new ModelAndView("manage-admins");

        //model
        modelAndView.addObject("admins", userService.findSimpleAdmins());

        //log
        User user = userService.findUserFromSecurityContextHolder();
        log.info(user.getEmail() + " with " + user.getRole() + " has visited admin management page.");

        return modelAndView;
    }

    /**
     * POST request. This method tries to save new admin with provided data.
     * @param name - The name of new admin
     * @param surname - The surname of new admin
     * @param email - Email of new admin
     * @param password - Password of new admin (Yes, super admin set password for all admins.
     *                But then they can change it).
     * @return String in JSON format which tell us about the result of operation.
     */
    @RequestMapping(value = "/management/add", method = RequestMethod.POST)
    public @ResponseBody String addNewAdminPostRequest(@RequestParam(name = "name") String name,
                                  @RequestParam(name = "surname") String surname,
                                  @RequestParam(name = "email") String email,
                                  @RequestParam(name = "password") String password) {
        if (!userService.isEmailFree(email)) {
            //log
            User user = userService.findUserFromSecurityContextHolder();
            log.info(user.getEmail() + " with " + user.getRole() + " has tried to add new admin." +
                    " But email is not free.");

            return "declined";
        } else {
            User admin = new User(UserRoleEnum.ROLE_ADMIN.name(), name, surname, email, password);
            userService.saveNewUser(admin);

            //log
            User user = userService.findUserFromSecurityContextHolder();
            log.info(user.getEmail() + " with " + user.getRole() + " has added new admin." +
                    "Email - " + admin.getEmail());

            return "saved";
        }
    }

    /**
     * POST request. Method return list of admins as a fragment of the page.
     * Ajax use this method to update admins list after removing or adding some admin.
     * @return ModelAndView with manage-admins-only-items.jsp view.
     */
    @RequestMapping(value = "/management/get/admins", method = RequestMethod.POST)
    public ModelAndView getAdminsListPostRequest() {
        //view
        ModelAndView modelAndView = new ModelAndView("manage-admins-only-items");

        //model
        modelAndView.addObject("admins", userService.findSimpleAdmins());

        return modelAndView;
    }

    /**
     * POST request. Method allow us (super admin) to remove simple admin by his id.
     * Ajax use this method to remove one or more admins without reloading page.
     * Method return list of admins as a fragment of the manage-admins page.
     * @param id - id of the admin who will be deleted.
     * @return ModelAndView with manage-admins-only-items.jsp view.
     */
    @RequestMapping(value = "/management/delete/{id}", method = RequestMethod.POST)
    public ModelAndView deleteAdminByIdPostRequest(@PathVariable(name = "id") String id) {
        userService.deleteUser(Long.parseLong(id));
        //view
        ModelAndView modelAndView = new ModelAndView("manage-admins-only-items");

        //model
        modelAndView.addObject("admins", userService.findSimpleAdmins());

        //log
        User user = userService.findUserFromSecurityContextHolder();
        log.info(user.getEmail() + " with " + user.getRole() + " has deleted admin.");

        return modelAndView;
    }
}
