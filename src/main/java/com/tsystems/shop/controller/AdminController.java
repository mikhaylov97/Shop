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
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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

    /**
     * Apache log4j object is used to logging all important info.
     */
    private static final Logger log = Logger.getLogger(AdminController.class);

    /**
     * Category service. See {@link com.tsystems.shop.service.impl.CategoryServiceImpl}.
     * It is necessary for working with shop categories.
     */
    private final CategoryService categoryService;

    /**
     * Product service. See {@link com.tsystems.shop.service.impl.ProductServiceImpl}.
     * It is necessary for working with products.
     */
    private final ProductService productService;

    /**
     * Order service. See {@link com.tsystems.shop.service.impl.OrderServiceImpl}.
     * It is necessary for working with orders.
     *
     */
    private final OrderService orderService;

    /**
     * User service. See {@link com.tsystems.shop.service.impl.UserServiceImpl}.
     * It is necessary for working with users.
     */
    private final UserService userService;

    /**
     * Injecting different services into this controller by spring tools.
     * @param categoryService - is our service which provide API to work with categories and DB.
     * @param productService - is our service which provide API to work with products and DB.
     * @param orderService - is our service which provide API to work with orders and DB.
     * @param userService - is our service which provide API to work with users and DB.
     */
    @Autowired
    public AdminController(CategoryService categoryService,
                           ProductService productService, OrderService orderService,
                           UserService userService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.orderService = orderService;
        this.userService = userService;
    }

    /**
     * This method shows page where admin can add new product to the shop by filling all important data.
     * @return ModelAndView object with add-product.jsp view.
     */
    @RequestMapping(value = "/products/add")
    public ModelAndView showAddProductPage() {
        //view
        ModelAndView modelAndView = new ModelAndView("add-product");

        //model
        modelAndView.addObject("options", categoryService.findCategoriesByHierarchyNumber("2", true));
        modelAndView.addObject("sizes", new SizesDto());

        //log
        User user = userService.findUserFromSecurityContextHolder();
        log.info(user.getEmail() + " with " + user.getRole() + " has visited page for adding new products.");

        return modelAndView;
    }

    /**
     * POST request. This method tries to upload new product with all necessary parameters.
     * They will be described below.
     * @param sizes set with all available sizes for current product.
     * @param name of the future product.
     * @param price of the future product.
     * @param image path of the future product.
     * @param category where future product will be stored.
     * @param description of the future product.
     * @return ModelAndView object with add-product.jsp view.
     */
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

            //log
            User user = userService.findUserFromSecurityContextHolder();
            log.info(user.getEmail() + " with " + user.getRole() + " has tried to add new product.");

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

            //log
            //log
            User user = userService.findUserFromSecurityContextHolder();
            log.info(user.getEmail() + " with " + user.getRole() + " has added new product. Name = \'" + name + "\'.");

            return modelAndView;
        }
    }

    /**
     * This method shows page where admins can edit every product int the shop.
     * @param id of the product.
     * @return ModelAndView object with edit.jsp view.
     */
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

        //log
        User user = userService.findUserFromSecurityContextHolder();
        log.info(user.getEmail() + " with " + user.getRole() + " has tried to edit "
                + productService.findProductById(Long.parseLong(id), true).getName() + " product.");

        return modelAndView;
    }

    /**
     * POST request. Method should receive all necessary data of the product(any old auto-filled data
     * or any changed by admin data). If every field is correct, method will update product data and save to the DB.
     * @param sizes of the product which now is available for selling.
     * @param id of the product.
     * @param name with old or updated value.
     * @param price - with old or updated value.
     * @param image - image with old or updated value.
     * @param category of the product.
     * @param description - with old or updated value.
     * @return ModelAndView object with redirecting view.
     */
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
        Set<Size> oldSet = product.getAttributes().getSizes();
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
        oldSet.removeAll(product.getAttributes().getSizes());
        try {
            productService.deleteSizesSet(oldSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        modelAndView.setViewName("redirect:/catalog/" + id);
        //log
        User user = userService.findUserFromSecurityContextHolder();
        log.info(user.getEmail() + " with " + user.getRole() + " has updated product. New name - \'" + name + "\'.");

        try {
            productService.sendUpdateMessageToJmsServer();
            //log
            log.info("System has sent message to ActiveMQ.");
        } catch (JmsException e) {
            //log
            log.info("System has tried to send message to ActiveMQ server, but something was wrong.", e);
        }
        return modelAndView;
    }

    /**
     * This method shows page with all existed orders.
     * @return ModelAndView object with management.jsp view.
     */
    @RequestMapping(value = "/orders")
    public ModelAndView showOrdersPage() {
        //view
        ModelAndView modelAndView = new ModelAndView("management");

        //model
        modelAndView.addObject("orders", orderService.findActiveOrders());
        //modelAndView.addObject("ordersDone", orderService.findDoneOrders());
        modelAndView.addObject("orderStatuses",
                Arrays.stream(OrderStatusEnum.values()).map(Enum::toString).collect(Collectors.toList()));
        modelAndView.addObject("paymentStatuses",
                Arrays.stream(PaymentStatusEnum.values()).map(Enum::toString).collect(Collectors.toList()));

        //log
        User user = userService.findUserFromSecurityContextHolder();
        log.info(user.getEmail() + " with " + user.getRole() + " has visited orders management page.");

        return modelAndView;
    }

    /**
     * POST request. Method tries to update order status to chosen by admin new value.
     * Admin can change payment status and order status. When payment status is PAID and
     * order status is delivered then such order becomes DONE and must be added to archive.
     * @param id of the order.
     * @param paymentStatus chosen by admin.
     * @param orderStatus chosen by admin.
     * @return ModelAndView object with redirecting view.
     */
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
        //log
        User user = userService.findUserFromSecurityContextHolder();
        log.info(user.getEmail() + " with " + user.getRole() + " has changed order(ID=" + id + ") status.");

        return modelAndView;
    }

    /**
     * Method shows statistics page for admin. There admin can see
     * shop income for the last week and for the last month. Also there will
     * be available top 10 products and top 10 users(The more money user spent the higher rating he has)
     * @return ModelAndView object with statistics.jsp view.
     */
    @RequestMapping(value = "/statistics")
    public ModelAndView showStatisticsPage() {
        ModelAndView modelAndView = new ModelAndView("statistics");
        modelAndView.addObject("topUsers", userService.findTop10UsersDto());
        modelAndView.addObject("topProducts", productService.findTop10ProductsDto(true));
        modelAndView.addObject("incomePerWeek", orderService.findIncomePerLastWeek());
        modelAndView.addObject("incomePerMonth", orderService.findIncomePerLastMonth());

        //log
        User user = userService.findUserFromSecurityContextHolder();
        log.info(user.getEmail() + " with " + user.getRole() + " has visited statistics page.");

        return modelAndView;
    }

    /**
     * This Method allows to see and if admin wants to download document
     * with all statistics in pdf format.
     * @return ModelAndView object which represents pdf document.
     * @throws IOException in cases when document is broken.
     */
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

        //log
        User user = userService.findUserFromSecurityContextHolder();
        log.info(user.getEmail() + " with " + user.getRole() + " has gotten statistics pdf document.");

        return modelAndView;
    }

    /**
     * This method shows categories management page. There admin can add new category
     * and hide or make category and products inside visible again.
     * @return ModelAndView object with manage-categories.jsp view.
     */
    @RequestMapping(value = "/categories")
    public ModelAndView showCategoriesManagementPage() {
        //view
        ModelAndView modelAndView = new ModelAndView("manage-categories");

        //model
        modelAndView.addObject("categories", categoryService.findChildsDtoById(1, true));

        //log
        User user = userService.findUserFromSecurityContextHolder();
        log.info(user.getEmail() + " with " + user.getRole() + " has visited categories management page.");

        return modelAndView;
    }

    /**
     * This method shows archived orders page.
     * @return ModelAndView object with manage-categories.jsp view.
     */
    @RequestMapping(value = "/archive")
    public ModelAndView showArchivePage() {
        //view
        ModelAndView modelAndView = new ModelAndView("archive");

        //model
        modelAndView.addObject("orders",
                orderService.findDoneOrders());

        //log
        User user = userService.findUserFromSecurityContextHolder();
        log.info(user.getEmail() + " with " + user.getRole() + " has visited archive page.");

        return modelAndView;
    }

    /**
     * POST request. This method allows admins to add new categories.
     * If all filled inputs are correct and name is free, such category will be created.
     * @param name of the future category.
     * @param id of the parent category.
     * @return answer in JSON format for the requester.
     */
    @RequestMapping(value = "/categories/add", method = RequestMethod.POST)
    public @ResponseBody String saveNewCategory(@RequestParam(name = "name") String name,
                                                @RequestParam(name = "parent") String id) {
        if (categoryService.checkIsCategoryNameFree(name, categoryService.findCategoryById(id, true))) {
            Category category = new Category(name, "2", categoryService.findCategoryById(id, true));
            categoryService.saveNewCategory(category);

            //log
            User user = userService.findUserFromSecurityContextHolder();
            log.info(user.getEmail() + " with " + user.getRole() + " has added new category.");

            return "saved";
        } else {
            //log
            User user = userService.findUserFromSecurityContextHolder();
            log.info(user.getEmail() + " with " + user.getRole() + " has tried to add new category." +
                    "But such name of category is already exists");

            return "declined";
        }
    }

    /**
     * POST request. Method allows admins to hide any category with products inside.
     * Guests and authorised users will not see such categories and products.
     * But admins can see them and can make them visible again.
     * @param id of the category that must be hidden.
     * @return answer in JSON format for the requester.
     */
    @RequestMapping(value = "/categories/hide/{id}", method = RequestMethod.POST)
    public @ResponseBody String hideCategory(@PathVariable(name = "id") String id) {
        categoryService.hideCategory(categoryService.findCategoryById(id, true));

        //log
        User user = userService.findUserFromSecurityContextHolder();
        log.info(user.getEmail() + " with " + user.getRole() + " has hidden \'"
                + categoryService.findCategoryById(id, true).getName() + "\' category.");

        return "hidden";
    }

    /**
     * POST request. Method allows admins to make hidden categories visible again.
     * Guests and authorised users after that is able to see these categories and products there.
     * @param id of the category that must become visible.
     * @return answer in JSON format for the requester.
     */
    @RequestMapping(value = "/categories/show/{id}", method = RequestMethod.POST)
    public @ResponseBody String showCategory(@PathVariable(name = "id") String id) {
        categoryService.showCategory(categoryService.findCategoryById(id, true));

        //log
        User user = userService.findUserFromSecurityContextHolder();
        log.info(user.getEmail() + " with " + user.getRole() + " has made \'"
                + categoryService.findCategoryById(id, true).getName() + "\' category visible.");

        return "shown";
    }

    /**
     * POST request. Allows authorised users(admins in our case) to get categories list by parent name.
     * @param active - parent category name.
     * @return ModelAndView object which represents fragment of the page(manage-categories-only-items.jsp).
     */
    @RequestMapping(value = "/get/categories", method = RequestMethod.POST)
    public ModelAndView getCategoriesList(@RequestParam(name = "active") String active) {
        ModelAndView modelAndView = new ModelAndView("manage-categories-only-items");
        if (active.equals("mens")) {
            modelAndView.addObject("categories", categoryService.findChildsDtoById(1, true));
        } else {
            modelAndView.addObject("categories", categoryService.findChildsDtoById(2, true));
        }

        return modelAndView;
    }

    /**
     * POST request. Method allow us to get filtered orders by
     * the next parameters: order date from, order date to, order status and payment status.
     * @return ModelAndView object which represents fragment (catalog-only-items.jsp) of the catalog page.
     */
    @RequestMapping(value = "/filter/{activeOrDone}", method = RequestMethod.POST)
    public ModelAndView getCatalogOnlyItems(@PathVariable(name = "activeOrDone") String type,
                                            @RequestParam(value = "date-from") String dateFrom,
                                            @RequestParam(value = "date-to") String dateTo,
                                            @RequestParam(value = "payment-status", required = false, defaultValue = "No matter") String paymentStatus,
                                            @RequestParam(value = "order-status", required = false, defaultValue = "No matter") String orderStatus) {
        ModelAndView modelAndView;
        if (type.equals("active")) {
            modelAndView = new ModelAndView("management-only-items");
            modelAndView.addObject("orders",
                    orderService.filterActiveOrdersByParameters(type, dateFrom, dateTo, paymentStatus, orderStatus));
            modelAndView.addObject("orderStatuses",
                    Arrays.stream(OrderStatusEnum.values()).map(Enum::toString).collect(Collectors.toList()));
            modelAndView.addObject("paymentStatuses",
                    Arrays.stream(PaymentStatusEnum.values()).map(Enum::toString).collect(Collectors.toList()));
        } else {
            modelAndView = new ModelAndView("archive-only-items");
            modelAndView.addObject("orders",
                    orderService.filterActiveOrdersByParameters(type, dateFrom, dateTo, paymentStatus, orderStatus));
        }
        return modelAndView;
    }

    /**
     * POST request. Method allows admins to hide any product in the shop.
     * All guests and authorised users will not see hidden products.
     * Admin always can make product visible again.
     * @param id of the product that must be hidden.
     * @return ModelAndView object with redirecting view to the current product page.
     */
    @RequestMapping(value = "/hide/{id}", method = RequestMethod.POST)
    public ModelAndView hideProduct(@PathVariable(name = "id") String id,
                                    @RequestParam(name = "redirect") String redirect) {
        ModelAndView modelAndView = new ModelAndView("redirect:/catalog/" + id);
        productService.hideProduct(productService.findProductById(Long.parseLong(id), true));

        return modelAndView;
    }

    /**
     * POST request. Method allows admins to make hidden products visible.
     * All guests and authorised users will see them.
     * Admin always can make product hidden again.
     * @param id of the product that must become visible.
     * @return ModelAndView object with redirecting view to the current product page.
     */
    @RequestMapping(value = "/show/{id}", method = RequestMethod.POST)
    public ModelAndView showProduct(@PathVariable(name = "id") String id,
                                    @RequestParam(name = "redirect") String redirect) {
        ModelAndView modelAndView = new ModelAndView("redirect:/catalog/" + id);
        productService.showProduct(productService.findProductById(Long.parseLong(id), true));

        return modelAndView;
    }


}
