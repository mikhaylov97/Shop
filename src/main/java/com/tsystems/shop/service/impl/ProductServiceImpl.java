package com.tsystems.shop.service.impl;

import com.tsystems.shop.dao.api.CategoryDao;
import com.tsystems.shop.dao.api.ProductDao;
import com.tsystems.shop.model.Category;
import com.tsystems.shop.model.Product;
import com.tsystems.shop.model.Size;
import com.tsystems.shop.model.dto.ProductDto;
import com.tsystems.shop.service.api.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private List<Product> tops = new ArrayList<>(10);

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public List<Product> findAllProducts() {
        return productDao.findAllProducts();
    }

    @Override
    public Product findProductById(long id) {
        return productDao.findProductById(id);
    }

    @Override
    public Product saveProduct(Product product) {
        return productDao.saveProduct(product);
    }

    @Override
    public Size findSizeById(long id) {
       return productDao.findSizeById(id);
    }

    @Override
    public int findAvailableAmountOfSize(long sizeId) {
        return productDao.findAvailableAmountOfSize(sizeId);
    }

    @Override
    public List<Product> findProductsByCategory(Category category) {
        List<Product> products = new ArrayList<>();
        List<Category> childs = categoryDao.findChilds(category);
        if (childs != null) {
            for (Category child : childs) {
                products.addAll(findProductsByCategory(child));
            }
        }
        products.addAll(productDao.findProductsByCategory(category));
        return products;
    }

    @Override
    public List<Product> findTop10Products() {
        return productDao.findTop10Products();
    }

    @Override
    public List<ProductDto> castProductsToDtos(List<Product> products) {
        List<ProductDto> resultList = new ArrayList<>();
        for (Product product : products) {
            ProductDto dto = new ProductDto();
            dto.setId(product.getId());
            dto.setName(product.getName());
            dto.setImage(product.getImage());
            dto.setPrice(product.getPrice());
            //dto.setNumberOfSales();
        }
        return resultList;
    }

    @Override
    @Transactional
    public boolean isTopProductsChanged() {
        if(tops.containsAll(productDao.findTop10Products())) {
            return false;
        } else {
            tops = productDao.findTop10Products();
            return true;
        }
    }

    @Override
    public long findTotalSalesById(long id) {
        return productDao.findTotalSalesById(id);
    }

    @Override
    public ProductDto convertProductToProductDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setImage(product.getImage());
        dto.setPrice(product.getPrice());
        dto.setNumberOfSales(findTotalSalesById(product.getId()));
        return dto;
    }

    @Override
    public List<ProductDto> convertProductsToProductsDto(List<Product> products) {
        List<ProductDto> resultList = new ArrayList<>();
        for (Product product : products) {
            resultList.add(convertProductToProductDto(product));
        }
        return resultList;
    }

    @Override
    public List<ProductDto> findTop10ProductsDto() {
        return convertProductsToProductsDto(findTop10Products());
    }

    @Override
    public List<Product> filterProductsByCostAndSize(String cost, String size, String categoryId) {
        List<Product> products;
        try {
            if (cost.equals("")) cost = "0";
            long costLong = Long.parseLong(cost);
            long id = Long.parseLong(categoryId);
            products = findProductsByCategory(categoryDao.findCategoryById(categoryId))
                    .stream()
                    .filter( p -> (costLong == 0 || Long.parseLong(p.getPrice()) <= costLong) &&
                    p.getAttributes().getSizes().stream().filter(s -> s.getSize().equals(size)).count() > 0)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return findProductsByCategory(categoryDao.findCategoryById(categoryId));
        }
        return products;
    }

    @Override
    public List<ProductDto> findProductsByTerm(String term) {
        List<Product> products = findAllProducts();
        List<ProductDto> productDtos = convertProductsToProductsDto(products);
        return convertProductsToProductsDto(findAllProducts())
                .stream()
                .filter(p -> p.getName().toLowerCase().contains(term.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Product> getTops() {
        return tops;
    }

    public void setTops(List<Product> tops) {
        this.tops = tops;
    }
}
