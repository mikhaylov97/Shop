package com.tsystems.shop.util;


import com.tsystems.shop.model.Category;
import com.tsystems.shop.model.Order;
import com.tsystems.shop.model.Product;

import java.util.Comparator;

/**
 * As we see from the class name, the goal of this class
 * to give different prepared comparators.
 */
public class ComparatorUtil {

    /**
     * Empty constructor
     */
    private ComparatorUtil() {
    }

    /**
     * Comparator for sorting orders by ID from smallest to largest
     */
    private static Comparator<Order> ascendingOrderComparator = (o1, o2) -> {
        if (o1.getId() < o2.getId()) return -1;
        if (o1.getId() > o2.getId()) return 1;
        return 0;
    };

    /**
     * Comparator for sorting orders by ID from largest to smallest
     */
    private static Comparator<Order> descendingOrderComparator = (o1, o2) -> {
        if (o1.getId() > o2.getId()) return -1;
        if (o1.getId() < o2.getId()) return 1;
        return 0;
    };

    /**
     * Comparator for sorting products by ID from smallest to largest
     */
    private static Comparator<Product> ascendingProductComparator = (p1, p2) -> {
        if (p1.getId() < p2.getId()) return -1;
        if (p1.getId() > p2.getId()) return 1;
        return 0;
    };

    /**
     * Comparator for sorting products by ID from largest to smallest
     */
    private static Comparator<Product> descendingProductComparator = (p1, p2) -> {
        if (p1.getId() > p2.getId()) return -1;
        if (p1.getId() < p2.getId()) return 1;
        return 0;
    };

    /**
     * Comparator for sorting categories by ID from smallest to largest
     */
    private static Comparator<Category> ascendingCategoryComparator = (c1, c2) -> {
        if (c1.getId() < c2.getId()) return -1;
        if (c1.getId() > c2.getId()) return 1;
        return 0;
    };

    /**
     * Comparator for sorting categories by ID from largest to smallest
     */
    private static Comparator<Category> descendingCategoryComparator = (c1, c2) -> {
        if (c1.getId() > c2.getId()) return -1;
        if (c1.getId() < c2.getId()) return 1;
        return 0;
    };

    /**
     * Getter of the ascendingOrderComparator static field
     * @return ascendingOrderComparator
     */
    public static Comparator<Order> getAscendingOrderComparator() {
        return ascendingOrderComparator;
    }

    /**
     * Getter of the descendingOrderComparator static field
     * @return descendingOrderComparator
     */
    public static Comparator<Order> getDescendingOrderComparator() {
        return descendingOrderComparator;
    }

    /**
     * Getter of the ascendingProductComparator static field
     * @return ascendingProductComparator
     */
    public static Comparator<Product> getAscendingProductComparator() {
        return ascendingProductComparator;
    }

    /**
     * Getter of the descendingProductComparator static field
     * @return descendingProductComparator
     */
    public static Comparator<Product> getDescendingProductComparator() {
        return descendingProductComparator;
    }

    /**
     * Getter of the ascendingCategoryComparator static field
     * @return ascendingCategoryComparator
     */
    public static Comparator<Category> getAscendingCategoryComparator() {
        return ascendingCategoryComparator;
    }

    /**
     * Getter of the descendingCategoryComparator static field
     * @return descendingCategoryComparator
     */
    public static Comparator<Category> getDescendingCategoryComparator() {
        return descendingCategoryComparator;
    }


}
