package com.tsystems.shop.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sizes")
public class Size implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "size_id")
    private long id;

    @Column(name = "size", nullable = false)
    private String size;

    @Column(name = "available_number", nullable = false)
    private String availableNumber;

//    @Column(name = "cost_dependency", nullable = false)
//    private String costDependency;

    public Size() {
    }

    public Size(String size, String availableNumber) {
        this.size = size;
        this.availableNumber = availableNumber;
        //this.costDependency = costDependency;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getAvailableNumber() {
        return availableNumber;
    }

    public void setAvailableNumber(String availableNumber) {
        this.availableNumber = availableNumber;
    }

//    public String getCostDependency() {
//        return costDependency;
//    }
//
//    public void setCostDependency(String costDependency) {
//        this.costDependency = costDependency;
//    }
}