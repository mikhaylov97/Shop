package com.tsystems.shop.controller;

import com.tsystems.shop.model.*;
import com.tsystems.shop.model.dto.SizesDto;
import com.tsystems.shop.model.enums.OrderStatusEnum;
import com.tsystems.shop.model.enums.PaymentStatusEnum;
import com.tsystems.shop.service.api.CategoryService;
import com.tsystems.shop.service.api.OrderService;
import com.tsystems.shop.service.api.ProductService;
import com.tsystems.shop.service.api.UserService;
import com.tsystems.shop.util.ByteArrayConverterUtil;
import com.tsystems.shop.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/products/add")
    public ModelAndView showAddProductPage() {
        ModelAndView modelAndView = new ModelAndView("add-product");
        modelAndView.addObject("options", categoryService.findCategoriesByHierarchyNumber("2"));
        modelAndView.addObject("sizes", new SizesDto());
        return modelAndView;
    }

    @RequestMapping(value = "/products/add/upload", method = RequestMethod.POST)
    public ModelAndView uploadProduct(@ModelAttribute("sizes") SizesDto sizes,
                                      BindingResult result,
                                      @RequestParam(name = "name") String name,
                                      @RequestParam(name = "price") String price,
                                      @RequestParam(name = "image") MultipartFile image,
                                      @RequestParam(name = "category") String category,
                                      @RequestParam(name = "description") String description) {
        ModelAndView modelAndView = new ModelAndView();
        if (image.isEmpty()) {
            modelAndView.setViewName("add-product");
            modelAndView.addObject("oldName", name);
            modelAndView.addObject("oldPrice", price);
            modelAndView.addObject("oldDescription", description);
            modelAndView.addObject("oldSizes", sizes.getSizes());
            modelAndView.addObject("oldCategory", category);
            modelAndView.addObject("errorMessage", "You didn't choose the image");
            modelAndView.addObject("options", categoryService.findCategoriesByHierarchyNumber("2"));
            modelAndView.addObject("sizes", new SizesDto());
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
        modelAndView.addObject("sizes", new SizesDto());
        Category currentCategory = categoryService
                .findCategoryById(String.valueOf(productService.findProductById(Long.parseLong(id)).getCategory().getId()));
        Category parentCategory = currentCategory.getParent();
        if (parentCategory.getId() == 1) modelAndView.addObject("isMensActive", true);
        if (parentCategory.getId() == 2) modelAndView.addObject("isWomensActive", true);
        modelAndView.addObject("options", categoryService.findChilds(parentCategory));
        modelAndView.addObject("activeOptionId", currentCategory.getId());

        return modelAndView;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public ModelAndView editProduct(@ModelAttribute("sizes") SizesDto sizes,
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
    public ModelAndView showOrdersPage() {
        ModelAndView modelAndView = new ModelAndView("management");
        modelAndView.addObject("ordersActive", orderService.findActiveOrders());
        modelAndView.addObject("ordersDone", orderService.findDoneOrders());
        modelAndView.addObject("orderStatuses", OrderStatusEnum.values());
        modelAndView.addObject("paymentStatuses", PaymentStatusEnum.values());

        return modelAndView;
    }

    @RequestMapping(value = "/orders/save/{id}", method = RequestMethod.POST)
    public ModelAndView saveNewOrderInstance(@PathVariable(name = "id") String id,
                                             @RequestParam(name = "payment-status") String paymentStatus,
                                             @RequestParam(name = "order-status") String orderStatus) {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/orders");
        Order order = orderService.findOrderById(id);
        if (paymentStatus.equalsIgnoreCase(PaymentStatusEnum.PAID.name())
                && orderStatus.equalsIgnoreCase(OrderStatusEnum.DELIVERED.name())) {
            order.getPayment().setPaymentStatus(paymentStatus);
            order.setOrderStatus(OrderStatusEnum.DONE.name());
            order.setDate(DateUtil.getLocalDateNowInDtfFormat());
        } else {
            order.getPayment().setPaymentStatus(paymentStatus);
            order.setOrderStatus(orderStatus);
        }
        orderService.saveOrder(order);

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

    @RequestMapping(value = "/statistics")
    public ModelAndView showStatisticsPage() {
        ModelAndView modelAndView = new ModelAndView("statistics");
        modelAndView.addObject("topUsers", userService.findTop10UsersDto());
        modelAndView.addObject("topProducts", productService.findTop10ProductsDto());
        modelAndView.addObject("incomePerWeek", orderService.findIncomePerLastWeek());
        modelAndView.addObject("incomePerMonth", orderService.findIncomePerLastMonth());

        return modelAndView;
    }

    @RequestMapping(value = "/statistics/download/pdf")
    public ModelAndView showOrDownloadStatisticsPdf() throws IOException {
        ModelAndView modelAndView = new ModelAndView("pdfView");
        modelAndView.addObject("listProducts", productService.findTop10ProductsDto());
        modelAndView.addObject("listUsers", userService.findTop10UsersDto());
        modelAndView.addObject("incomePerWeek", orderService.findIncomePerLastWeek());
        modelAndView.addObject("incomePerMonth", orderService.findIncomePerLastMonth());
        Map<Long, Byte[]> imageMap = new HashMap<>();
        for (Product product : productService.findTop10Products()) {
            File serverFile = new File(IMAGES_DIR_ABSOLUTE_PATH + product.getName());
            imageMap.put(product.getId(), ByteArrayConverterUtil.convertBytes(Files.readAllBytes(serverFile.toPath())));
        }
        modelAndView.addObject("imagesMap", imageMap);
        // return a view which will be resolved by an excel view resolver
        return modelAndView;
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
