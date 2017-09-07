package com.tsystems.shop.util;


import com.tsystems.shop.model.Category;
import com.tsystems.shop.model.Product;

import java.util.Comparator;

public class ComparatorUtil {

    private static Comparator<Product> productComparator = (o1, o2) -> {
        if (o1.getId() < o2.getId()) return -1;
        if (o1.getId() > o2.getId()) return 1;
        return 0;
    };

    private static Comparator<Category> categoryComparator = (o1, o2) -> {
        if (o1.getId() < o2.getId()) return -1;
        if (o1.getId() > o2.getId()) return 1;
        return 0;
    };

    public static Comparator<Product> getProductComparator() {
        return productComparator;
    }

    public static Comparator<Category> getCategoryComparator() {
        return categoryComparator;
    }
}
