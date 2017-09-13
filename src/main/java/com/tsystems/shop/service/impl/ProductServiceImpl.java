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
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.TextMessage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Product service. It is used to product manipulations.
 */
@Service
public class ProductServiceImpl implements ProductService {

    /**
     * List with top shop products. (the more sales, the higher the rating).
     */
    private List<Product> tops = new ArrayList<>(10);

    /**
     * This injected object allow us to send messages to the JMS server
     * without any difficulties through sendMessage() API.
     */
    private final JmsTemplate jmsTemplate;

    /**
     * Injected by spring productDao bean
     */
    private final ProductDao productDao;

    /**
     * Injected by spring categoryDao bean
     */
    private final CategoryDao categoryDao;

    /**
     * Injecting constructor.
     * @param productDao that must be injected.
     * @param categoryDao that must be injected.
     */
    @Autowired
    public ProductServiceImpl(ProductDao productDao, CategoryDao categoryDao, JmsTemplate jmsTemplate) {
        this.productDao = productDao;
        this.categoryDao = categoryDao;
        this.jmsTemplate = jmsTemplate;
    }

    /**
     * Method finds all products in shop. If activeMode is true,
     * method will return all found products, otherwise only not hidden
     * products.
     *
     * @param adminMode see above.
     * @return list of found products.
     */
    @Override
    public List<Product> findAllProducts(boolean adminMode) {
        if (adminMode) return productDao.findAllProducts();
        else return productDao.findAllProducts()
                .stream()
                .filter(Product::getActive)
                .collect(Collectors.toList());
    }

    /**
     * Method finds product by his ID.
     *
     * @param id        of the product.
     * @param adminMode - If activeMode is true,
     *                  method will return found product, if activeMode is false, method will return
     *                  product if it is not hidden, otherwise null will be returned.
     * @return null or found product object.
     */
    @Override
    public Product findProductById(long id, boolean adminMode) {
        Product product = productDao.findProductById(id);
        if (adminMode) return product;
        else return product.getActive() ? product : null;
    }

    /**
     * Method saves products.
     *
     * @param product that must be saved in database.
     * @return reference to a saved product.
     */
    @Override
    public Product saveProduct(Product product) {
        return productDao.saveProduct(product);
    }

    /**
     * Method finds size object by his ID.
     *
     * @param id of the Size.
     * @return found Size object.
     */
    @Override
    public Size findSizeById(long id) {
        return productDao.findSizeById(id);
    }

    /**
     * Method should try to send message to the ActiveMQ server.
     * If advertising stand application is available it will receive this message
     * and will make an attempt to update top products list.
     */
    @Override
    public void sendUpdateMessageToJmsServer() {
        sendMessage();
    }

    /**
     * The method finds the available quantity of a product of a certain size.
     *
     * @param sizeId that must be checked.
     * @return available number of product.
     */
    @Override
    public int findAvailableAmountOfSize(long sizeId) {
        return productDao.findAvailableAmountOfSize(sizeId);
    }

    /**
     * Method finds products in certain category. If activeMode is true,
     * method will return all found products, otherwise only not hidden
     * found products.
     *
     * @param category  where products must be found.
     * @param adminMode see above.
     * @return list of found products.
     */
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

    /**
     * Method finds top 10 products. If activeMode is true,
     * method will return all found top products, otherwise only not hidden
     * top products.
     *
     * @param adminMode see above.
     * @return list of found products.
     */
    @Override
    public List<Product> findTop10Products(boolean adminMode) {
        return productDao.findTop10Products(adminMode);
    }

    /**
     * Method finds 4 products for suggested block inside product page.
     *
     * @return list with suggested products.
     */
    @Override
    public List<Product> suggest4RandomProducts() {
        List<Product> suggestions = new ArrayList<>(4);
        List<Product> top = productDao.findTop10Products(false);
        Collections.shuffle(top);
        List<Product> notTop = productDao.findNotHiddenProducts();
        notTop.removeAll(top);
        Collections.shuffle(notTop);

        int topSize = top.size();
        int nonTopSize = notTop.size();
        if (nonTopSize >= 2 && topSize >= 2) {
            suggestions.addAll(Arrays.asList(top.stream().findFirst().get(),
                    top.stream().skip(topSize/2).findFirst().get(),
                    notTop.stream().findFirst().get(),
                    notTop.stream().skip(nonTopSize/2).findFirst().get()));
        } else if (topSize >= 4){
            suggestions.addAll(Arrays.asList(top.stream().findFirst().get(),
                    top.stream().skip(topSize/4).findFirst().get(),
                    top.stream().skip(topSize/4).findFirst().get(),
                    top.stream().skip(topSize/4).findFirst().get()));
        } else {
            suggestions.addAll(tops);
        }
        Collections.shuffle(suggestions);
        return suggestions;
    }

    /**
     * Method checks if top is changed.
     *
     * @return true if is changed and false if not.
     */
    @Override
    @Transactional
    public void updateTopIfItHaveChanged() {
        if(!tops.containsAll(productDao.findTop10Products(false))) {
            tops = productDao.findTop10Products(false);
            sendMessage();
        }
    }

    /**
     * Method makes product status(active field) false.
     * Users cannot see and buy hidden products(but admins can see them).
     *
     * @param product that must be hidden.
     */
    @Override
    public void hideProduct(Product product) {
        product.setActive(false);
        productDao.saveProduct(product);
    }

    /**
     * Method makes product status(active field) true.
     * Users cannot see and buy hidden products(but admins can see them).
     *
     * @param product that must be hidden.
     */
    @Override
    public void showProduct(Product product) {
        product.setActive(true);
        productDao.saveProduct(product);
    }

    /**
     * Method finds total sales of the product by his ID.
     *
     * @param id of the Products object.
     * @return number of sales.
     */
    @Override
    public long findTotalSalesById(long id) {
        return productDao.findTotalSalesById(id);
    }

    /**
     * Method converts Product object {@link Product} to ProductDto object {@link ProductDto}.
     *
     * @param product that must be converted.
     * @return ProductDto object as a result of converting.
     */
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

    /**
     * Method converts Products list  {@link Product} to ProductsDto list {@link ProductDto}.
     *
     * @param products is a list that must be converted.
     * @return list converted products in dto format. See {@link ProductDto}.
     */
    @Override
    public List<ProductDto> convertProductsToProductsDto(List<Product> products) {
        List<ProductDto> resultList = new ArrayList<>();
        for (Product product : products) {
            resultList.add(convertProductToProductDto(product));
        }
        return resultList;
    }

    /**
     * Method finds top 10 products. If activeMode is true,
     * method will return all found top products, otherwise only not hidden
     * top products.
     *
     * @param adminMode see above.
     * @return list of found products in dto format(see {@link ProductDto}).
     */
    @Override
    public List<ProductDto> findTop10ProductsDto(boolean adminMode) {
        return convertProductsToProductsDto(findTop10Products(adminMode));
    }


    /**
     * Method filter products by their cost(lower cost bound and upper cost bound) and size
     * in certain category.
     *
     * @param lowerCostBound is a filter parameter chosen by user.
     * @param upperCostBound is a filter parameter chosen by user.
     * @param size           is a filter parameter chosen by user.
     * @param categoryId     which must be parent category for the sought products.
     * @return list of found products.
     */
    @Override
    public List<Product> filterProductsByCostAndSize(String lowerCostBound, String upperCostBound, String size, String categoryId) {
        List<Product> products;
        try {
            long costToLong;
            long costFromLong;
            if (lowerCostBound.equals("")) costFromLong = Long.parseLong("0");
            else costFromLong = Long.parseLong(lowerCostBound.substring(1, lowerCostBound.length()));
            if (upperCostBound.equals("")) costToLong = Long.parseLong("0");
            else costToLong = Long.parseLong(upperCostBound.substring(1, upperCostBound.length()));
            products = findProductsByCategory(categoryDao.findCategoryById(categoryId), false)
                    .stream()
                    .filter( p -> (costFromLong == 0 || Long.parseLong(p.getPrice()) >= costFromLong)
                            && (costToLong == 0 || Long.parseLong(p.getPrice()) <= costToLong)
                            && (size.equals("No matter") || p.getAttributes().getSizes().stream().filter(s -> s.getSize().equals(size)).count() > 0))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return findProductsByCategory(categoryDao.findCategoryById(categoryId), false);
        }
        return products;
    }

    /**
     * Method finds products which names contains term parameter. If activeMode is true,
     * method will return all found products, otherwise only not hidden
     * found products.
     *
     * @param term      that must be in the names of products.
     * @param adminMode see above.
     * @return list of found products in dto forma. See {@link ProductDto}
     */
    @Override
    public List<ProductDto> findProductsByTerm(String term, boolean adminMode) {
        return convertProductsToProductsDto(findAllProducts(adminMode))
                .stream()
                .filter(p -> p.getName().toLowerCase().contains(term.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Method sorts items in ascending order.
     *
     * @param products that must be sorted.
     * @return Sorted products list.
     */
    @Override
    public List<Product> ascendingSortProductsById(List<Product> products) {
        return products
                .stream()
                .sorted(ComparatorUtil.getAscendingProductComparator())
                .collect(Collectors.toList());
    }

    /**
     * Simple getter.
     * @return list of current top products in shop.
     */
    public List<Product> getTops() {
        return tops;
    }

    /**
     * Simple setter.
     * @param tops is value that must be set.
     */
    public void setTops(List<Product> tops) {
        this.tops = tops;
    }

    /**
     * This method push message to the ActiveMQ server. When the second application will see this message,
     * it will send GET request http://localhost:8080/advertising/stand (see AdvertisingRestController).
     * @throws JmsException when, for example, don't have connection with JMS server
     */
    private void sendMessage() {
        jmsTemplate.send("advertising.stand", session -> {
            TextMessage msg = session.createTextMessage();
            msg.setText("update");
            return msg;
        });
    }
}
