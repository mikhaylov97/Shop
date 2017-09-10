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
import com.tsystems.shop.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.jms.TextMessage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Secured controller for admins(including super admin).
 * There they can change their own account settings (Name, surname, address data, phone, password).
 * Users also can see their order history.
 * Admins instead of this have a list of different actions:
 * 1) Manage categories.
 * 2) Manage orders.
 * 3) Statistics.
 * 4) Add product.
 * Super admin has both of simple admin and super admin roles. He has access for actions
 * above and also has option - manage admins.
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private final JmsTemplate jmsTemplate;

    private final CategoryService categoryService;

    private final ProductService productService;

    private final OrderService orderService;

    private final UserService userService;

    @Autowired
    public AdminController(JmsTemplate jmsTemplate, CategoryService categoryService,
                           ProductService productService, OrderService orderService,
                           UserService userService) {
        this.jmsTemplate = jmsTemplate;
        this.categoryService = categoryService;
        this.productService = productService;
        this.orderService = orderService;
        this.userService = userService;
    }

    @RequestMapping(value = "/products/add")
    public ModelAndView showAddProductPage() {
        ModelAndView modelAndView = new ModelAndView("add-product");
        modelAndView.addObject("options", categoryService.findCategoriesByHierarchyNumber("2", true));
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
            modelAndView.addObject("options", categoryService.findCategoriesByHierarchyNumber("2", true));
            modelAndView.addObject("sizes", new SizesDto());
            return modelAndView;
        } else {
            ImageUtil.createImagesDirectoryIfNeeded();
            modelAndView.setViewName("redirect:/home");
            Set<Size> sizeSet = new HashSet<>();
            if (sizes.getSizes() != null && !sizes.getSizes().isEmpty()) {
                for (Size size : sizes.getSizes()) {
                    sizeSet.add(new Size(size.getSize(), size.getAvailableNumber()));
                }
            }
            Attribute attribute = new Attribute(sizeSet, description);
            Product product = new Product(name, price, "image/" + name, categoryService.findCategoryById(category, true), attribute);
            product = productService.saveProduct(product);
            ImageUtil.uploadImage(String.valueOf(product.getId()), image);
            return modelAndView;
        }
    }

    @RequestMapping(value = "/edit/{id}")
    public ModelAndView showEditPage(@PathVariable(name = "id") String id) {
        ModelAndView modelAndView = new ModelAndView("edit");
        modelAndView.addObject("product", productService.findProductById(Long.parseLong(id), true));
        modelAndView.addObject("sizes", new SizesDto());
        Category currentCategory = categoryService
                .findCategoryById(String.valueOf(productService.findProductById(Long.parseLong(id), true).getCategory().getId()), true);
        Category parentCategory = currentCategory.getParent();
        if (parentCategory.getId() == 1) modelAndView.addObject("isMensActive", true);
        if (parentCategory.getId() == 2) modelAndView.addObject("isWomensActive", true);
        modelAndView.addObject("options", categoryService.findChilds(parentCategory, true));
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
        Product product = productService.findProductById(Long.parseLong(id), true);
        if (!image.isEmpty()) {
            ImageUtil.createImagesDirectoryIfNeeded();
            ImageUtil.uploadImage(id, image);
        }
        Set<Size> sizeSet = new HashSet<>();
        if (sizes.getSizes() != null && !sizes.getSizes().isEmpty()) {
            for (Size size : sizes.getSizes()) {
                sizeSet.add(new Size(size.getSize(), size.getAvailableNumber()));
            }
        }
        product.getAttributes().setDescription(description);
        product.getAttributes().setSizes(sizeSet);
        product.setName(name);
        product.setPrice(price);
        product.setCategory(categoryService.findCategoryById(category, true));
        product.setImage("/image/" + id);
        productService.saveProduct(product);
        modelAndView.setViewName("redirect:/catalog/" + id);

        try {
            sendMessage("advertising.stand", "update");
        } catch (JmsException e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    @RequestMapping(value = "/orders")
    public ModelAndView showOrdersPage() {
        ModelAndView modelAndView = new ModelAndView("management");
        modelAndView.addObject("ordersActive", orderService.findActiveOrders());
        modelAndView.addObject("ordersDone", orderService.findDoneOrders());
        modelAndView.addObject("orderStatuses",
                Arrays.stream(OrderStatusEnum.values()).map(Enum::toString).collect(Collectors.toList()));
        modelAndView.addObject("paymentStatuses",
                Arrays.stream(PaymentStatusEnum.values()).map(Enum::toString).collect(Collectors.toList()));

        return modelAndView;
    }

    @RequestMapping(value = "/orders/save/{id}", method = RequestMethod.POST)
    public ModelAndView saveNewOrderInstance(@PathVariable(name = "id") String id,
                                             @RequestParam(name = "payment-status") String paymentStatus,
                                             @RequestParam(name = "order-status") String orderStatus) {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/orders");
        Order order = orderService.findOrderById(id);
        if (paymentStatus.equalsIgnoreCase(PaymentStatusEnum.PAID.toString())
                && orderStatus.equalsIgnoreCase(OrderStatusEnum.DELIVERED.toString())) {
            order.getPayment().setPaymentStatus(paymentStatus);
            order.setOrderStatus(OrderStatusEnum.DONE.toString());
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
        if (!status.equals(OrderStatusEnum.AWAITING_PAYMENT.toString())) order.getPayment().setPaymentStatus(PaymentStatusEnum.PAID.toString());
        orderService.saveOrder(order);
        return "redirect:/admin/orders";
    }

    @RequestMapping(value = "/statistics")
    public ModelAndView showStatisticsPage() {
        ModelAndView modelAndView = new ModelAndView("statistics");
        modelAndView.addObject("topUsers", userService.findTop10UsersDto());
        modelAndView.addObject("topProducts", productService.findTop10ProductsDto(true));
        modelAndView.addObject("incomePerWeek", orderService.findIncomePerLastWeek());
        modelAndView.addObject("incomePerMonth", orderService.findIncomePerLastMonth());

        return modelAndView;
    }

    @RequestMapping(value = "/statistics/download/pdf")
    public ModelAndView showOrDownloadStatisticsPdf() throws IOException {
        ModelAndView modelAndView = new ModelAndView("pdfView");
        modelAndView.addObject("listProducts", productService.findTop10ProductsDto(true));
        modelAndView.addObject("listUsers", userService.findTop10UsersDto());
        modelAndView.addObject("incomePerWeek", orderService.findIncomePerLastWeek());
        modelAndView.addObject("incomePerMonth", orderService.findIncomePerLastMonth());
        Map<Long, Byte[]> imageMap = new HashMap<>();
        for (Product product : productService.findTop10Products(true)) {
            File serverFile = new File(ImageUtil.getImagesDirectoryAbsolutePath() + product.getId());
            imageMap.put(product.getId(), ByteArrayConverterUtil.convertBytes(Files.readAllBytes(serverFile.toPath())));
        }
        modelAndView.addObject("imagesMap", imageMap);
        // return a view which will be resolved by an excel view resolver
        return modelAndView;
    }

    @RequestMapping(value = "/categories")
    public ModelAndView showCategoriesManagementPage() {
        ModelAndView modelAndView = new ModelAndView("manage-categories");
        modelAndView.addObject("categories", categoryService.findChildsDtoById(1, true));

        return modelAndView;
    }

    @RequestMapping(value = "/categories/add", method = RequestMethod.POST)
    public @ResponseBody String saveNewCategory(@RequestParam(name = "name") String name,
                                  @RequestParam(name = "parent") String id) {
        if (categoryService.checkIsCategoryNameFree(name, categoryService.findCategoryById(id, true))) {
            Category category = new Category(name, "2", categoryService.findCategoryById(id, true));
            categoryService.saveNewCategory(category);
            return "saved";
        } else {
            return "declined";
        }
    }

    @RequestMapping(value = "/categories/hide/{id}", method = RequestMethod.POST)
    public @ResponseBody String hideCategory(@PathVariable(name = "id") String id) {
        categoryService.hideCategory(categoryService.findCategoryById(id, true));
        return "hidden";
    }

    @RequestMapping(value = "/categories/show/{id}", method = RequestMethod.POST)
    public @ResponseBody String showCategory(@PathVariable(name = "id") String id) {
        categoryService.showCategory(categoryService.findCategoryById(id, true));
        return "shown";
    }

    @RequestMapping(value = "/get/categories", method = RequestMethod.POST)
    public ModelAndView getCategoriesList(@RequestParam(name = "active") String active) {
        ModelAndView modelAndView = new ModelAndView("manage-categories-only-items");
        switch (active) {
            case "mens":
                modelAndView.addObject("categories", categoryService.findChildsDtoById(1, true));
                break;
            case "womens":
                modelAndView.addObject("categories", categoryService.findChildsDtoById(2, true));
                break;
        }

        return modelAndView;
    }

    @RequestMapping(value = "/hide/{id}", method = RequestMethod.POST)
    public ModelAndView hideProduct(@PathVariable(name = "id") String id,
                                    @RequestParam(name = "redirect") String redirect) {
        ModelAndView modelAndView = new ModelAndView("redirect:/catalog/" + id);
        productService.hideProduct(productService.findProductById(Long.parseLong(id), true));

        return modelAndView;
    }

    @RequestMapping(value = "/show/{id}", method = RequestMethod.POST)
    public ModelAndView showProduct(@PathVariable(name = "id") String id,
                                    @RequestParam(name = "redirect") String redirect) {
        ModelAndView modelAndView = new ModelAndView("redirect:/catalog/" + id);
        productService.showProduct(productService.findProductById(Long.parseLong(id), true));

        return modelAndView;
    }

    public void sendMessage(final String queueName, final String message) throws JmsException {
        jmsTemplate.send(queueName, session -> {
            TextMessage msg = session.createTextMessage();
            msg.setText(message);
            return msg;
        });
    }


}
