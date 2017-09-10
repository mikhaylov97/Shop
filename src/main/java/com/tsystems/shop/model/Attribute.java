package com.tsystems.shop.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Attribute entity model. This class maps on attributes Table in our Database.
 * There we put all necessary options in fields. Hibernate forces us to make class
 * with fields and getters and setters for all of them, Serializable interface(optional),
 * and empty constructor if we define custom one.
 */
@Entity
@Table(name = "attributes")
public class Attribute implements Serializable {

    /**
     * Attribute ID. It generates by hibernate while inserting.
     * This filed connects with attribute_id column in attributes table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "attribute_id")
    private long id;

    /**
     * Attribute sizes set. Our Product model contains attribute class field.
     * So, we should have a possibility to store different sizes of the product one.
     * That is why we need this set. And the easiest way to do it is to create table with
     * many-to-many connection.
     * JPA API allow us to get this set and Hibernate do it.
     * This filed connected with attributes_sizes table.
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "attributes_sizes",
            joinColumns = {@JoinColumn(name = "attribute_id")}, inverseJoinColumns = {@JoinColumn(name = "size_id")})
    private Set<Size> sizes;

    /**
     * Attribute description.
     * This filed connects with description column in attributes table.
     * Cannot be nullable.
     */
    @Column(name = "description", nullable = false)
    private String description;

    /**
     * Empty constructor for hibernate.
     */
    public Attribute() {
    }

    /**
     * Our custom constructor.
     * @param sizes - see above.
     * @param description - see above.
     */
    public Attribute(Set<Size> sizes, String description) {
        this.sizes = sizes;
        this.description = description;
    }

    /**
     * Simple getter
     * @return Attribute ID
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
     * @return Attribute sizes set
     */
    public Set<Size> getSizes() {
        return sizes;
    }

    /**
     * Simple setter
     * @param sizes value to set
     */
    public void setSizes(Set<Size> sizes) {
        this.sizes = sizes;
    }

    /**
     * Simple getter
     * @return Attribute description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Simple setter
     * @param description value to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
