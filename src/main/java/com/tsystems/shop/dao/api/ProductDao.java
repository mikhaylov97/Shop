package com.tsystems.shop.dao.api;


import com.tsystems.shop.model.Category;
import com.tsystems.shop.model.Product;
import com.tsystems.shop.model.Size;

import java.util.List;

public interface ProductDao {
    List<Product> findAllProducts();
    Product findProductById(long id);
    Product saveProduct(Product product);
    Size findSizeById(long id);
    int findAvailableAmountOfSize(long sizeId);
    List<Product> findProductsByCategory(Category category);
    List<Product> findTop10Products();
    //List<Product> findProductsByCurrentCategory(Category category);
}
