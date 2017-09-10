package com.tsystems.shop.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * This class maps on orders-products Table in our Database.
 * Some description put here {@link Order}
 * Customer should have a possibility to add different sizes of the same product in bag
 * and, obviously, in order. To solve this problem was used following approach:
 * orders table store records about order(including payment) and reference to a customer.
 * orders_products table store records about order(his id), product(his id), necessary size of it
 * and quantity. Therefore we can connect one order and one product with several sizes of the last.
 * There we put all necessary options in fields. Hibernate forces us to make class
 * with fields and getters and setters for all of them, Serializable interface(optional),
 * and empty constructor if we define custom one.
 */
@Entity
@Table(name = "orders_products")
public class OrdersProducts implements Serializable {

    /**
     * ID. It generates by hibernate while inserting.
     * This filed connects with orders_products_id column in orders_products table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "orders_products_id")
    private long id;

    /**
     * Some order. As can see above in class declaration, this table connects order and product with different sizes.
     * Hibernate allow us to get Order object from OrdersProducts object.
     * This filed connected with order_id column in orders_products table.
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;

    /**
     * Some product. As can see above in class declaration, this table connects order and product with different sizes.
     * Hibernate allow us to get Product object from OrdersProducts object.
     * This filed connected with product_id column in orders_products table.
     */
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.DETACH})
    @JoinColumn(name = "product_id")
    private Product product;

    /**
     * Product quantity of certain size.
     * This filed connects with amount column in orders_products table.
     * Cannot be nullable.
     */
    @Column(name = "amount", nullable = false)
    private String amount;

    /**
     * Some size object. As you can see above in class declaration, this table connects order and product with different sizes.
     * Therefore we also should store information about size.
     * This filed do it.
     * Hibernate allow us to get Size object from OrdersProducts object.
     * This filed connected with size_id column in orders_products table.
     */
    @ManyToOne
    @JoinColumn(name = "size_id")
    private Size size;

    /**
     * Empty constructor for Hibernate
     */
    public OrdersProducts() {
    }

    /**
     * Custom constructor to initialize all necessary fields.
     * @param order object.
     * @param product object.
     * @param amount value.
     * @param size object.
     */
    public OrdersProducts(Order order, Product product, String amount, Size size) {
        this.order = order;
        this.product = product;
        this.amount = amount;
        this.size = size;
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
     * @return Order object
     */
    public Order getOrder() {
        return order;
    }

    /**
     * Simple setter
     * @param order is object to set
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * Simple getter
     * @return Product object
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Simple setter
     * @param product is object to set
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Simple getter
     * @return amount value
     */
    public String getAmount() {
        return amount;
    }

    /**
     * Simple setter
     * @param amount is value to set
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * Simple getter
     * @return Size object
     */
    public Size getSize() {
        return size;
    }

    /**
     * Simple setter
     * @param size is object to set
     */
    public void setSize(Size size) {
        this.size = size;
    }
}
