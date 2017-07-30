package com.tsystems.shop.service.api;


import com.tsystems.shop.model.Product;

import java.util.Set;

public interface BagService {
    String figureOutTotalPrice(Set<Product> products);
}
