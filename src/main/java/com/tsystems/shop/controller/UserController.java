package com.tsystems.shop.controller;

import com.tsystems.shop.model.Address;
import com.tsystems.shop.model.BagProduct;
import com.tsystems.shop.model.Payment;
import com.tsystems.shop.model.User;
import com.tsystems.shop.model.enums.OrderStatusEnum;
import com.tsystems.shop.model.enums.PaymentStatusEnum;
import com.tsystems.shop.model.enums.PaymentTypeEnum;
import com.tsystems.shop.service.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.jms.TextMessage;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private BagService bagService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

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

//    @RequestMapping(value = "/ordering")
//    public ModelAndView showOrderingPage(HttpServletRequest request) {
//        ModelAndView modelAndView = new ModelAndView("orderingTest");
//        Set<Product> products = (Set<Product>) request.getSession().getAttribute("bag");
//        modelAndView.addObject("bag", products);
//        modelAndView.addObject("paymentTypes", paymentService.getPaymentTypes());
//        //modelAndView.addObject("totalPrice", bagService.figureOutTotalPrice(products));
//
//        return modelAndView;
//    }

    @RequestMapping(value = "/history")
    public ModelAndView showHistoryPage() {
        ModelAndView modelAndView = new ModelAndView("historyTest");
        modelAndView.addObject("orders",
                orderService.findOrdersByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));


        return modelAndView;
    }

    @RequestMapping(value = "/checkout")
    public ModelAndView showCheckoutPage(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("checkout");
        modelAndView.addObject("user",
                userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
        modelAndView.addObject("bagTotalPrice", bagService.figureOutTotalPrice((List<BagProduct>)session.getAttribute("bag")));

        return modelAndView;
    }

    @RequestMapping(value = "/checkout/cash", method = RequestMethod.POST)
    public ModelAndView checkoutWithCash(@RequestParam(name = "country") String country,
                                         @RequestParam(name = "postcode") String postcode,
                                         @RequestParam(name = "city") String city,
                                         @RequestParam(name = "house") String house,
                                         @RequestParam(name = "street") String street,
                                         @RequestParam(name = "apartment") String apartment,
                                         @RequestParam(name = "phone") String phone,
                                         @RequestParam(name = "shipping-method") String methodCost,
                                         HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("checkout");
        modelAndView.addObject("successMsg", "Order successfully completed");

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        String date = dtf.format(now);

        String address = country + ", " + city + " (" + postcode + "), " + street + " " + house + ", " + apartment;
        String totalPrice = String.valueOf(Long.parseLong(bagService.figureOutTotalPrice((List<BagProduct>)session.getAttribute("bag")))
                + Long.parseLong(methodCost));
        Payment payment = new Payment(PaymentTypeEnum.CASH.name(), totalPrice, methodCost, PaymentStatusEnum.AWAITING_PAYMENT.name());
        User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        orderService.addNewOrder(address, OrderStatusEnum.AWAITING_SHIPMENT.name(), user,
                payment, date, phone, (List<BagProduct>)session.getAttribute("bag"));

        ((List<BagProduct>)session.getAttribute("bag")).clear();

        if(productService.isTopProductsChanged()) sendMessage("advertising.stand", "update");

        return modelAndView;
    }

    @RequestMapping(value = "/checkout/card", method = RequestMethod.POST)
    public ModelAndView checkoutWithCard(@RequestParam(name = "country") String country,
                                         @RequestParam(name = "postcode") String postcode,
                                         @RequestParam(name = "city") String city,
                                         @RequestParam(name = "house") String house,
                                         @RequestParam(name = "street") String street,
                                         @RequestParam(name = "apartment") String apartment,
                                         @RequestParam(name = "phone") String phone,
                                         @RequestParam(name = "shipping-method") String methodCost,
                                         HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("checkout");
        modelAndView.addObject("successMsg", "Order successfully completed");

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        String date = dtf.format(now);

        String address = country + ", " + city + " (" + postcode + "), " + street + " " + house + ", " + apartment;
        String totalPrice = String.valueOf(Long.parseLong(bagService.figureOutTotalPrice((List<BagProduct>)session.getAttribute("bag")))
                + Long.parseLong(methodCost));
        Payment payment = new Payment(PaymentTypeEnum.CASH.name(), totalPrice, methodCost, PaymentStatusEnum.AWAITING_PAYMENT.name());
        User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        orderService.addNewOrder(address, OrderStatusEnum.AWAITING_SHIPMENT.name(), user,
                payment, date, phone, (List<BagProduct>)session.getAttribute("bag"));

        ((List<BagProduct>)session.getAttribute("bag")).clear();

        return modelAndView;
    }

    public void sendMessage(final String queueName, final String message) {
        jmsTemplate.send(queueName, session -> {
            TextMessage msg = session.createTextMessage();
            msg.setText(message);
            return msg;
        });
    }
}
