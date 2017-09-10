package com.tsystems.shop.model.dto;

/**
 * Class represents modernised view of Product model.
 * It shows all necessary data from Product object and in addition
 * different fields with information we should calculate.
 * See {@link com.tsystems.shop.model.Product}
 */
public class ProductDto {
    /**
     * Product ID
     */
    private long id;
    /**
     * Product name
     */
    private String name;
    /**
     * Product price
     */
    private String price;
    /**
     * Path to the product image
     */
    private String image;
    /**
     * Product sales number for all orders history
     */
    private long numberOfSales;
    /**
     * Product status. It must be true when product is active and simple users
     * can buy it. And it must be false when users cannot see this product. But admins can.
     */
    private boolean active;

    /**
     * Simple getter
     * @return ID value
     */
    public long getId() {
        return id;
    }

    /**
     * Simple setter
     * @param id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Simple getter
     * @return name value
     */
    public String getName() {
        return name;
    }

    /**
     * Simple setter
     * @param name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Simple getter
     * @return price value
     */
    public String getPrice() {
        return price;
    }

    /**
     * Simple setter
     * @param price to set
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * Simple getter
     * @return image path value
     */
    public String getImage() {
        return image;
    }

    /**
     * Simple setter
     * @param image path to set
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Simple getter
     * @return number of sales value
     */
    public long getNumberOfSales() {
        return numberOfSales;
    }

    /**
     * simple setter
     * @param numberOfSales to set
     */
    public void setNumberOfSales(long numberOfSales) {
        this.numberOfSales = numberOfSales;
    }

    /**
     * Simple getter
     * @return product status value. See details above
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Simple setter
     * @param active - status to set. See details above.
     */
    public void setActive(boolean active) {
        this.active = active;
    }
}
