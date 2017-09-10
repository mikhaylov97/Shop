package com.tsystems.shop.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * This class maps on payments Table in our Database.
 * Some description put here {@link Order}
 * It is considered better practice to create another table for such data as payment.
 * There we put all necessary options in fields. Hibernate forces us to make class
 * with fields and getters and setters for all of them, Serializable interface(optional),
 * and empty constructor if we define custom one.
 */
@Entity
@Table(name = "payments")
public class Payment implements Serializable {

    /**
     * Payment ID. It generates by hibernate while inserting.
     * This filed connects with payment_id column in payments table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "payment_id")
    private long id;

    /**
     * Payment type. See more about available types here {@link com.tsystems.shop.model.enums.PaymentTypeEnum}
     * This filed connects with amount payment_type in payments table.
     * Cannot be nullable.
     */
    @Column(name = "payment_type", nullable = false)
    private String paymentType;

    /**
     * Total cost of the order. Depends on all products in bag, their cost and quantity.
     * This filed connects with total_price column in payments table.
     * Cannot be nullable.
     */
    @Column(name = "total_price", nullable = false)
    private String totalPrice;

    /**
     * Shipping cost.
     * This filed connects with shipping_price column in payments table.
     * Cannot be nullable.
     */
    @Column(name = "shipping_price", nullable = false)
    private String shippingPrice;

    /**
     * Payment status. See more about payment status types here {@link com.tsystems.shop.model.enums.PaymentStatusEnum}.
     * This filed connects with amount column in orders_products table.
     * Cannot be nullable.
     */
    @Column(name = "payment_status", nullable = false)
    private String paymentStatus;

    /**
     * Empty constructor for Hibernate
     */
    public Payment() {
    }

    /**
     * Custom constructor to initialize all necessary fields.
     * @param paymentType - see in field declaration.
     * @param totalPrice - see in field declaration.
     * @param shippingPrice - see in field declaration.
     * @param paymentStatus - see in field declaration.
     */
    public Payment(String paymentType, String totalPrice, String shippingPrice, String paymentStatus) {
        this.paymentType = paymentType;
        this.totalPrice = totalPrice;
        this.shippingPrice = shippingPrice;
        this.paymentStatus = paymentStatus;
    }

    /**
     * Simple getter
     * @return ID value
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
     * @return Payment type value
     */
    public String getPaymentType() {
        return paymentType;
    }

    /**
     * Simple setter
     * @param paymentType is value to set
     */
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    /**
     * Simple getter
     * @return Order cost value
     */
    public String getTotalPrice() {
        return totalPrice;
    }

    /**
     * Simple setter
     * @param totalPrice is value to set
     */
    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Simple getter
     * @return Payment status value
     */
    public String getPaymentStatus() {
        return paymentStatus;
    }

    /**
     * Simple setter
     * @param paymentStatus is value to set
     */
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    /**
     * Simple getter
     * @return Shipping cost value
     */
    public String getShippingPrice() {
        return shippingPrice;
    }

    /**
     * Simple setter
     * @param shippingPrice is value to set
     */
    public void setShippingPrice(String shippingPrice) {
        this.shippingPrice = shippingPrice;
    }
}
