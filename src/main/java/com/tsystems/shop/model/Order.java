package com.tsystems.shop.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private long id;

    @Column(name = "shipping_method", nullable = false)
    private String shippingMethod;

    @Column(name = "order_status", nullable = false)
    private String orderStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @Column(name = "address", nullable = false)
    private String address;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "orders_products",
//            joinColumns = {@JoinColumn(name = "order_id")}, inverseJoinColumns = {@JoinColumn(name = "product_id")})
//    private Set<Product> products;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrdersProducts> products = new HashSet<>();

    public Order() {
    }

    public Order(String address, String shippingMethod, String orderStatus, User user, Payment payment) { //Set<Product> products) {
        this.shippingMethod = shippingMethod;
        this.orderStatus = orderStatus;
        this.user = user;
        this.payment = payment;
        this.products = products;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Set<OrdersProducts> getProducts() {
        return products;
    }

    public void setProducts(Set<OrdersProducts> products) {
        this.products = products;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    //    public Set<Product> getProducts() {
//        return products;
//    }
//
//    public void setProducts(Set<Product> products) {
//        this.products = products;
//    }
}
