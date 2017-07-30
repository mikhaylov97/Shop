package com.tsystems.shop.service.impl;

import com.tsystems.shop.dao.api.ProductDao;
import com.tsystems.shop.model.Product;
import com.tsystems.shop.service.api.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public List<Product> findAllProducts() {
        return productDao.findAllProducts();
    }

    @Override
    public Product findProductById(long id) {
        return productDao.findProductById(id);
    }
}
