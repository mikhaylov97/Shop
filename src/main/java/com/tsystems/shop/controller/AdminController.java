package com.tsystems.shop.controller;

import com.tsystems.shop.model.*;
import com.tsystems.shop.model.enums.OrderStatusEnum;
import com.tsystems.shop.model.enums.PaymentStatusEnum;
import com.tsystems.shop.service.api.CategoryService;
import com.tsystems.shop.service.api.OrderService;
import com.tsystems.shop.service.api.ProductService;
import com.tsystems.shop.service.api.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private static final String IMAGES = "images";
    private static final String TOMCAT_HOME_PROPERTY = "catalina.home";
    private static final String TOMCAT_HOME_PATH = System.getProperty(TOMCAT_HOME_PROPERTY);
    private static final String IMAGES_PATH = TOMCAT_HOME_PATH + File.separator + IMAGES;

    private static final File IMAGES_DIR = new File(IMAGES_PATH);
    private static final String IMAGES_DIR_ABSOLUTE_PATH = IMAGES_DIR.getAbsolutePath() + File.separator;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SizeService sizeService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/products/add")
    public ModelAndView showAddProductPage() {
        ModelAndView modelAndView = new ModelAndView("add-product");
        modelAndView.addObject("options", categoryService.findCategoriesByHierarchyNumber("2"));
        modelAndView.addObject("sizes", new AddProductForm());
        return modelAndView;
    }

    @RequestMapping(value = "/products/add/upload", method = RequestMethod.POST)
    public ModelAndView uploadProduct(@ModelAttribute("sizes") AddProductForm sizes,
                                      BindingResult result,
                                      @RequestParam(name = "name") String name,
                                      @RequestParam(name = "price") String price,
                                      @RequestParam(name = "image") MultipartFile image,
                                      @RequestParam(name = "category") String category,
                                      @RequestParam(name = "description") String description) {
        ModelAndView modelAndView = new ModelAndView();
        if (image.isEmpty()) {
            modelAndView.setViewName("redirect:/add-product");
            modelAndView.addObject("errorMessage", "You didn't choose the image");
            modelAndView.addObject("options", categoryService.findCategoriesByHierarchyNumber("2"));
            modelAndView.addObject("sizes", new AddProductForm());
            return modelAndView;
        } else {
            createImagesDirectoryIfNeeded();
            modelAndView.setViewName("redirect:/home");
            uploadImage(name, image);
            Set<Size> sizeSet = new HashSet<>();
            if (sizes == null || sizes.getSizes().isEmpty()) {
                sizeSet.add(new Size("All", "0"));
            } else {
                for (Size size : sizes.getSizes()) {
                    sizeSet.add(new Size(size.getSize(), size.getAvailableNumber()));
                }
            }
            Attribute attribute = new Attribute(sizeSet, description);
            Product product = new Product(name, price, "image/" + name, categoryService.findCategoryById(category), attribute);
            productService.saveProduct(product);
            return modelAndView;
        }
    }

    @RequestMapping(value = "/edit/{id}")
    public ModelAndView showEditPage(@PathVariable(name = "id") String id) {
        ModelAndView modelAndView = new ModelAndView("edit");
        modelAndView.addObject("product", productService.findProductById(Long.parseLong(id)));
        modelAndView.addObject("sizes", new AddProductForm());
        modelAndView.addObject("options", categoryService.findCategoriesByHierarchyNumber("2"));

        return modelAndView;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public ModelAndView editProduct(@ModelAttribute("sizes") AddProductForm sizes,
                                    BindingResult result,
                                    @PathVariable(name = "id") String id,
                                    @RequestParam(name = "name") String name,
                                    @RequestParam(name = "price") String price,
                                    @RequestParam(name = "image") MultipartFile image,
                                    @RequestParam(name = "category") String category,
                                    @RequestParam(name = "description") String description) {
        ModelAndView modelAndView = new ModelAndView();
        Product product = productService.findProductById(Long.parseLong(id));
        if (!image.isEmpty()) {
            createImagesDirectoryIfNeeded();
            uploadImage(name, image);
        }
        Set<Size> sizeSet = new HashSet<>();
        if (sizes == null || sizes.getSizes().isEmpty()) {
            sizeSet.add(new Size("All", "0"));
        } else {
            for (Size size : sizes.getSizes()) {
                sizeSet.add(new Size(size.getSize(), size.getAvailableNumber()));
            }
        }
        product.getAttributes().setDescription(description);
        product.getAttributes().setSizes(sizeSet);
        product.setName(name);
        product.setPrice(price);
        product.setCategory(categoryService.findCategoryById(category));
        product.setImage("/image/" + name);
        productService.saveProduct(product);
        modelAndView.setViewName("redirect:/catalog/" + id);
        return modelAndView;
    }

    @RequestMapping(value = "/orders")
    public ModelAndView showOrdersPage(@RequestParam(name = "orderStatus", required = false) String status) {
        ModelAndView modelAndView = new ModelAndView("ordersHistoryTest");
        if (status == null) status = "all";
        modelAndView.addObject("types", orderService.findOrderStatusTypes());
        if (status.equals("all")) modelAndView.addObject("orders", orderService.findAllOrders());
        else modelAndView.addObject("orders", orderService.findOrderByStatusType(status));

        return modelAndView;
    }

    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public String changeOrderStatus(@RequestParam(name = "id") String id,
                                    @RequestParam(name = "status") String status) {
        Order order = orderService.findOrderById(id);
        order.setOrderStatus(status);
        if (!status.equals(OrderStatusEnum.AWAITING_PAYMENT.name())) order.getPayment().setPaymentStatus(PaymentStatusEnum.PAID.name());
        orderService.saveOrder(order);
        return "redirect:/admin/orders";
    }

    private void createImagesDirectoryIfNeeded() {
        if (!IMAGES_DIR.exists()) {
            IMAGES_DIR.mkdirs();
        }
    }

    private void uploadImage(String name, MultipartFile file) {
        try {
            File image = new File(IMAGES_DIR_ABSOLUTE_PATH + name);
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(image));
            stream.write(file.getBytes());
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
