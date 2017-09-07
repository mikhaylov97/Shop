package com.tsystems.shop.model.dto;


import com.tsystems.shop.model.Category;

public class CategoryDto {

    private long id;
    private Category parent;
    private int hierarchyNumber;
    private String name;
    private long numberOfSales;
    private long numberOfProducts;
    private boolean active;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public int getHierarchyNumber() {
        return hierarchyNumber;
    }

    public void setHierarchyNumber(int hierarchyNumber) {
        this.hierarchyNumber = hierarchyNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getNumberOfSales() {
        return numberOfSales;
    }

    public void setNumberOfSales(long numberOfSales) {
        this.numberOfSales = numberOfSales;
    }

    public long getNumberOfProducts() {
        return numberOfProducts;
    }

    public void setNumberOfProducts(long numberOfProducts) {
        this.numberOfProducts = numberOfProducts;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
