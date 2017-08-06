package com.tsystems.shop.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "attributes")
public class Attribute implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "attribute_id")
    private long id;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "attributes_sizes",
            joinColumns = {@JoinColumn(name = "attribute_id")}, inverseJoinColumns = {@JoinColumn(name = "size_id")})
    private Set<Size> sizes;

    @Column(name = "description", nullable = false)
    private String description;

    public Attribute() {
    }

    public Attribute(Set<Size> sizes, String description) {
        this.sizes = sizes;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Size> getSizes() {
        return sizes;
    }

    public void setSizes(Set<Size> sizes) {
        this.sizes = sizes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
