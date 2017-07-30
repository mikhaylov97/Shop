package com.tsystems.shop.dao.api;


import com.tsystems.shop.model.Product;

import java.util.List;

public interface ProductDao {
    List<Product> findAllProducts();
    Product findProductById(long id);
    Product saveProduct(Product product);
}
