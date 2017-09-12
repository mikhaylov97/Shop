package com.tsystems.shop.model.dto;

import java.io.Serializable;

/**
 * Class which represents simplified view of Product model.
 * This dto is used in Bag mechanism
 * See {@link com.tsystems.shop.model.Product}
 */
public class BagProductDto implements Serializable {
    /**
     * Product id
     */
    private long id;
    /**
     * Product name
     */
    private String name;
    /**
     * Path to image
     */
    private String image;
    /**
     * Current quantity of Product in Bag.
     */
    private long amount;
    /**
     * Total price of the Product in Bag. (Depends on cost and amount).
     */
    private long totalPrice;
    /**
     * Size ID of current type of Product in Bag
     */
    private long sizeId;
    /**
     * Size name of the product
     */
    private String size;

    /**
     * Empty constructor
     */
    public BagProductDto() {
    }

    /**
     * Simple constructor.
     * @param id see fields description
     * @param name see fields description
     * @param image see fields description
     * @param amount see fields description
     * @param totalPrice see fields description
     * @param sizeId see fields description
     * @param size see fields description
     */
    public BagProductDto(long id, String name, String image, long amount, long totalPrice, long sizeId, String size) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.amount = amount;
        this.totalPrice = totalPrice;
        this.sizeId = sizeId;
        this.size = size;
    }

    /**
     * @return ID of current product
     */
    public long getId() {
        return id;
    }

    /**
     * Allow us to set ID of current product
     * @param id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return amount of current product in bag
     */
    public long getAmount() {
        return amount;
    }

    /**
     * Allow us to set amount of current product
     * @param amount to set
     */
    public void setAmount(long amount) {
        this.amount = amount;
    }

    /**
     * @return total price of current product
     */
    public long getTotalPrice() {
        return totalPrice;
    }

    /**
     * Allow us to set total price of current product
     * @param totalPrice to set
     */
    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * @return size ID of current product
     */
    public long getSizeId() {
        return sizeId;
    }

    /**
     * Allow us to set size ID of current product
     * @param sizeId to set
     */
    public void setSizeId(long sizeId) {
        this.sizeId = sizeId;
    }

    /**
     * @return Name of current product
     */
    public String getName() {
        return name;
    }

    /**
     * Allow us to set name of current product
     * @param name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return image path in String format of current product
     */
    public String getImage() {
        return image;
    }

    /**
     * Allow us to set image path of current product
     * @param image to set
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return Size name of current product
     */
    public String getSize() {
        return size;
    }

    /**
     * Allow us to set Size name of current product
     * @param size to set
     */
    public void setSize(String size) {
        this.size = size;
    }
}
