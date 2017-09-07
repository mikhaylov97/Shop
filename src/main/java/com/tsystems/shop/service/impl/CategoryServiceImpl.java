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

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ProductDao productDao;

    @Override
    public Category saveNewCategory(Category category) {
        return categoryDao.saveNewCategory(category);
    }

    @Override
    public List<Category> findCategories(boolean adminMode) {
        if (adminMode) return categoryDao.findCategories();
        else return ascendingSortCategoriesById(categoryDao
                .findCategories()
                .stream()
                .filter(Category::getActive)
                .collect(Collectors.toList()));
    }

    @Override
    public Category findCategoryById(String id, boolean adminMode) {
        Category category = categoryDao.findCategoryById(id);
        if (adminMode) return category;
        else return category.getActive() ? category : null;
    }

    @Override
    public List<Category> findRootCategories() {
       return categoryDao.findRootCategories();
    }

    @Override
    public List<Category> findChilds(Category category, boolean adminMode) {
        if (adminMode) return ascendingSortCategoriesById(categoryDao.findChilds(category));
        else return ascendingSortCategoriesById(categoryDao
                .findChilds(category)
                .stream()
                .filter(Category::getActive)
                .collect(Collectors.toList()));
    }

    @Override
    public List<Category> findCategoriesByHierarchyNumber(String hierarchyNumber, boolean adminMode) {
        if (adminMode) return ascendingSortCategoriesById(categoryDao.findCategoriesByHierarchyNumber(hierarchyNumber));
        else return ascendingSortCategoriesById(categoryDao
                .findCategoriesByHierarchyNumber(hierarchyNumber)
                .stream()
                .filter(Category::getActive)
                .collect(Collectors.toList()));
    }

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

    @Override
    public List<CategoryDto> convertCategoriesToCategoriesDto(List<Category> categories) {
        return categories
                .stream()
                .map(this::convertCategoryToCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto> findChildsDtoByCategory(Category category, boolean adminMode) {
        return convertCategoriesToCategoriesDto(findChilds(category, adminMode));
    }

    @Override
    public List<CategoryDto> findChildsDtoById(long id, boolean adminMode) {
        return convertCategoriesToCategoriesDto(findChilds(findCategoryById(String.valueOf(id), adminMode), adminMode));
    }

    @Override
    public long calculateNumberOfSales(long id) {
        Category category = categoryDao.findCategoryById(String.valueOf(id));
        long numberOfSales = productDao.findProductsByCategory(category)
                .stream()
                .mapToLong(p -> productDao.findTotalSalesById(p.getId()))
                .sum();
        return numberOfSales;
    }

    @Override
    public boolean checkIsCategoryNameFree(String name, Category parent) {
        return findChilds(parent, true)
                .stream()
                .filter(c -> c.getName().equalsIgnoreCase(name))
                .count() == 0;
    }

    @Override
    public void hideCategory(Category category) {
        productDao.findProductsByCategory(category)
                .forEach(p -> {
                    p.setActive(false);
                    productDao.saveProduct(p);
                });
        category.setActive(false);
        categoryDao.saveNewCategory(category);
    }

    @Override
    public void showCategory(Category category) {
        productDao.findProductsByCategory(category)
                .forEach(p -> {
                    p.setActive(true);
                    productDao.saveProduct(p);
                });
        category.setActive(true);
        categoryDao.saveNewCategory(category);
    }

    @Override
    public List<Category> ascendingSortCategoriesById(List<Category> categories) {
        return categories
                .stream()
                .sorted(ComparatorUtil.getCategoryComparator())
                .collect(Collectors.toList());
    }
}
