package com.tsystems.shop.service.api;


import com.tsystems.shop.model.dto.BagProductDto;
import com.tsystems.shop.model.Product;

import java.util.List;

/**
 * Interface provide us API we can use to manipulate our Bag products.
 */
public interface BagService {
    /**
     * Method calculates total price of the user's bag.
     * @param products - given list of products(BagProductDto)
     * @return total price in String format.
     */
    String calculateTotalPrice(List<BagProductDto> products);

    /**
     * During the application execution we should have a possibility
     * to convert our bag product(BagProductDto as you remember {@link BagProductDto})
     * to simple Product object {@link Product}.
     * @param bagProduct one.
     * @return converted Product object.
     */
    Product convertBagProductDtoToProduct(BagProductDto bagProduct);

    /**
     * During the application execution we should have a possibility
     * to convert our Product {@link Product}
     * to bag product object {@link BagProductDto}.
     * @param product that will be converted
     * @param sizeId - our bag product contains chosen by user size.
     * @return converted BagProductDto
     */
    BagProductDto createBagProductFromProduct(Product product, long sizeId);

    /**
     * Method adds Product object to the bag. If such product already exists in user bag
     * method will increase quality of this product.
     * @param products existing products in bag.
     * @param product that must be added.
     */
    void addOrIncreaseIfExist(List<BagProductDto> products, Product product);

    /**
     * Method checks if product exists in bag.
     * @param products existing products in bag.
     * @param id of product that must be checked.
     * @return true if exist or false if not.
     */
    boolean checkIfProductAlreadyExist(List<BagProductDto> products, long id);

    /**
     * Method delete product from the bag by product id and size id of this product.
     * @param productId  - id of the product that will be delted.
     * @param sizeId - id of product size.
     * @param bagProducts - existing products in bag.
     */
    void deleteFromBag(long productId, long sizeId, List<BagProductDto> bagProducts);

    /**
     * Method add product to the bag.
     * @param productId - id f the product that will be added.
     * @param amount - quality of product.
     * @param sizeId - id of product size.
     * @param price of product.
     * @param bagProducts existing products in bag
     */
    void addToBag(long productId, int amount, long sizeId,long price, List<BagProductDto> bagProducts);
}
