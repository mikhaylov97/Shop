package com.tsystems.shop.service.api;


import com.tsystems.shop.model.Category;
import com.tsystems.shop.model.Product;
import com.tsystems.shop.model.Size;

import java.util.List;

public interface ProductService {
    List<Product> findAllProducts();
    Product findProductById(long id);
    Product saveProduct(Product product);
    Size findSizeById(long id);
    int findAvailableAmountOfSize(long sizeId);
    List<Product> findProductsByCategory(Category category);
    List<Product> findTop10Products();
    boolean isTopProductsChanged();
}
