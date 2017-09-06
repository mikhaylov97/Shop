package com.tsystems.shop.service.api;


import com.tsystems.shop.model.Product;
import com.tsystems.shop.model.Size;

import java.util.List;
import java.util.Set;

public interface SizeService {
    Set<Size> parseString(String sizes);
    int getAvaiableAmountOfSize(int sizeId);
    Set<String> findSizesFromProducts(List<Product> products);
}
