package com.tsystems.shop.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "categories")
public class Category  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")
    private int id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "hierarchy_number", nullable = false)
    private String hierarchyNumber;

    public Category() {
    }

    public Category(String name, String image, String hierarchyNumber) {
        this.name = name;
        this.image = image;
        this.hierarchyNumber = hierarchyNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getHierarchyNumber() {
        return hierarchyNumber;
    }

    public void setHierarchyNumber(String hierarchyNumber) {
        this.hierarchyNumber = hierarchyNumber;
    }
}
