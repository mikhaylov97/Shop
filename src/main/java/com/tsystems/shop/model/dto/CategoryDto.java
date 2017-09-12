package com.tsystems.shop.model.dto;


import com.tsystems.shop.model.Category;

import java.io.Serializable;

/**
 * Class which represents simplified view of Category model.
 * See {@link com.tsystems.shop.model.Category}
 */
public class CategoryDto implements Serializable {

    /**
     * Category id
     */
    private long id;
    /**
     * parent of current category (By default: MEN'S or WOMEN'S object)
     */
    private Category parent;
    /**
     * Hierarchy number of the category
     */
    private int hierarchyNumber;
    /**
     * Name of the category
     */
    private String name;
    /**
     * Total number of sales in current category.
     * (Depends on all products in this category and their number of sales)
     */
    private long numberOfSales;
    /**
     * Number of different products in current category
     */
    private long numberOfProducts;
    /**
     * Status of the category(Should it be visible for simple users)
     */
    private boolean active;

    /**
     * Simple getter of the ID
     * @return ID
     */
    public long getId() {
        return id;
    }

    /**
     * Simple setter of the ID
     * @param id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Simple getter of the Parent object
     * @return Parent object
     */
    public Category getParent() {
        return parent;
    }

    /**
     * Simple setter of the Parent object
     * @param parent to set
     */
    public void setParent(Category parent) {
        this.parent = parent;
    }

    /**
     * Simple getter of the hierarchy number
     * @return hierarchy number
     */
    public int getHierarchyNumber() {
        return hierarchyNumber;
    }

    /**
     * Simple setter of the hierarchy number
     * @param hierarchyNumber to set
     */
    public void setHierarchyNumber(int hierarchyNumber) {
        this.hierarchyNumber = hierarchyNumber;
    }

    /**
     * Simple getter of the category's name.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Simple setter of the category's name
     * @param name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Simple getter of the category's number of sales
     * @return number of sales
     */
    public long getNumberOfSales() {
        return numberOfSales;
    }

    /**
     * Simple setter of the category's sales number
     * @param numberOfSales to set
     */
    public void setNumberOfSales(long numberOfSales) {
        this.numberOfSales = numberOfSales;
    }

    /**
     * Simple getter of the category's number of products
     * @return number of product
     */
    public long getNumberOfProducts() {
        return numberOfProducts;
    }

    /**
     * Simple setter of the category's number of products
     * @param numberOfProducts to set
     */
    public void setNumberOfProducts(long numberOfProducts) {
        this.numberOfProducts = numberOfProducts;
    }

    /**
     * Simple getter of the category's active status
     * @return true if active and vice versa
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Simple setter of the category's active status
     * @param active to set. True if active and vice versa
     */
    public void setActive(boolean active) {
        this.active = active;
    }
}
