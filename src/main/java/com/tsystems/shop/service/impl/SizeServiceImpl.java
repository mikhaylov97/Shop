package com.tsystems.shop.service.impl;

import com.tsystems.shop.dao.api.ProductDao;
import com.tsystems.shop.model.Product;
import com.tsystems.shop.service.api.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Size service. It is used to size manipulations.
 */
@Service
public class SizeServiceImpl implements SizeService {

    /**
     * Injected dao object which allow us to work with products table in DB.
     */
    private final ProductDao productDao;

    /**
     * Injection constructor.
     * @param productDao - object that must be injected.
     */
    @Autowired
    public SizeServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    /**
     * Method returns available amount of any size by his id.
     *
     * @param sizeId that amount method must return.
     * @return available amount of size;
     */
    @Override
    public int getAvailableAmountOfSize(int sizeId) {
        return productDao.findAvailableAmountOfSize(sizeId);
    }

    /**
     * Method find unique size names in products list.
     *
     * @param products where method will find size names.
     * @return set with unique names.
     */
    @Override
    public Set<String> findSizesFromProducts(List<Product> products) {
        Set<String> sizes = new HashSet<>();
        if (products == null) return sizes;
        products.forEach(p -> p.getAttributes().getSizes()
                .forEach(s -> sizes.add(s.getSize())));
        return sizes;
    }
}
