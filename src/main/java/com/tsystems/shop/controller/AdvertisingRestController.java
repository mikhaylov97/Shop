package com.tsystems.shop.controller;

import com.tsystems.shop.model.dto.ProductDto;
import com.tsystems.shop.service.api.ProductService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * The rest controller who is responsible for advertising.
 * It contains emitters of all users which is working with
 * the advertising stand(Second application on 8081 port).
 */
@RestController
@RequestMapping(value = "/advertising")
public class AdvertisingRestController {

    /**
     * Apache log4j object is used to logging all important info.
     */
    private static final Logger log = Logger.getLogger(AdvertisingRestController.class);

    /**
     * Set of the emitters. It is used to send messages for all active users.
     * The message on the client side will refresh the page of the stand.
     */
    private final Set<SseEmitter> emitters = Collections.synchronizedSet(new HashSet<>());

    /**
     * Product service. It is necessary for working with products.
     */
    private final ProductService productService;

    /**
     * Injecting product service into this controller by spring tools.
     * @param productService is our service which provide API to work with products and DB
     */
    @Autowired
    public AdvertisingRestController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * By this URL we can take list of the top product in JSON format.
     * This method also call sendMessageToUpdate() to notify all browsers about changing
     * of top products list. See {@link #sendNotificationForAllSubscribers()} ()}
     * @return List of top products.
     */
    @RequestMapping(value = "/stand")
    public List<Map<String, String>> getStandInformation() {
        List<ProductDto> list = productService.findTop10ProductsDto(false);
        List<Map<String, String>> tops = new ArrayList<>();
        for (ProductDto product : list) {
            Map<String, String> item = new HashMap<>();
            item.put("id", String.valueOf(product.getId()));
            item.put("name", product.getName());
            item.put("price", product.getPrice());
            item.put("numberOfSales", String.valueOf(product.getNumberOfSales()));
            tops.add(item);
        }
        new Timer(true).schedule(new TimerTask() {
            @Override
            public void run() {
                sendNotificationForAllSubscribers();
            }
        }, 10000);

        //log
        log.info("Someone has requested stand information. Application is returning list of top products.");

        return tops;
    }


    /**
     * By this method, all users who have visited advertising stand subscribe for
     * notifications from this application. In our case, when our top have changed or
     * any admin change one of the top products, we send message to the JMS server.
     * Our second application always listen to this JMS server and when it sees
     * messages in queue, it try to update tops by available URL (see {@link #getStandInformation}).
     * After that current application send notifications for all subscribers (browsers or something else)
     * to refresh their pages and to see updated top products.
     * @param response - no comments
     * @return new exemplar of emitter
     */
    @RequestMapping(value = "/stand/connection")
    public SseEmitter openConnection(HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
        final SseEmitter emitter = new SseEmitter(20000L);

        emitter.onTimeout(emitter::complete);
        emitter.onCompletion(() -> {
            synchronized (this.emitters) {
                emitters.remove(emitter);
            }
        });
        emitters.add(emitter);

        return emitter;
    }

    /**
     * URL which allow us to send notification for all subscribed users to refresh their browsers.
     * It is needed only to demonstrate how emitters work.
     */
    @RequestMapping(value = "/stand/update")
    private void sendNotificationForAllSubscribers() {
        synchronized (this.emitters) {
            for (SseEmitter emitter : emitters) {
                try {
                    emitter.send("update");
                    emitter.complete();
                } catch (Exception ignored) {
                    //we should ignore this exception
                    //because sometimes we have two similar connections to one user.
                    //One of them complete(timeout), another is active.
                    //After this catch block, completed exemplar will be removed.
                }
            }
        }
        //log
        log.info("All stand viewers have received message to refresh page in browser.");

    }

}
