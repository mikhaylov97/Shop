package com.tsystems.shop.service.api;


import com.tsystems.shop.model.Category;
import com.tsystems.shop.model.dto.CategoryDto;

import java.util.List;

/**
 * Interface provide us API we can use to manipulate categories.
 */
public interface CategoryService {

    /**
     * Find root categories.
     * @return list with root categories(By default - MEN'S and WOMEN'S objects)
     */
    List<Category> findRootCategories();

    /**
     * Calculates number of sales of the category by id value.
     * @param id of the category
     * @return number of sales
     */
    long calculateNumberOfSales(long id);

    /**
     * Hide category all objects inside.
     * @param category that must be hidden
     */
    void hideCategory(Category category);

    /**
     * Activates the category and objects inside.
     * @param category that must be activated.
     */
    void showCategory(Category category);

    /**
     * Add new category object to the database.
     * @param category that must be added.
     * @return reference to saved object.
     */
    Category saveNewCategory(Category category);

    /**
     * Find all categories. If adminMode is true method will return all categories,
     * even hidden. Otherwise it will return only active one.
     * @param adminMode see above.
     * @return list with found categories.
     */
    List<Category> findCategories(boolean adminMode);

    /**
     * Find category by ID. If adminMode is true method will return found category.
     * If this category is hidden and adminMode is false method will return null.
     * @param id of category that must be found.
     * @param adminMode see above.
     * @return found category object or null.
     */
    Category findCategoryById(String id, boolean adminMode);

    /**
     * Convert Category {@link Category} object to CategoryDto object {@link CategoryDto}
     * @param category that must be converted.
     * @return CategoryDto object - result of the converting.
     */
    CategoryDto convertCategoryToCategoryDto(Category category);

    /**
     * Method checks if category name is free.
     * @param name that must be checked.
     * @param parent category. It is necessary because in different root categories
     * names may overlaps.
     * @return result - true if name is free or false otherwise.
     */
    boolean checkIsCategoryNameFree(String name, Category parent);

    /**
     * Method finds all child categories. If adminMode is true method will return
     * all child categories, otherwise only active child categories should be found.
     * @param id of parent category.
     * @param adminMode see above.
     * @return list of found categories in dto format.
     */
    List<CategoryDto> findChildsDtoById(long id, boolean adminMode);

    /**
     * Method finds all child categories. If adminMode is true method will return
     * all child categories, otherwise only active child categories should be found.
     * @param category object which childs must be found.
     * @param adminMode see above.
     * @return list of found categories.
     */
    List<Category> findChilds(Category category, boolean adminMode);

    /**
     * Method sort ascending given list of categories.
     * @param categories given categories.
     * @return sorted list.
     */
    List<Category> ascendingSortCategoriesById(List<Category> categories);

    /**
     * Convert Category list{@link Category} to CategoryDto list {@link CategoryDto}
     * @param categories that must be converted.
     * @return CategoryDto list - result of the converting.
     */
    List<CategoryDto> convertCategoriesToCategoriesDto(List<Category> categories);

    /**
     * Method finds all child categories. If adminMode is true method will return
     * all child categories, otherwise only active child categories should be found.
     * @param category object which childs must be found.
     * @param adminMode see above.
     * @return list of found categories in dto format.
     */
    List<CategoryDto> findChildsDtoByCategory(Category category, boolean adminMode);

    /**
     * Method finds categories by certain hierarchy number. If adminMode is true,
     * method will return all found categories, otherwise only active found categories must be returned.
     * @param hierarchyNumber - products with this hierarchy number must be found.
     * @param adminMode see above.
     * @return list of found categories.
     */
    List<Category> findCategoriesByHierarchyNumber(String hierarchyNumber, boolean adminMode);
}
