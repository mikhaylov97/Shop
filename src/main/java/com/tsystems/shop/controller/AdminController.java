package com.tsystems.shop.controller;

import com.tsystems.shop.model.Attribute;
import com.tsystems.shop.model.Product;
import com.tsystems.shop.model.Size;
import com.tsystems.shop.service.api.CategoryService;
import com.tsystems.shop.service.api.ProductService;
import com.tsystems.shop.service.api.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SizeService sizeService;

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/products/add")
    public ModelAndView showAddProductPage() {
        ModelAndView modelAndView = new ModelAndView("addProductTest");
        modelAndView.addObject("categories",categoryService.findCategories());

        return modelAndView;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadProduct(@RequestParam(name = "name") String name,
                                @RequestParam(name = "price") String price,
//                                @RequestParam(name = "image") MultipartFile image,
                                @RequestParam(name = "category") String category,
                                @RequestParam(name = "color") String color,
                                @RequestParam(name = "model") String model,
                                @RequestParam(name = "sex") String sex,
                                @RequestParam(name = "size") String sizes) {
        Set<Size> sizeSet = sizeService.parseString(sizes);
        Attribute attributes = new Attribute(sizeSet, color, sex, model);
        Product product = new Product(name, price, "smth", categoryService.findCategoryById(category), attributes);
        productService.saveProduct(product);
        return "redirect:/home";
    }


}
