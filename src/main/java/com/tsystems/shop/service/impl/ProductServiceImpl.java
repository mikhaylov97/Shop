package com.tsystems.shop.service.impl;

import com.tsystems.shop.dao.api.CategoryDao;
import com.tsystems.shop.dao.api.ProductDao;
import com.tsystems.shop.model.Category;
import com.tsystems.shop.model.Product;
import com.tsystems.shop.model.Size;
import com.tsystems.shop.service.api.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private List<Product> tops = new ArrayList<>(10);

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

    @Override
    public List<Product> findTop10Products() {
        return productDao.findTop10Products();
    }

    @Override
    @Transactional
    public boolean isTopProductsChanged() {
        if(tops.containsAll(productDao.findTop10Products())) {
            return false;
        } else {
            tops = productDao.findTop10Products();
            return true;
        }
    }

    public List<Product> getTops() {
        return tops;
    }

    public void setTops(List<Product> tops) {
        this.tops = tops;
    }
}
