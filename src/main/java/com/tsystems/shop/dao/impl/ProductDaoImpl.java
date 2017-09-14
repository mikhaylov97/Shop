package com.tsystems.shop.dao.impl;

import com.tsystems.shop.dao.api.ProductDao;
import com.tsystems.shop.model.Category;
import com.tsystems.shop.model.Product;
import com.tsystems.shop.model.Size;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Class which implements all necessary methods which allow us
 * to work with database and categories.
 */
@Repository
public class ProductDaoImpl implements ProductDao {

    /**
     * It's an exemplar created by EntityManagerFactory we created and configured in DatabaseConfig class.
     * EntityManager provide us special API for working with database.
     * In particular, we can create different HQL queries. After executing that queries will give us
     * all necessary data.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * See {@link ProductDao}
     * @return list of found products.
     */
    @Override
    public List<Product> findAllProducts() {
        Query query = em.createQuery("SELECT p FROM Product p");
        return (List<Product>) query.getResultList();
    }

    /**
     * See {@link ProductDao}
     * @return list of found products.
     */
    @Override
    public List<Product> findNotHiddenProducts() {
        Query productsListQuery = em.createQuery("SELECT p FROM Product p WHERE p.active=true");
        return (List<Product>) productsListQuery.getResultList();
    }

    /**
     * See {@link ProductDao}
     * @param id of the product.
     * @return reference to a found Product object
     */
    @Override
    public Product findProductById(long id) {
        Query query = em.createQuery("SELECT p FROM Product p WHERE id = :id");
        query.setParameter("id", id);
        return (Product) query.getSingleResult();
    }

    /**
     * See {@link ProductDao}
     * @param product reference to a product object.
     * @return reference to a saved Product object.
     */
    @Override
    @Transactional
    public Product saveProduct(Product product) {
        Product saved = em.merge(product);
        em.flush();
        return saved;
    }

    /**
     * See {@link ProductDao}
     * @param id of the product size.
     * @return reference to a found Size object.
     */
    @Override
    public Size findSizeById(long id) {
        Query query = em.createQuery("SELECT s FROM Size s WHERE id = :id");
        query.setParameter("id", id);
        return (Size) query.getSingleResult();
    }

    /**
     * When admin change available sizes of product we don't need anymore
     * to store this sizes and this method delete them from database.
     *
     * @param sizeSet that must be deleted.
     */
    @Override
    @Transactional
    public void deleteSizesSet(Set<Size> sizeSet) {
        sizeSet.forEach(size -> em.remove(em.contains(size) ? size : em.merge(size)));
        em.flush();
    }

    /**
     * See {@link ProductDao}
     * @param sizeId - ID of the Size object.
     * @return available amount of the product with certain size.
     */
    @Override
    public int findAvailableAmountOfSize(long sizeId) {
        Query query = em.createQuery("SELECT s FROM Size s WHERE id = :id");
        query.setParameter("id", sizeId);
        return Integer.parseInt(((Size) query.getSingleResult()).getAvailableNumber());
    }

    /**
     * See {@link ProductDao}
     * @param category reference to a mapped Category object. Method will find object within this category.
     * @return list of found products.
     */
    @Override
    public List<Product> findProductsByCategory(Category category) {
        Query query = em.createQuery("SELECT p FROM Product p WHERE category.id = :id");
        query.setParameter("id", category.getId());
        return (List<Product>) query.getResultList();
    }

    /**
     * See {@link ProductDao}
     * @param adminMode - parameter which signalize is this top needed for admin or user.
     * @return list of found products.
     */
    @Override
    public List<Product> findTop10Products(boolean adminMode) {
        String subQuery = "SELECT MIN(p1.id), p1.order.id, p1.product.id FROM OrdersProducts p1"
                + " GROUP BY(p1.order.id, p1.product.id)";
        Query topListQuery;
        if (adminMode) {
            topListQuery = em.createQuery("SELECT p.product.id FROM OrdersProducts p"
                    + " WHERE (p.id, p.order.id, p.product.id) IN(" + subQuery + ") GROUP BY p.product.id"
                    + " ORDER BY COUNT(p.product.id) DESC");
        } else {
            topListQuery = em.createQuery("SELECT p.product.id FROM OrdersProducts p"
                    + " WHERE (p.id, p.order.id, p.product.id) IN(" + subQuery + ") AND p.product.active=true GROUP BY p.product.id"
                    + " ORDER BY COUNT(p.product.id) DESC");
        }
        topListQuery.setMaxResults(10);
        List<Product> products = new ArrayList<>();
        for (Long id : (List<Long>)topListQuery.getResultList()) {
            products.add(findProductById(id));
        }
        return products;
    }

    /**
     * See {@link ProductDao}
     * @param id of the product.
     * @return number of sales of the product by his ID.
     */
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
            return (long) totalSalesQuery.getSingleResult();
        } catch (NoResultException e) {
            return  0L;
        }
    }
}
