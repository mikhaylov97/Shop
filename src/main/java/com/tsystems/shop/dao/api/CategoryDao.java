package com.tsystems.shop.dao.api;


import com.tsystems.shop.model.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> findCategories();
    Category findCategoryById(String id);
}
