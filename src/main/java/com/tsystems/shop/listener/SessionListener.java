package com.tsystems.shop.listener;


import com.tsystems.shop.model.Product;
import com.tsystems.shop.model.Size;
import com.tsystems.shop.model.dto.BagProductDto;
import com.tsystems.shop.service.api.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.List;
import java.util.Set;

public class SessionListener implements ServletContextListener, HttpSessionListener {

    @Autowired
    private ProductService productService;

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        httpSessionEvent.getSession().setMaxInactiveInterval(60*60*2);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        List<BagProductDto> bag = (List<BagProductDto>) httpSessionEvent.getSession().getAttribute("bag");
        if (httpSessionEvent.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST") != null) return;
        for (BagProductDto productDto : bag) {
            Product product = productService.findProductById(productDto.getId(), true);
            Set<Size> sizeSet = product.getAttributes().getSizes();
            for (Size size : sizeSet) {
                if (size.getId() == productDto.getSizeId()) {
                    size.setAvailableNumber(String.valueOf(Long.parseLong(size.getAvailableNumber())
                            + productDto.getAmount()));
                }
            }
            productService.saveProduct(product);
        }
        bag.clear();
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }

}
