package com.tsystems.shop.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Order entity model. This class maps on orders Table in our Database.
 * There we put all necessary options in fields. Hibernate forces us to make class
 * with fields and getters and setters for all of them, Serializable interface(optional),
 * and empty constructor if we define custom one.
 */
@Entity
@Table(name = "orders")
public class Order implements Serializable {

    /**
     * Order ID. It generates by hibernate while inserting.
     * This filed connects with order_id column in orders table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private long id;

    /**
     * Order status value.
     * This filed connects with order_status column in orders table.
     * Cannot be nullable.
     */
    @Column(name = "order_status", nullable = false)
    private String orderStatus;

    /**
     * Owner of the order. For correct working of our application we should have
     * an opportunity to get owner of every category(User). So, adding user field will help us with it.
     * JPA API allow us to get user object data and Hibernate do it.
     * This filed connected with user_id column in orders table.
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Payment part in order. Every order contains some data about the payment. So
     * it is the best practice - create standalone table (In our case payments table,
     * where every entry connected with some order and have detail information about it).
     * JPA API allow us to get payment data and Hibernate do it.
     * This filed connected with payment_id column in orders table.
     */
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    /**
     * Delivery address value.
     * This filed connects with address column in orders table.
     * Cannot be nullable.
     */
    @Column(name = "address", nullable = false)
    private String address;

    /**
     * Order date value.
     * This filed connects with date column in orders table.
     * Cannot be nullable.
     */
    @Column(name = "date", nullable = false)
    private String date;

    /**
     * Customer phone value.
     * This filed connects with phone column in orders table.
     * Cannot be nullable.
     */
    @Column(name = "phone", nullable = false)
    private String phone;

    /**
     * List of products in order. It is obvious that customer would like to buy several
     * product for one order. Even more, customer want to to have an opportunity
     * to buy different sizes of the same products. And we had to make orders_products table where we store
     * records about order, product and size(and amount of quantity of products).
     * Hibernate allow us to get list of records described above.
     */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrdersProducts> products = new ArrayList<>();

    /**
     * Empty constructor for Hibernate.
     */
    public Order() {
    }

    /**
     * Custom constructor with all necessary fields to initialize.
     * @param address - delivery address.
     * @param orderStatus - directly, order status, it's obvious.
     * @param user - the customer.
     * @param payment - reference to payment object.
     * @param date - order date in following format - dd-MM-yyyy. See {@link com.tsystems.shop.util.DateUtil}
     * @param phone - customer phone number.
     */
    public Order(String address, String orderStatus,
                 User user, Payment payment, String date, String phone) {
        this.orderStatus = orderStatus;
        this.user = user;
        this.payment = payment;
        this.address = address;
        this.date = date;
        this.phone = phone;
    }

    /**
     * Simple getter
     * @return Order ID value
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
     * @return Order status value
     */
    public String getOrderStatus() {
        return orderStatus;
    }

    /**
     * Simple setter
     * @param orderStatus is value to set
     */
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * Simple getter
     * @return Order customer object
     */
    public User getUser() {
        return user;
    }

    /**
     * Simple setter
     * @param user is value to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Simple getter
     * @return Order payment object
     */
    public Payment getPayment() {
        return payment;
    }

    /**
     * Simple setter
     * @param payment is value to set
     */
    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    /**
     * Simple getter
     * @return OrdersProducts list. To understand what it is, see description of this field and
     * OrderProducts class - {@link OrdersProducts}
     */
    public List<OrdersProducts> getProducts() {
        return products;
    }

    /**
     * Simple setter
     * @param products is value to set
     */
    public void setProducts(List<OrdersProducts> products) {
        this.products = products;
    }

    /**
     * Simple getter
     * @return Delivery address value
     */
    public String getAddress() {
        return address;
    }

    /**
     * Simple setter
     * @param address is value to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Simple getter
     * @return Order date value
     */
    public String getDate() {
        return date;
    }

    /**
     * Simple setter
     * @param date is value to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Simple getter
     * @return Customer phone number value
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Simple setter
     * @param phone is value to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
