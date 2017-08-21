package com.tsystems.shop.controller;

import com.tsystems.shop.model.Product;
import com.tsystems.shop.service.api.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/advertising")
public class AdvertisingRestController {

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
        return tops;
    }
}
