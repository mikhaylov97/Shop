package com.tsystems.shop.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private long id;

//    @Column(name = "shipping_cost", nullable = false)
//    private String shippingMethod;

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

    @Column(name = "date", nullable = false)
    private String date;

    @Column(name = "phone", nullable = false)
    private String phone;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "orders_products",
//            joinColumns = {@JoinColumn(name = "order_id")}, inverseJoinColumns = {@JoinColumn(name = "product_id")})
//    private Set<Product> products;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrdersProducts> products = new ArrayList<>();

    public Order() {
    }

    public Order(String address, String orderStatus,
                 User user, Payment payment, String date, String phone) { //Set<Product> products) {
       // this.shippingMethod = shippingMethod;
        this.orderStatus = orderStatus;
        this.user = user;
        this.payment = payment;
        this.address = address;
        this.date = date;
        this.phone = phone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

//    public String getShippingMethod() {
//        return shippingMethod;
//    }
//
//    public void setShippingMethod(String shippingMethod) {
//        this.shippingMethod = shippingMethod;
//    }

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

    public List<OrdersProducts> getProducts() {
        return products;
    }

    public void setProducts(List<OrdersProducts> products) {
        this.products = products;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    //    public Set<Product> getProducts() {
//        return products;
//    }
//
//    public void setProducts(Set<Product> products) {
//        this.products = products;
//    }
}
