package com.tsystems.shop.service.impl;


import com.tsystems.shop.dao.api.CategoryDao;
import com.tsystems.shop.dao.api.ProductDao;
import com.tsystems.shop.model.Category;
import com.tsystems.shop.model.dto.CategoryDto;
import com.tsystems.shop.service.api.CategoryService;
import com.tsystems.shop.util.ComparatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Category service. It is used to category manipulations.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    /**
     * Injected by spring categoryDao bean
     */
    private final CategoryDao categoryDao;

    /**
     * Injected by spring productDao bean
     */
    private final ProductDao productDao;

    /**
     * Injecting constructor
     * @param categoryDao - that must be injected.
     * @param productDao - that must be injected.
     */
    @Autowired
    public CategoryServiceImpl(CategoryDao categoryDao, ProductDao productDao) {
        this.categoryDao = categoryDao;
        this.productDao = productDao;
    }

    /**
     * Add new category object to the database.
     *
     * @param category that must be added.
     * @return reference to saved object.
     */
    @Override
    public Category saveNewCategory(Category category) {
        if (category == null) return null;
        return categoryDao.saveCategory(category);
    }

    /**
     * Find all categories. If adminMode is true method will return all categories,
     * even hidden. Otherwise it will return only active one.
     *
     * @param adminMode see above.
     * @return list with found categories.
     */
    @Override
    public List<Category> findCategories(boolean adminMode) {
        if (adminMode) return categoryDao.findCategories();
        else return ascendingSortCategoriesById(categoryDao
                .findCategories()
                .stream()
                .filter(Category::getActive)
                .collect(Collectors.toList()));
    }

    /**
     * Find category by ID. If adminMode is true method will return found category.
     * If this category is hidden and adminMode is false method will return null.
     *
     * @param id        of category that must be found.
     * @param adminMode see above.
     * @return found category object or null.
     */
    @Override
    public Category findCategoryById(String id, boolean adminMode) {
        Category category = categoryDao.findCategoryById(id);
        if (adminMode) return category;
        else return category.getActive() ? category : null;
    }

    /**
     * Find root categories.
     *
     * @return list with root categories(By default - MEN'S and WOMEN'S objects)
     */
    @Override
    public List<Category> findRootCategories() {
        return categoryDao.findRootCategories();
    }

    /**
     * Method finds all child categories. If adminMode is true method will return
     * all child categories, otherwise only active child categories should be found.
     *
     * @param category  object which childs must be found.
     * @param adminMode see above.
     * @return list of found categories.
     */
    @Override
    public List<Category> findChilds(Category category, boolean adminMode) {
        if (adminMode) return ascendingSortCategoriesById(categoryDao.findChilds(category));
        else return ascendingSortCategoriesById(categoryDao
                .findChilds(category)
                .stream()
                .filter(Category::getActive)
                .collect(Collectors.toList()));
    }

    /**
     * Method finds categories by certain hierarchy number. If adminMode is true,
     * method will return all found categories, otherwise only active found categories must be returned.
     *
     * @param hierarchyNumber - products with this hierarchy number must be found.
     * @param adminMode       see above.
     * @return list of found categories.
     */
    @Override
    public List<Category> findCategoriesByHierarchyNumber(String hierarchyNumber, boolean adminMode) {
        if (adminMode) return ascendingSortCategoriesById(categoryDao.findCategoriesByHierarchyNumber(hierarchyNumber));
        else return ascendingSortCategoriesById(categoryDao
                .findCategoriesByHierarchyNumber(hierarchyNumber)
                .stream()
                .filter(Category::getActive)
                .collect(Collectors.toList()));
    }

    /**
     * Convert Category {@link Category} object to CategoryDto object {@link CategoryDto}
     *
     * @param category that must be converted.
     * @return CategoryDto object - result of the converting.
     */
    @Override
    public CategoryDto convertCategoryToCategoryDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setHierarchyNumber(Integer.parseInt(category.getHierarchyNumber()));
        categoryDto.setParent(category.getParent());
        categoryDto.setNumberOfSales(calculateNumberOfSales(category.getId()));
        categoryDto.setNumberOfProducts(productDao.findProductsByCategory(category).size());
        categoryDto.setActive(category.getActive());
        return categoryDto;
    }

    /**
     * Convert Category list{@link Category} to CategoryDto list {@link CategoryDto}
     *
     * @param categories that must be converted.
     * @return CategoryDto list - result of the converting.
     */
    @Override
    public List<CategoryDto> convertCategoriesToCategoriesDto(List<Category> categories) {
        return categories
                .stream()
                .map(this::convertCategoryToCategoryDto)
                .collect(Collectors.toList());
    }

    /**
     * Method finds all child categories. If adminMode is true method will return
     * all child categories, otherwise only active child categories should be found.
     *
     * @param category  object which childs must be found.
     * @param adminMode see above.
     * @return list of found categories in dto format.
     */
    @Override
    public List<CategoryDto> findChildsDtoByCategory(Category category, boolean adminMode) {
        return convertCategoriesToCategoriesDto(findChilds(category, adminMode));
    }

    /**
     * Method finds all child categories. If adminMode is true method will return
     * all child categories, otherwise only active child categories should be found.
     *
     * @param id        of parent category.
     * @param adminMode see above.
     * @return list of found categories in dto format.
     */
    @Override
    public List<CategoryDto> findChildsDtoById(long id, boolean adminMode) {
        return convertCategoriesToCategoriesDto(findChilds(findCategoryById(String.valueOf(id), adminMode), adminMode));
    }

    /**
     * Calculates number of sales of the category by id value.
     *
     * @param id of the category
     * @return number of sales
     */
    @Override
    public long calculateNumberOfSales(long id) {
        Category category = categoryDao.findCategoryById(String.valueOf(id));
        return productDao.findProductsByCategory(category)
                .stream()
                .mapToLong(p -> productDao.findTotalSalesById(p.getId()))
                .sum();
    }

    /**
     * Method checks if category name is free.
     *
     * @param name   that must be checked.
     * @param parent category. It is necessary because in different root categories
     *               names may overlaps.
     * @return result - true if name is free or false otherwise.
     */
    @Override
    public boolean checkIsCategoryNameFree(String name, Category parent) {
        return findChilds(parent, true)
                .stream()
                .filter(c -> c.getName().equalsIgnoreCase(name))
                .count() == 0;
    }

    /**
     * Hide category all objects inside.
     *
     * @param category that must be hidden
     */
    @Override
    public void hideCategory(Category category) {
        productDao.findProductsByCategory(category)
                .forEach(p -> {
                    p.setActive(false);
                    productDao.saveProduct(p);
                });
        category.setActive(false);
        categoryDao.saveCategory(category);
    }

    /**
     * Activates the category and objects inside.
     *
     * @param category that must be activated.
     */
    @Override
    public void showCategory(Category category) {
        productDao.findProductsByCategory(category)
                .forEach(p -> {
                    p.setActive(true);
                    productDao.saveProduct(p);
                });
        category.setActive(true);
        categoryDao.saveCategory(category);
    }

    /**
     * Method sort ascending given list of categories.
     *
     * @param categories given categories.
     * @return sorted list.
     */
    @Override
    public List<Category> ascendingSortCategoriesById(List<Category> categories) {
        return categories
                .stream()
                .sorted(ComparatorUtil.getAscendingCategoryComparator())
                .collect(Collectors.toList());
    }
}
