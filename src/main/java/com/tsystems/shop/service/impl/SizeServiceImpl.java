package com.tsystems.shop.service.impl;

import com.tsystems.shop.model.Product;
import com.tsystems.shop.model.Size;
import com.tsystems.shop.service.api.SizeService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SizeServiceImpl implements SizeService {
    @Override
    public Set<Size> parseString(String sizes) {
        Set<Size> sizeSet = new HashSet<>();
        for (String string : sizes.split(",")) {
            String[] sizeItem = string.split("-");
            Size size = new Size(sizeItem[0], sizeItem[2]);
            sizeSet.add(size);
        }
        return sizeSet;
    }

    @Override
    public int getAvaiableAmountOfSize(int sizeId) {
        return 0;
    }

    @Override
    public Set<String> findSizesFromProducts(List<Product> products) {
        Set<String> sizes = new HashSet<>();
        products.forEach(p -> p.getAttributes().getSizes()
                .forEach(s -> sizes.add(s.getSize())));
        return sizes;
    }
}
