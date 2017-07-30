package com.tsystems.shop.service.impl;

import com.tsystems.shop.model.Product;
import com.tsystems.shop.service.api.BagService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class BagServiceImpl implements BagService {
    @Override
    public String figureOutTotalPrice(Set<Product> products) {
        long totalPrice = 0;
        for (Product product : products) {
            totalPrice += Long.parseLong(product.getPrice());
        }
        return String.valueOf(totalPrice);
    }
}
