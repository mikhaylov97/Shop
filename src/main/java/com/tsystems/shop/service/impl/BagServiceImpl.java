package com.tsystems.shop.service.impl;

import com.tsystems.shop.dao.api.ProductDao;
import com.tsystems.shop.model.dto.BagProductDto;
import com.tsystems.shop.model.Product;
import com.tsystems.shop.model.Size;
import com.tsystems.shop.service.api.BagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Bag service. It is used to bag manipulations.
 */
@Service
public class BagServiceImpl implements BagService {

    /**
     * Injected by spring productDao bean
     */
    private final ProductDao productDao;

    /**
     * Injecting constructor
     * @param productDao that must be injected
     */
    @Autowired
    public BagServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    /**
     * Method calculates total price of the user's bag.
     *
     * @param products - given list of products(BagProductDto)
     * @return total price in String format.
     */
    @Override
    public String calculateTotalPrice(List<BagProductDto> products) {
        long totalPrice = 0;
        if (products == null) return "0";
        for (BagProductDto product : products) {
            totalPrice += product.getTotalPrice();
        }
        return String.valueOf(totalPrice);
    }

    /**
     * Method checks if product exists in bag.
     *
     * @param products existing products in bag.
     * @param id       of product that must be checked.
     * @return true if exist or false if not.
     */
    @Override
    public boolean checkIfProductAlreadyExist(List<BagProductDto> products, long id) {
        for (BagProductDto bagProduct : products) {
            if (bagProduct.getId() == id) return true;
        }
        return false;
    }

    /**
     * Method adds Product object to the bag. If such product already exists in user bag
     * method will increase quality of this product.
     *
     * @param products existing products in bag.
     * @param product  that must be added.
     */
    @Override
    public void addOrIncreaseIfExist(List<BagProductDto> products, Product product) {
        for (BagProductDto bagProduct : products) {
            if (bagProduct.getId() == product.getId()){
                bagProduct.setAmount(bagProduct.getAmount() + 1);
                break;
            }
        }
    }

    /**
     * During the application execution we should have a possibility
     * to convert our Product {@link Product}
     * to bag product object {@link BagProductDto}.
     *
     * @param product that will be converted
     * @param sizeId  - our bag product contains chosen by user size.
     * @return converted BagProductDto
     */
    @Override
    public BagProductDto createBagProductFromProduct(Product product, long sizeId) {
        return new BagProductDto(product.getId(),product.getName(),
                product.getImage(), 1L,
                Long.parseLong(product.getPrice()), sizeId,
                productDao.findSizeById(sizeId).getSize());
    }

    /**
     * During the application execution we should have a possibility
     * to convert our bag product(BagProductDto as you remember {@link BagProductDto})
     * to simple Product object {@link Product}.
     *
     * @param bagProduct one.
     * @return converted Product object.
     */
    @Override
    public Product convertBagProductDtoToProduct(BagProductDto bagProduct) {
        if (bagProduct == null) return null;
        return productDao.findProductById(bagProduct.getId());
    }

    /**
     * Method add product to the bag.
     *
     * @param productId   - id f the product that will be added.
     * @param amount      - quality of product.
     * @param sizeId      - id of product size.
     * @param price       of product.
     * @param bagProducts existing products in bag
     */
    @Override
    public void addToBag(long productId, int amount, long sizeId, long price, List<BagProductDto> bagProducts) {
        boolean isFoundInBag = false;
        for (BagProductDto product : bagProducts) {
            if (product.getId() == productId && product.getSizeId() == sizeId) {
                product.setAmount(product.getAmount() + amount);
                product.setTotalPrice(price * product.getAmount());
                isFoundInBag = true;
                break;
            }
        }
        Product originalProduct = productDao.findProductById(productId);
        //for collisions
//        if (Integer.parseInt(originalProduct
//                .getAttributes()
//                .getSizes()
//                .stream()
//                .filter(s -> s.getId() == sizeId)
//                .findFirst()
//                .get().getAvailableNumber()) < amount) {
//            return;
//        }
        if (!isFoundInBag) {
            BagProductDto bagProduct = new BagProductDto(productId, originalProduct.getName(),
                    originalProduct.getImage(), amount,
                    price*amount, sizeId,
                    productDao.findSizeById(sizeId).getSize());
            bagProducts.add(bagProduct);
        }

        for (Size size : originalProduct.getAttributes().getSizes()) {
            if (size.getId() == sizeId) {
                size.setAvailableNumber(String.valueOf(Integer.parseInt(size.getAvailableNumber()) - amount));
                productDao.saveProduct(originalProduct);
                break;
            }
        }
    }

    /**
     * Method delete product from the bag by product id and size id of this product.
     *
     * @param productId   - id of the product that will be delted.
     * @param sizeId      - id of product size.
     * @param bagProducts - existing products in bag.
     */
    @Override
    public void deleteFromBag(long productId, long sizeId, List<BagProductDto> bagProducts) {
        for (BagProductDto product : bagProducts) {
            if (product.getId() == productId && product.getSizeId() == sizeId) {
                Product originalProduct = productDao.findProductById(productId);
                for (Size size : originalProduct.getAttributes().getSizes()) {
                    if (size.getId() == sizeId) {
                        size.setAvailableNumber(String.valueOf(Integer.parseInt(size.getAvailableNumber()) + product.getAmount()));
                        productDao.saveProduct(originalProduct);
                        break;
                    }
                }
                bagProducts.remove(product);
                break;
            }
        }
    }
}
