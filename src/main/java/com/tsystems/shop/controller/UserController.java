package com.tsystems.shop.controller;

import com.tsystems.shop.model.dto.BagProductDto;
import com.tsystems.shop.service.api.BagService;
import com.tsystems.shop.service.api.OrderService;
import com.tsystems.shop.service.api.ProductService;
import com.tsystems.shop.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.jms.TextMessage;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Secured controller for authorised user's actions.
 * this controller provide us URL through which user can place an order
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    /**
     * This injected object allow us to send messages to the JMS server
     * without any difficulties through sendMessage() API.
     */
    private final JmsTemplate jmsTemplate;

    /**
     * User service. It is necessary for working with users.
     */
    private final UserService userService;

    /**
     * Product service. It is necessary for working with products.
     */
    private final ProductService productService;

    /**
     * Bag service. It is necessary for working with bag products.
     */
    private final BagService bagService;

    /**
     * Order service. It is necessary for working with orders.
     */
    private final OrderService orderService;

    /**
     * Injecting different services into this controller by spring tools.
     * @param jmsTemplate - give us sendMessage() method to publish messages in ActiveMQ server.
     * @param userService - is our service which provide API to work with users and DB.
     * @param productService is our service which provide API to work with products and DB.
     * @param bagService is our service which provide API to work with bag.
     * @param orderService is our service which provide API to work with orders and DB.
     */
    @Autowired
    public UserController(JmsTemplate jmsTemplate, UserService userService,
                          ProductService productService, BagService bagService,
                          OrderService orderService) {
        this.jmsTemplate = jmsTemplate;
        this.userService = userService;
        this.productService = productService;
        this.bagService = bagService;
        this.orderService = orderService;
    }

    /**
     * This method shows checkout page. For that we need to put all products from users bag
     * to the model.
     * @param session - Since our bag stores in the session we need
     *               this parameter to get our bag products from there and calculate the cost of the bag
     * @return ModelAndView object with checkout.jsp view
     */
    @RequestMapping(value = "/checkout")
    public ModelAndView showCheckoutPage(HttpSession session) {
        //view
        ModelAndView modelAndView = new ModelAndView("checkout");

        //model
        modelAndView.addObject("user",
                userService.findUserFromSecurityContextHolder());
        modelAndView.addObject("bagTotalPrice",
                bagService.calculateTotalPrice((List<BagProductDto>)session.getAttribute("bag")));

        return modelAndView;
    }

    /**
     * POST request. This method create an order. Order contains information of the customer,
     * delivery address, phone number, list of products and order date. All this data is stored
     * in database. User can see his order history in account page. In turn, admins can see orders of every user
     * and change state of orders.
     * @param country where products should be delivered.
     * @param postcode of the region.
     * @param city of delivering.
     * @param house of delivering.
     * @param street of delivering.
     * @param apartment of delivering.
     * @param phone of the customer.
     * @param methodCost - cost of the shipping method.
     * @param session where our bag is.
     * @return String in JSON format with the result of method processing.
     */
    @RequestMapping(value = "/checkout/cash", method = RequestMethod.POST)
    public @ResponseBody String checkoutWithCash(@RequestParam(name = "country") String country,
                                                 @RequestParam(name = "postcode") String postcode,
                                                 @RequestParam(name = "city") String city,
                                                 @RequestParam(name = "house") String house,
                                                 @RequestParam(name = "street") String street,
                                                 @RequestParam(name = "apartment") String apartment,
                                                 @RequestParam(name = "phone") String phone,
                                                 @RequestParam(name = "shipping-method") String methodCost,
                                                 HttpSession session) {
        List<BagProductDto> bag = (List<BagProductDto>) session.getAttribute("bag");
        orderService.saveOrder(methodCost, country, city, street, postcode,
                house, apartment, phone, bag, false);
        bag.clear();

        if(productService.isTopProductsChanged()) {
            try {
                sendMessage();
            } catch (JmsException e) {
                e.printStackTrace();
            }
        }

        return "saved";
    }

    /**
     * POST request. This method create an order. Order contains information of the customer,
     * delivery address, phone number, list of products and order date. All this data is stored
     * in database. User can see his order history in account page. In turn, admins can see orders of every user
     * and change state of orders.
     * @param country where products should be delivered.
     * @param postcode of the region.
     * @param city of delivering.
     * @param house of delivering.
     * @param street of delivering.
     * @param apartment of delivering.
     * @param phone of the customer.
     * @param methodCost - cost of the shipping method.
     * @param session where our bag is.
     * @return String in JSON format with the result of method processing.
     */
    @RequestMapping(value = "/checkout/card", method = RequestMethod.POST)
    public @ResponseBody String checkoutWithCard(@RequestParam(name = "country") String country,
                                         @RequestParam(name = "postcode") String postcode,
                                         @RequestParam(name = "city") String city,
                                         @RequestParam(name = "house") String house,
                                         @RequestParam(name = "street") String street,
                                         @RequestParam(name = "apartment") String apartment,
                                         @RequestParam(name = "phone") String phone,
                                         @RequestParam(name = "shipping-method") String methodCost,
                                         HttpSession session) {
        List<BagProductDto> bag = (List<BagProductDto>) session.getAttribute("bag");
        orderService.saveOrder(methodCost, country, city, street, postcode,
                house, apartment, phone, bag, true);
        bag.clear();

        if(productService.isTopProductsChanged()) {
            try {
                sendMessage();
            } catch (JmsException e) {
                e.printStackTrace();
            }
        }
        return "saved";
    }

    /**
     * This method push message to the ActiveMQ server. When the second application will see this message,
     * it will send GET request http://localhost:8080/advertising/stand (see AdvertisingRestController).
     * @throws JmsException when, for example, don't have connection with JMS server
     */
    private void sendMessage() throws JmsException {
        jmsTemplate.send("advertising.stand", session -> {
            TextMessage msg = session.createTextMessage();
            msg.setText("update");
            return msg;
        });
    }
}
