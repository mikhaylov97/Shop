package com.tsystems.shop.service.impl;

import com.tsystems.shop.dao.api.ProductDao;
import com.tsystems.shop.model.BagProduct;
import com.tsystems.shop.model.Product;
import com.tsystems.shop.model.Size;
import com.tsystems.shop.service.api.BagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BagServiceImpl implements BagService {

    @Autowired
    private ProductDao productDao;

    @Override
    public String figureOutTotalPrice(List<BagProduct> products) {
        long totalPrice = 0;
        if (products == null) return "0";
        for (BagProduct product : products) {
            totalPrice += product.getTotalPrice();
        }
        return String.valueOf(totalPrice);
    }

    @Override
    public boolean checkIfProductAlreadyExist(List<BagProduct> products, long id) {
        for (BagProduct bagProduct : products) {
            if (bagProduct.getId() == id) return true;
        }
        return false;
    }

    @Override
    public void addOrIncreaseIfExist(List<BagProduct> products, Product product) {
        boolean isExist = false;
        for (BagProduct bagProduct : products) {
            if (bagProduct.getId() == product.getId()){
                bagProduct.setAmount(bagProduct.getAmount() + 1);
                isExist = true;
                break;
            }
        }
        if (!isExist) {
            //products.add(createBagProductFromProduct(product));
        }
    }

    @Override
    public BagProduct createBagProductFromProduct(Product product, long sizeId) {
        BagProduct bagProduct = new BagProduct(product.getId(),product.getName(),
                product.getImage(), 1L,
                Long.parseLong(product.getPrice()), sizeId,
                productDao.findSizeById(sizeId).getSize());
        return  bagProduct;
    }

    @Override
    public Product findProductByBagProduct(BagProduct bagProduct) {
        return productDao.findProductById(bagProduct.getId());
    }

    @Override
    public void addToBag(long productId, int amount, long sizeId, long price, List<BagProduct> bagProducts) {
        boolean isFoundInBag = false;
        for (BagProduct product : bagProducts) {
            if (product.getId() == productId && product.getSizeId() == sizeId) {
                product.setAmount(product.getAmount() + amount);
                product.setTotalPrice(price * product.getAmount());
                isFoundInBag = true;
                break;
            }
        }
        Product originalProduct = productDao.findProductById(productId);
        if (!isFoundInBag) {
            BagProduct bagProduct = new BagProduct(productId, originalProduct.getName(),
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

    @Override
    public void deleteFromBag(long productId, long sizeId, List<BagProduct> bagProducts) {
        for (BagProduct product : bagProducts) {
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
