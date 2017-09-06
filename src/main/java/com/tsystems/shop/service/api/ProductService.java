package com.tsystems.shop.service.api;


import com.tsystems.shop.model.Category;
import com.tsystems.shop.model.Product;
import com.tsystems.shop.model.Size;
import com.tsystems.shop.model.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<Product> findAllProducts();
    Product findProductById(long id);
    Product saveProduct(Product product);
    Size findSizeById(long id);
    int findAvailableAmountOfSize(long sizeId);
    List<Product> findProductsByCategory(Category category);
    List<Product> findTop10Products();
    List<ProductDto> castProductsToDtos(List<Product> products);
    boolean isTopProductsChanged();
    long findTotalSalesById(long id);
    ProductDto convertProductToProductDto(Product product);
    List<ProductDto> convertProductsToProductsDto(List<Product> products);
    List<ProductDto> findTop10ProductsDto();
    List<Product> filterProductsByCostAndSize(String cost, String size, String categoryId);
    List<ProductDto> findProductsByTerm(String term);
}
