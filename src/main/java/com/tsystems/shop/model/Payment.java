package com.tsystems.shop.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "payments")
public class Payment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "payment_id")
    private long id;

    @Column(name = "payment_type", nullable = false)
    private String paymentType;

    @Column(name = "total_price", nullable = false)
    private String totalPrice;

    @Column(name = "payment_status", nullable = false)
    private String paymentStatus;

    public Payment() {
    }

    public Payment(String paymentType, String totalPrice, String paymentStatus) {
        this.paymentType = paymentType;
        this.totalPrice = totalPrice;
        this.paymentStatus = paymentStatus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
