package com.tsystems.shop.service.impl;

import com.tsystems.shop.dao.api.CategoryDao;
import com.tsystems.shop.dao.api.ProductDao;
import com.tsystems.shop.model.Category;
import com.tsystems.shop.model.Product;
import com.tsystems.shop.model.Size;
import com.tsystems.shop.model.dto.ProductDto;
import com.tsystems.shop.service.api.ProductService;
import com.tsystems.shop.util.ComparatorUtil;
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
    public List<Product> findAllProducts(boolean adminMode) {
        if (adminMode) return ascendingSortProductsById(productDao.findAllProducts());
        else return ascendingSortProductsById(productDao.findNotHiddenProducts());
    }

    @Override
    public Product findProductById(long id, boolean adminMode) {
        Product product = productDao.findProductById(id);
        if (adminMode) return product;
        else return product.getActive() ? product : null;
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
    public List<Product> findProductsByCategory(Category category, boolean adminMode) {
        List<Product> products = new ArrayList<>();
        List<Category> childs = categoryDao.findChilds(category);
        if (childs != null) {
            for (Category child : childs) {
                products.addAll(findProductsByCategory(child, adminMode));
            }
        }
        products.addAll(productDao.findProductsByCategory(category));
        if (adminMode) return ascendingSortProductsById(products);
        else return ascendingSortProductsById(products
                .stream()
                .filter(Product::getActive)
                .collect(Collectors.toList()));
    }

    @Override
    public List<Product> findTop10Products(boolean adminMode) {
        return productDao.findTop10Products(adminMode);
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
            dto.setNumberOfSales(findTotalSalesById(product.getId()));
        }
        return resultList;
    }

    @Override
    @Transactional
    public boolean isTopProductsChanged() {
        if(tops.containsAll(productDao.findTop10Products(false))) {
            return false;
        } else {
            tops = productDao.findTop10Products(false);
            return true;
        }
    }

    @Override
    public void hideProduct(Product product) {
        product.setActive(false);
        productDao.saveProduct(product);
    }

    @Override
    public void showProduct(Product product) {
        product.setActive(true);
        productDao.saveProduct(product);
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
        dto.setActive(product.getActive());
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
    public List<ProductDto> findTop10ProductsDto(boolean adminMode) {
        return convertProductsToProductsDto(findTop10Products(adminMode));
    }

    @Override
    public List<Product> filterProductsByCostAndSize(String cost, String size, String categoryId) {
        List<Product> products;
        try {
            boolean sizeActive = true;
            if (cost.equals("")) cost = "0";
            long costLong = Long.parseLong(cost);
            long id = Long.parseLong(categoryId);
            products = findProductsByCategory(categoryDao.findCategoryById(categoryId), false)
                    .stream()
                    .filter( p -> (costLong == 0 || Long.parseLong(p.getPrice()) <= costLong)
                            && (size.equals("No matter") || p.getAttributes().getSizes().stream().filter(s -> s.getSize().equals(size)).count() > 0))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return findProductsByCategory(categoryDao.findCategoryById(categoryId), false);
        }
        return products;
    }

    @Override
    public List<ProductDto> findProductsByTerm(String term, boolean adminMode) {
        List<Product> products = findAllProducts(adminMode);
        List<ProductDto> productDtos = convertProductsToProductsDto(products);
        return convertProductsToProductsDto(findAllProducts(adminMode))
                .stream()
                .filter(p -> p.getName().toLowerCase().contains(term.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> ascendingSortProductsById(List<Product> products) {
        return products
                .stream()
                .sorted(ComparatorUtil.getProductComparator())
                .collect(Collectors.toList());
    }

    public List<Product> getTops() {
        return tops;
    }

    public void setTops(List<Product> tops) {
        this.tops = tops;
    }
}
