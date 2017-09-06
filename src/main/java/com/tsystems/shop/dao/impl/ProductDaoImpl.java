package com.tsystems.shop.dao.impl;

import com.tsystems.shop.dao.api.ProductDao;
import com.tsystems.shop.model.Category;
import com.tsystems.shop.model.Product;
import com.tsystems.shop.model.Size;
import com.tsystems.shop.model.dto.ProductDto;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
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
        product = em.merge(product);
        em.flush();
        return product;
    }

    @Override
    public Size findSizeById(long id) {
        Query query = em.createQuery("SELECT s FROM Size s WHERE id = :id");
        query.setParameter("id", id);
        return (Size) query.getSingleResult();
    }

    @Override
    public int findAvailableAmountOfSize(long sizeId) {
        Query query = em.createQuery("SELECT s FROM Size s WHERE id = :id");
        query.setParameter("id", sizeId);
        return Integer.parseInt(((Size) query.getSingleResult()).getAvailableNumber());
    }

    @Override
    public List<Product> findProductsByCategory(Category category) {
        Query query = em.createQuery("SELECT p FROM Product p WHERE category.id = :id");
        query.setParameter("id", category.getId());
        return (List<Product>) query.getResultList();
    }

    @Override
    public List<Product> findTop10Products() {
//        String subQuery = "SELECT p1.id, p1.order.id, p1.product.id FROM OrdersProducts p1 "
//                + "LEFT JOIN OrdersProducts p2 ON p1.order.id=p2.order.id AND p1.product.id=p2.product.id"
//                +
        String subQuery = "SELECT MIN(p1.id), p1.order.id, p1.product.id FROM OrdersProducts p1"
                + " GROUP BY(p1.order.id, p1.product.id)";
        Query topListQuery = em.createQuery("SELECT p.product.id FROM OrdersProducts p"
                + " WHERE (p.id, p.order.id, p.product.id) IN(" + subQuery + ") GROUP BY p.product.id"
                + " ORDER BY COUNT(p.product.id) DESC");
        topListQuery.setMaxResults(10);
        List<Product> products = new ArrayList<>();
        for (Long id : (List<Long>)topListQuery.getResultList()) {
            products.add(findProductById(id));
        }
        return products;
    }

    @Override
    public long findTotalSalesById(long id) {
        String subQuery = "SELECT MIN(p1.id), p1.order.id, p1.product.id FROM OrdersProducts p1"
                + " WHERE p1.product.id = :id"
                + " GROUP BY(p1.order.id, p1.product.id)";
        Query totalSalesQuery = em.createQuery("SELECT COUNT(o.product.id) FROM OrdersProducts o"
                + " WHERE (o.id, o.order.id, o.product.id) IN"
                + " (" + subQuery +") GROUP BY o.product.id");
        totalSalesQuery.setParameter("id", id);
        try {
            long sales = (Long) totalSalesQuery.getSingleResult();
            return sales;
        } catch (NoResultException e) {
            return  0L;
        }
    }
}
