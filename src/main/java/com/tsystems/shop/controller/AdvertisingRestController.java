package com.tsystems.shop.controller;

import com.tsystems.shop.model.Product;
import com.tsystems.shop.service.api.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping(value = "/advertising")
public class AdvertisingRestController {

    private final Set<SseEmitter> emitters = Collections.synchronizedSet(new HashSet<>());
    //private Map<Long, SseEmitter> emitters1 = new HashMap<>();

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/stand")
    public List<Map<String, String>> getStandInformation() {
        List<Product> list = productService.findTop10Products();
        List<Map<String, String>> tops = new ArrayList<>();
        for (Product product : list) {
            Map<String, String> item = new HashMap<>();
            item.put("id", String.valueOf(product.getId()));
            item.put("name", product.getName());
            item.put("price", product.getPrice());
            tops.add(item);
        }
        sendMessageToUpdate();
        return tops;
    }



    @RequestMapping(value = "/stand/connection")
    public SseEmitter openConnection(HttpServletResponse response) {
        //response.setContentType("text/event-stream");
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

//        if (emitters1.isEmpty()) {
//            emitters1.put(1L, new SseEmitter(50000L));
//        }
//
//        return emitters1.get(1L);
    }

    @RequestMapping(value = "/stand/update")
    private void sendMessageToUpdate() {
        synchronized (this.emitters) {
            for (SseEmitter emitter : emitters) {
                try {
                    emitter.send("update");
                    emitter.complete();
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
        }
    }

}
