package com.tsystems.shop.service.api;


import com.tsystems.shop.model.Category;
import com.tsystems.shop.model.Product;
import com.tsystems.shop.model.Size;
import com.tsystems.shop.model.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<Product> findAllProducts(boolean adminMode);
    Product findProductById(long id, boolean adminMode);
    Product saveProduct(Product product);
    Size findSizeById(long id);
    int findAvailableAmountOfSize(long sizeId);
    List<Product> findProductsByCategory(Category category, boolean adminMode);
    List<Product> findTop10Products(boolean adminMode);
    List<ProductDto> castProductsToDtos(List<Product> products);
    boolean isTopProductsChanged();
    void hideProduct(Product product);
    void showProduct(Product product);
    long findTotalSalesById(long id);
    ProductDto convertProductToProductDto(Product product);
    List<ProductDto> convertProductsToProductsDto(List<Product> products);
    List<ProductDto> findTop10ProductsDto(boolean adminMode);
    List<Product> filterProductsByCostAndSize(String cost, String size, String categoryId);
    List<ProductDto> findProductsByTerm(String term, boolean adminMode);
    List<Product> ascendingSortProductsById(List<Product> products);
}
