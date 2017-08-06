package com.tsystems.shop.service.impl;

import com.tsystems.shop.dao.api.CategoryDao;
import com.tsystems.shop.dao.api.ProductDao;
import com.tsystems.shop.model.Category;
import com.tsystems.shop.model.Product;
import com.tsystems.shop.model.Size;
import com.tsystems.shop.service.api.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public List<Product> findAllProducts() {
        return productDao.findAllProducts();
    }

    @Override
    public Product findProductById(long id) {
        return productDao.findProductById(id);
    }

    @Override
    public Product saveProduct(Product product) {
        return productDao.saveProduct(product);
    }

    @Override
    public Size findSizeById(long id) {
       return productDao.findSizeById(id);
    }

    @Override
    public int findAvailableAmountOfSize(long sizeId) {
        return productDao.findAvailableAmountOfSize(sizeId);
    }

    @Override
    public List<Product> findProductsByCategory(Category category) {
        List<Product> products = new ArrayList<>();
        List<Category> childs = categoryDao.findChilds(category);
        if (childs != null) {
            for (Category child : childs) {
                products.addAll(findProductsByCategory(child));
            }
        }
        products.addAll(productDao.findProductsByCategory(category));
        return products;
    }
}
