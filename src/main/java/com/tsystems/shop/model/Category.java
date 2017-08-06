package com.tsystems.shop.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "categories")
public class Category  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id")
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "parent_id")
//    private Set<Category> childs = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @Column(name = "hierarchy_number", nullable = false)
    private String hierarchyNumber;

    public Category() {
    }

    public Category(String name,String hierarchyNumber, Category parent) {
        this.name = name;
        //this.childs = childs;
        this.hierarchyNumber = hierarchyNumber;
        this.parent = parent;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public Set<Category> getChilds() {
//        return childs;
//    }

//    public void setChilds(Set<Category> childs) {
//        this.childs = childs;
//    }

    public String getHierarchyNumber() {
        return hierarchyNumber;
    }

    public void setHierarchyNumber(String hierarchyNumber) {
        this.hierarchyNumber = hierarchyNumber;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }
}
