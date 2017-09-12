package com.tsystems.shop.model.dto;


import com.tsystems.shop.model.Size;

import java.io.Serializable;
import java.util.List;

/**
 * Class represents simplified view of Size model.
 * This class is especially needed in adding or editing product,
 * To set unpredictable number of sizes which admin will decide to set for the product.
 * See {@link com.tsystems.shop.model.Size}
 */
public class SizesDto implements Serializable {

    /**
     * Directly, list of sizes which out product should contains inside.
     */
    private List<Size> sizes;

    /**
     * Simple getter of the sizes list
     * @return sizes
     */
    public List<Size> getSizes() {
        return sizes;
    }

    /**
     * Simple setter of the sizes list
     * @param sizes list to set
     */
    public void setSizes(List<Size> sizes) {
        this.sizes = sizes;
    }
}
