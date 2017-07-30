package com.tsystems.shop.controller;

import com.tsystems.shop.model.Product;
import com.tsystems.shop.model.User;
import com.tsystems.shop.service.api.ProductService;
import com.tsystems.shop.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
//@SessionAttributes(value = "bag")
public class CommonController {

    @Autowired
    private
    UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

//    @ModelAttribute("bag")
//    List<Product> createBag() { return new ArrayList<>(); }

    @RequestMapping(value = "/")
    public String redirectToHomePage() {
        return "redirect:/home";
    }

    @RequestMapping(value = "/home")
    public ModelAndView showHomePage() {
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("products", productService.findAllProducts());
        return modelAndView;
    }

    @RequestMapping(value = "/login")
    public String showLoginPage() {
        if (isCurrentAuthenticationAnonymous()) return "loginTest";
        else return "redirect:/home";
    }

    @RequestMapping(value = "/signUp")
    public String showSignUpPage() {
        return "registrationTest";
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public String signUp(@RequestParam(name = "email") String email,
                         @RequestParam(name = "name") String name,
                         @RequestParam(name = "surname") String surname,
                         @RequestParam(name = "password") String password,
                         HttpServletRequest request) {
        User user = new User(email, name, surname, password);
        userService.saveNewUser(user);
        authenticateUserAndSetSession(email, password, request);
        return "redirect:/home";
    }


    //    @RequestMapping(value = "/addToBag/{id}")
//    public String addToBag(@PathVariable(name = "id") String id, @ModelAttribute("bag") List<Product> products) {
//        Product product = productService.findProductById(Long.parseLong(id));
//        products.add(product);
//        return "redirect:/home";
//    }
    @RequestMapping(value = "/addToBag/{id}")
    public String addToBag(@PathVariable(name = "id") String id, HttpServletRequest request) {
        Product product = productService.findProductById(Long.parseLong(id));
        Object bag = request.getSession().getAttribute("bag");
        if (bag != null) {
            ((List<Product>) bag).add(product);
        } else {
            List<Product> products = new ArrayList<>();
            products.add(product);
            request.getSession().setAttribute("bag", products);
        }
        return "redirect:/home";
    }

//    @RequestMapping(value = "/bag")
//    public ModelAndView showBagPage(@ModelAttribute("bag")List<Product> products) {
//        ModelAndView modelAndView = new ModelAndView("bagTest");
//        modelAndView.addObject("bag", products);
//        return modelAndView;
//    }

    @RequestMapping(value = "/bag")
    public ModelAndView showBagPage(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("bagTest");
        modelAndView.addObject("bag", request.getSession().getAttribute("bag"));
        return modelAndView;
    }

    private void authenticateUserAndSetSession(String email, String password, HttpServletRequest request) {
        // generate session if one doesn't exist
        request.getSession();

        UserDetails user = userDetailsService.loadUserByUsername(email);
        Authentication authenticatedUser = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
    }

    private boolean isCurrentAuthenticationAnonymous() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication instanceof AnonymousAuthenticationToken;
    }
}
