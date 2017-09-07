package com.tsystems.shop.dao.impl;


import com.tsystems.shop.dao.api.CategoryDao;
import com.tsystems.shop.model.Category;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class CategoryDaoImpl implements CategoryDao{

    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional
    public Category saveNewCategory(Category category) {
        category = em.merge(category);
        em.flush();
        return category;
    }

    @Override
    public List<Category> findCategories() {
        Query query = em.createQuery("SELECT c FROM Category c");
        return (List<Category>) query.getResultList();
    }

    @Override
    public Category findCategoryById(String id) {
        Query query = em.createQuery("SELECT c FROM Category c WHERE id = :id");
        query.setParameter("id", Long.parseLong(id));
        return (Category) query.getSingleResult();
    }

    @Override
    public List<Category> findRootCategories() {
        Query query = em.createQuery("SELECT c FROM Category c WHERE parent = null");
        return (List<Category>) query.getResultList();
    }

    @Override
    public List<Category> findChilds(Category category) {
        Query query = em.createQuery("SELECT c FROM Category c WHERE parent = :category");
        query.setParameter("category", category);
        return (List<Category>) query.getResultList();
    }

    @Override
    public List<Category> findCategoriesByHierarchyNumber(String hierarchyNumber) {
        Query query = em.createQuery("SELECT c FROM Category c WHERE hierarchyNumber = :hierarchyNumber");
        query.setParameter("hierarchyNumber", hierarchyNumber);
        return (List<Category>) query.getResultList();
    }
}
