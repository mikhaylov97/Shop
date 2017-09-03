package com.tsystems.shop.model.dto;


public class BagProductDto {
    private long id;
    private String name;
    private String image;
    private long amount;
    private long totalPrice;
    private long sizeId;
    private String size;

    public BagProductDto(long id, String name, String image, long amount, long totalPrice, long sizeId, String size) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.amount = amount;
        this.totalPrice = totalPrice;
        this.sizeId = sizeId;
        this.size = size;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getSizeId() {
        return sizeId;
    }

    public void setSizeId(long sizeId) {
        this.sizeId = sizeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
