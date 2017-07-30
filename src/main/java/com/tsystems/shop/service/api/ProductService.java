package com.tsystems.shop.service.api;


import com.tsystems.shop.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAllProducts();
    Product findProductById(long id);
    Product saveProduct(Product product);
}
