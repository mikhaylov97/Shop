package com.tsystems.shop.service.api;


import com.tsystems.shop.model.Product;

import java.util.List;
import java.util.Set;

/**
 * Interface provide us API we can use to manipulate on product size.
 */
public interface SizeService {
    /**
     * Method returns available amount of any size by his id.
     * @param sizeId that amount method must return.
     * @return available amount of size;
     */
    int getAvaiableAmountOfSize(int sizeId);

    /**
     * Method find unique size names in products list.
     * @param products where method will find size names.
     * @return set with unique names.
     */
    Set<String> findSizesFromProducts(List<Product> products);
}
