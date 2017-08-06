package com.tsystems.shop.service.api;


import com.tsystems.shop.model.BagProduct;
import com.tsystems.shop.model.Product;

import java.util.List;

public interface BagService {
    String figureOutTotalPrice(List<BagProduct> products);
    boolean checkIfProductAlreadyExist(List<BagProduct> products, long id);
    void addOrIncreaseIfExist(List<BagProduct> products, Product product);
    BagProduct createBagProductFromProduct(Product product, long sizeId);
    Product findProductByBagProduct(BagProduct bagProduct);
    void addToBag(long productId, int amount, long sizeId,long price, List<BagProduct> bagProducts);
    void deleteFromBag(long productId, long sizeId, List<BagProduct> bagProducts);
}
