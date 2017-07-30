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

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "sex", nullable = false)
    private String sex;

    @Column(name = "model", nullable = false)
    private String model;

    public Attribute() {
    }

    public Attribute(Set<Size> sizes, String color, String sex, String model) {
        this.sizes = sizes;
        this.color = color;
        this.sex = sex;
        this.model = model;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
