package com.tsystems.shop.dao.impl;


import com.tsystems.shop.dao.api.CategoryDao;
import com.tsystems.shop.model.Category;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Class which implements all necessary methods which allow us
 * to work with database and categories.
 */
@Repository
public class CategoryDaoImpl implements CategoryDao{

    /**
     * It's an exemplar created by EntityManagerFactory we created and configured in DatabaseConfig class.
     * EntityManager provide us special API for working with database.
     * In particular, we can create different HQL queries. After executing that queries will give us
     * all necessary data.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Method saves category object.
     * @param category reference to a Category object which we need to save.
     * @return reference to a saved object.
     */
    @Override
    @Transactional
    public Category saveCategory(Category category) {
        Category saved = em.merge(category);
        em.flush();
        return saved;
    }

    /**
     * See {@link CategoryDao}
     * @return list of found categories
     */
    @Override
    public List<Category> findCategories() {
        Query query = em.createQuery("SELECT c FROM Category c");
        return (List<Category>) query.getResultList();
    }

    /**
     * See {@link CategoryDao}
     * @param id of the category.
     * @return reference to a category object.
     */
    @Override
    public Category findCategoryById(String id) {
        Query query = em.createQuery("SELECT c FROM Category c WHERE id = :id");
        query.setParameter("id", Long.parseLong(id));
        return (Category) query.getSingleResult();
    }

    /**
     * See {@link CategoryDao}
     * @return list of found categories.
     */
    @Override
    public List<Category> findRootCategories() {
        Query query = em.createQuery("SELECT c FROM Category c WHERE parent = null");
        return (List<Category>) query.getResultList();
    }

    /**
     * See {@link CategoryDao}
     * @param category - reference to a Category object where we need to find childs.
     * @return list of found categories.
     */
    @Override
    public List<Category> findChilds(Category category) {
        Query query = em.createQuery("SELECT c FROM Category c WHERE parent = :category");
        query.setParameter("category", category);
        return (List<Category>) query.getResultList();
    }

    /**
     * See {@link CategoryDao}
     * @param hierarchyNumber - directly, the number.
     * @return list of found categories.
     */
    @Override
    public List<Category> findCategoriesByHierarchyNumber(String hierarchyNumber) {
        Query query = em.createQuery("SELECT c FROM Category c WHERE hierarchyNumber = :hierarchyNumber");
        query.setParameter("hierarchyNumber", hierarchyNumber);
        return (List<Category>) query.getResultList();
    }
}
