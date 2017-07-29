package com.tsystems.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CommonController {

    @RequestMapping(value = "/")
    public String redirectToHomePage() {
        return "redirect:/home";
    }

    @RequestMapping(value = "/home")
    public ModelAndView showHomePage() {
        ModelAndView modelAndView = new ModelAndView("home");
        return modelAndView;
    }
}
