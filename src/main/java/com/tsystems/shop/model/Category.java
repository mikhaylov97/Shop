package com.tsystems.shop.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Category entity model. This class maps on categories Table in our Database.
 * There we put all necessary options in fields. Hibernate forces us to make class
 * with fields and getters and setters for all of them, Serializable interface(optional),
 * and empty constructor if we define custom one.
 */
@Entity
@Table(name = "categories")
public class Category  implements Serializable {

    /**
     * Category ID. It generates by hibernate while inserting.
     * This filed connects with category_id column in attributes table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")
    private long id;

    /**
     * Category name.
     * This filed connects with name column in categories table.
     * Cannot be nullable.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Category status value.
     * This filed connects with status column in categories table.
     * Cannot be nullable.
     */
    @Column(name = "status", nullable = false)
    private boolean active;

    /**
     * Category parent object. For correct working of our application we should have
     * and opportunity to get all categories hierarchy. So, adding parent field will help us with it.
     * JPA API allow us to get all parent data and Hibernate do it.
     * This filed connected with parent_id column in categories table.
     */
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    /**
     * Category hierarchy number value.
     * This filed connects with hierarchy_number column in categories table.
     * Cannot be nullable.
     */
    @Column(name = "hierarchy_number", nullable = false)
    private String hierarchyNumber;

    /**
     * Empty constructor for hibernate.
     */
    public Category() {
    }

    /**
     * Our custom constructor.
     * @param name value to set
     * @param hierarchyNumber value to set
     * @param parent object to set.
     */
    public Category(String name,String hierarchyNumber, Category parent) {
        this.name = name;
        this.active = true;
        this.hierarchyNumber = hierarchyNumber;
        this.parent = parent;
    }

    /**
     * Simple getter
     * @return Category status value
     */
    public boolean getActive() {
        return active;
    }

    /**
     * Simple setter
     * @param active value to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Simple getter
     * @return Category ID value
     */
    public long getId() {
        return id;
    }

    /**
     * Simple setter
     * @param id value to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Simple getter
     * @return Category name value
     */
    public String getName() {
        return name;
    }

    /**
     * Simple setter
     * @param name value to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Simple getter
     * @return Category hierarchy number value
     */
    public String getHierarchyNumber() {
        return hierarchyNumber;
    }

    /**
     * Simple setter
     * @param hierarchyNumber value to set
     */
    public void setHierarchyNumber(String hierarchyNumber) {
        this.hierarchyNumber = hierarchyNumber;
    }

    /**
     * Simple getter
     * @return Category parent object
     */
    public Category getParent() {
        return parent;
    }

    /**
     * Simple setter
     * @param parent object to set
     */
    public void setParent(Category parent) {
        this.parent = parent;
    }
}
