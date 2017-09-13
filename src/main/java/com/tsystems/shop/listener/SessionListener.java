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

/**
 * Session listener. When session is timed out it clears user bag
 * and put all items in database again.
 */
public class SessionListener implements ServletContextListener, HttpSessionListener {

    /**
     * Injected product service which allows us to work with database products.
     */
    @Autowired
    private ProductService productService;

    /**
     * Event when user's session is created.
     */
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        httpSessionEvent.getSession().setMaxInactiveInterval(60*60*2);
    }

    /**
     * Event when user's session must be destroyed.
     * It also happens when user successfully becomes authorised.
     * And our goal is preventing bag clearing in this case(SPRING_SECURITY_SAVED_REQUEST).
     * In other cases we should clear bag and put items back to the database.
     */
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

    /**
     * Spring can't inject any bean into listeners by standard tools.
     * And we should do it to use product service.
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }


    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }

}
