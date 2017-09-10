package com.tsystems.shop.dao.api;


import com.tsystems.shop.model.Category;

import java.util.List;

/**
 * This interface provide us API through which we will communicate with database.
 */
public interface CategoryDao {

    /**
     * Method should find all categories.
     * @return list of found categories.
     */
    List<Category> findCategories();

    /**
     * Method should find only root categories. In our case, we have 2 root categories(Men's and women's clothes).
     * @return list of found root categories.
     */
    List<Category> findRootCategories();

    /**
     * Method should find certain category by his ID.
     * @param id of the category.
     * @return reference to a mapped Category object.
     */
    Category findCategoryById(String id);

    /**
     * Method should save Category in database.
     * @param category reference to a Category object which we need to save.
     * @return reference to a saved Category object.
     */
    Category saveCategory(Category category);

    /**
     * Method should find childs of some category.
     * @param category - reference to a Category object where we need to find childs.
     * @return list of found categories.
     */
    List<Category> findChilds(Category category);

    /**
     * Method should find categories by hierarchy number.
     * @param hierarchyNumber - directly, the number.
     * @return list of found categories.
     */
    List<Category> findCategoriesByHierarchyNumber(String hierarchyNumber);
}
