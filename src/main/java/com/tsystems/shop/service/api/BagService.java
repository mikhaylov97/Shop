package com.tsystems.shop.service.api;


import com.tsystems.shop.model.dto.BagProductDto;
import com.tsystems.shop.model.Product;

import java.util.List;

public interface BagService {
    String figureOutTotalPrice(List<BagProductDto> products);
    boolean checkIfProductAlreadyExist(List<BagProductDto> products, long id);
    void addOrIncreaseIfExist(List<BagProductDto> products, Product product);
    BagProductDto createBagProductFromProduct(Product product, long sizeId);
    Product findProductByBagProduct(BagProductDto bagProduct);
    void addToBag(long productId, int amount, long sizeId,long price, List<BagProductDto> bagProducts);
    void deleteFromBag(long productId, long sizeId, List<BagProductDto> bagProducts);
}
