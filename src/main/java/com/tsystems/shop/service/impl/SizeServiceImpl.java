package com.tsystems.shop.service.impl;

import com.tsystems.shop.model.Size;
import com.tsystems.shop.service.api.SizeService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class SizeServiceImpl implements SizeService {
    @Override
    public Set<Size> parseString(String sizes) {
        Set<Size> sizeSet = new HashSet<>();
        for (String string : sizes.split(",")) {
            String[] sizeItem = string.split("-");
            Size size = new Size(sizeItem[0], sizeItem[2], sizeItem[1]);
            sizeSet.add(size);
        }
        return sizeSet;
    }
}
