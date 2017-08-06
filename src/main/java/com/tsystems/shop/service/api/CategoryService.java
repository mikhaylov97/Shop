package com.tsystems.shop.service.api;


import com.tsystems.shop.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findCategories();
    Category findCategoryById(String id);
    List<Category> findRootCategories();
    List<Category> findChilds(Category category);
    List<Category> findCategoriesByHierarchyNumber(String hierarchyNumber);
}
