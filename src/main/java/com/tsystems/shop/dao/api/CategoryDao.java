package com.tsystems.shop.dao.api;


import com.tsystems.shop.model.Category;

import java.util.List;

public interface CategoryDao {
    Category saveNewCategory(Category category);
    List<Category> findCategories();
    Category findCategoryById(String id);
    List<Category> findRootCategories();
    List<Category> findChilds(Category category);
    List<Category> findCategoriesByHierarchyNumber(String hierarchyNumber);
}
