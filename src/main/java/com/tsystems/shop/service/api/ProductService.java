package com.tsystems.shop.service.api;


import com.tsystems.shop.model.Category;
import com.tsystems.shop.model.Product;
import com.tsystems.shop.model.Size;
import com.tsystems.shop.model.dto.ProductDto;

import java.util.List;


/**
 * Interface provide us API we can use to manipulate products.
 */
public interface ProductService {
    /**
     * Method finds size object by his ID.
     * @param id of the Size.
     * @return found Size object.
     */
    Size findSizeById(long id);

    /**
     * Method checks if top is changed.
     * @return true if is changed and false if not.
     */
    boolean isTopProductsChanged();

    /**
     * Method finds total sales of the product by his ID.
     * @param id of the Products object.
     * @return number of sales.
     */
    long findTotalSalesById(long id);

    /**
     * Method makes product status(active field) false.
     * Users cannot see and buy hidden products(but admins can see them).
     * @param product that must be hidden.
     */
    void hideProduct(Product product);

    /**
     * Method makes product status(active field) true.
     * Users cannot see and buy hidden products(but admins can see them).
     * @param product that must be hidden.
     */
    void showProduct(Product product);

    /**
     * Method saves products.
     * @param product that must be saved in database.
     * @return reference to a saved product.
     */
    Product saveProduct(Product product);

    /**
     * The method finds the available quantity of a product of a certain size.
     * @param sizeId that must be checked.
     * @return available number of product.
     */
    int findAvailableAmountOfSize(long sizeId);

    /**
     * Method finds all products in shop. If activeMode is true,
     * method will return all found products, otherwise only not hidden
     * products.
     * @param adminMode see above.
     * @return list of found products.
     */
    List<Product> findAllProducts(boolean adminMode);

    /**
     * Method finds top 10 products. If activeMode is true,
     * method will return all found top products, otherwise only not hidden
     * top products.
     * @param adminMode see above.
     * @return list of found products.
     */
    List<Product> findTop10Products(boolean adminMode);

    /**
     * Method finds product by his ID.
     * @param id of the product.
     * @param adminMode - If activeMode is true,
     * method will return found product, if activeMode is false, method will return
     * product if it is not hidden, otherwise null will be returned.
     * @return null or found product object.
     */
    Product findProductById(long id, boolean adminMode);

    /**
     * Method converts Product object {@link Product} to ProductDto object {@link ProductDto}.
     * @param product that must be converted.
     * @return ProductDto object as a result of converting.
     */
    ProductDto convertProductToProductDto(Product product);

    /**
     * Method finds top 10 products. If activeMode is true,
     * method will return all found top products, otherwise only not hidden
     * top products.
     * @param adminMode see above.
     * @return list of found products in dto format(see {@link ProductDto}).
     */
    List<ProductDto> findTop10ProductsDto(boolean adminMode);

    /**
     * Method sorts items in ascending order.
     * @param products that must be sorted.
     * @return Sorted products list.
     */
    List<Product> ascendingSortProductsById(List<Product> products);

    /**
     * Method finds products which names contains term parameter. If activeMode is true,
     * method will return all found products, otherwise only not hidden
     * found products.
     * @param term that must be in the names of products.
     * @param adminMode see above.
     * @return list of found products in dto forma. See {@link ProductDto}
     */
    List<ProductDto> findProductsByTerm(String term, boolean adminMode);

    /**
     * Method converts Products list  {@link Product} to ProductsDto list {@link ProductDto}.
     * @param products is a list that must be converted.
     * @return list converted products in dto format. See {@link ProductDto}.
     */
    List<ProductDto> convertProductsToProductsDto(List<Product> products);

    /**
     * Method finds products in certain category. If activeMode is true,
     * method will return all found products, otherwise only not hidden
     * found products.
     * @param category where products must be found.
     * @param adminMode see above.
     * @return list of found products.
     */
    List<Product> findProductsByCategory(Category category, boolean adminMode);

    /**
     * Method filter products by their cost(lower cost bound and upper cost bound) and size
     * in certain category.
     * @param lowerCostBound is a filter parameter chosen by user.
     * @param upperCostBound is a filter parameter chosen by user.
     * @param size is a filter parameter chosen by user.
     * @param categoryId which must be parent category for the sought products.
     * @return list of found products.
     */
    List<Product> filterProductsByCostAndSize(String lowerCostBound, String upperCostBound, String size, String categoryId);
}
