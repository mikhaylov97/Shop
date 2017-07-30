package com.tsystems.shop.service.api;


import com.tsystems.shop.model.Size;

import java.util.Set;

public interface SizeService {
    Set<Size> parseString(String sizes);
}
