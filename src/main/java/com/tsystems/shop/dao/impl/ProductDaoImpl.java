package com.tsystems.shop.dao.impl;

import com.tsystems.shop.dao.api.ProductDao;
import com.tsystems.shop.model.Product;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDao {

    @PersistenceContext
    EntityManager em;
    @Override
    public List<Product> findAllProducts() {
        Query query = em.createQuery("SELECT p FROM Product p");
        List<Product> products = (List<Product>) query.getResultList();
        return products;
    }

    @Override
    public Product findProductById(long id) {
        Query query = em.createQuery("SELECT p FROM Product p WHERE id = :id");
        query.setParameter("id", id);
        return (Product) query.getSingleResult();
    }

    @Override
    @Transactional
    public Product saveProduct(Product product) {
        em.merge(product);
        em.flush();
        return product;
    }
}
