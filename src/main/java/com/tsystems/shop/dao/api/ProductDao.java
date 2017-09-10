package com.tsystems.shop.dao.api;


import com.tsystems.shop.model.Category;
import com.tsystems.shop.model.Product;
import com.tsystems.shop.model.Size;

import java.util.List;

/**
 * This interface provide us API through which we will communicate with database.
 */
public interface ProductDao {

    /**
     * Method should find size by ID
     * @param id of the product size.
     * @return reference to a Size object
     */
    Size findSizeById(long id);

    /**
     * Method should find all products in the database.
     * @return list of the found products.
     */
    List<Product> findAllProducts();

    /**
     * Method should find product in database by his ID.
     * @param id of the product.
     * @return reference to a mapped Product object.
     */
    Product findProductById(long id);

    /**
     * Method should find number of sales of the product by his ID.
     * @param id of the product.
     * @return number of sales.
     */
    long findTotalSalesById(long id);

    /**
     * Method should save some product in database.
     * @param product reference to a product object.
     * @return reference to a saved object.
     */
    Product saveProduct(Product product);

    /**
     * Method should find not hidden products(users cannot see them, but admins can).
     * @return list of the found products.
     */
    List<Product> findNotHiddenProducts();

    /**
     * Method should find available amount of some Size object by his ID.
     * @param sizeId - ID of the Size object.
     * @return available amount of the product with certain size.
     */
    int findAvailableAmountOfSize(long sizeId);

    /**
     * Method should find top 10 products of the shop.
     * @param adminMode - parameter which signalize is this top needed for admin or user.
     * @return list of found products.
     */
    List<Product> findTop10Products(boolean adminMode);

    /**
     * Method should find products by the certain category.
     * @param category reference to a mapped Category object. Method will find object within this category.
     * @return list of found products.
     */
    List<Product> findProductsByCategory(Category category);
}
