package com.tsystems.shop.service.impl;


import com.tsystems.shop.dao.api.CategoryDao;
import com.tsystems.shop.model.Category;
import com.tsystems.shop.service.api.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;
    @Override
    public List<Category> findCategories() {
        return categoryDao.findCategories();
    }

    @Override
    public Category findCategoryById(String id) {
        return categoryDao.findCategoryById(id);
    }
}
