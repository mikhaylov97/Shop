package com.tsystems.shop.service.api;


import com.tsystems.shop.model.Category;
import com.tsystems.shop.model.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    Category saveNewCategory(Category category);
    List<Category> findCategories(boolean adminMode);
    Category findCategoryById(String id, boolean adminMode);
    List<Category> findRootCategories();
    List<Category> findChilds(Category category, boolean adminMode);
    List<Category> findCategoriesByHierarchyNumber(String hierarchyNumber, boolean adminMode);
    CategoryDto convertCategoryToCategoryDto(Category category);
    List<CategoryDto> convertCategoriesToCategoriesDto(List<Category> categories);
    List<CategoryDto> findChildsDtoByCategory(Category category, boolean adminMode);
    List<CategoryDto> findChildsDtoById(long id, boolean adminMode);
    long calculateNumberOfSales(long id);
    boolean checkIsCategoryNameFree(String name, Category parent);
    void hideCategory(Category category);
    void showCategory(Category category);
    List<Category> ascendingSortCategoriesById(List<Category> categories);
}
