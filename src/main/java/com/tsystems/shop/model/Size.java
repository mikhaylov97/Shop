package com.tsystems.shop.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Size entity model. This class maps on sizes Table in our Database.
 * There we store all information about the product size. Hibernate forces us to make class
 * with fields and getters and setters for all of them, Serializable interface(optional),
 * and empty constructor if we define custom one.
 */
@Entity
@Table(name = "sizes")
public class Size implements Serializable{

    /**
     * Size ID. It generates by hibernate while inserting.
     * This filed connects with size_id column in sizes table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "size_id")
    private long id;

    /**
     * Size name.
     * This filed connected with size column in sizes table.
     * Cannot be nullable.
     */
    @Column(name = "size", nullable = false)
    private String sizeName;

    /**
     * Available number of product with this size.
     * This filed connected with available_number column in sizes table.
     * Cannot be nullable.
     */
    @Column(name = "available_number", nullable = false)
    private String availableNumber;

    /**
     * Empty constructor for Hibernate
     */
    public Size() {
    }

    /**
     * Custom constructor to initialize all necessary fields.
     * @param size name. See in fields declaration
     * @param availableNumber of current size. See in fields declaration.
     */
    public Size(String size, String availableNumber) {
        this.sizeName = size;
        this.availableNumber = availableNumber;
    }

    /**
     * Simple getter
     * @return Size ID value
     */
    public long getId() {
        return id;
    }

    /**
     * Simple setter
     * @param id is value to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Simple getter
     * @return Size name value
     */
    public String getSize() {
        return sizeName;
    }

    /**
     * Simple setter
     * @param size
     * is value to set
     */
    public void setSize(String size) {
        this.sizeName = size;
    }

    /**
     * Simple getter
     * @return Size available number value
     */
    public String getAvailableNumber() {
        return availableNumber;
    }

    /**
     * Simple setter
     * @param availableNumber is value to set
     */
    public void setAvailableNumber(String availableNumber) {
        this.availableNumber = availableNumber;
    }
}