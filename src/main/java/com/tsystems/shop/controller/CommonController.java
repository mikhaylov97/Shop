package com.tsystems.shop.controller;

import com.tsystems.shop.model.Category;
import com.tsystems.shop.model.Product;
import com.tsystems.shop.model.User;
import com.tsystems.shop.model.dto.BagProductDto;
import com.tsystems.shop.model.dto.ProductDto;
import com.tsystems.shop.model.enums.UserRoleEnum;
import com.tsystems.shop.service.api.*;
import com.tsystems.shop.util.ImageUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Unsecured controller for all users(means guests, admins and super-admins) while them unauthorised.
 * There they can browse catalogs, see detail product information, add them to bag
 * and ,of course, they can sign up or log in.
 */
@Controller
public class CommonController {

    /**
     * Apache log4j object is used to logging all important info.
     */
    private static final Logger log = Logger.getLogger(CommonController.class);


    /**
     * User service. It is necessary for working with users.
     */
    private final UserService userService;

    /**
     * Product service. It is necessary for working with products.
     */
    private final ProductService productService;

    /**
     * Bag service. It is necessary for working with bag.
     */
    private final BagService bagService;

    /**
     * User details service. It is necessary for working with security.
     */
    private final UserDetailsService userDetailsService;

    /**
     * Category service. It is necessary for working with categories.
     */
    private final CategoryService categoryService;

    /**
     * Size service. It is necessary for working with product sizes.
     */
    private final SizeService sizeService;

    /**
     * Injecting different services into this controller by spring tools.
     * @param userService - is our service which provide API to work with users and DB.
     * @param productService is our service which provide API to work with products and DB.
     * @param bagService is our service which provide API to work with bag.
     * @param categoryService is our service which provide API to work with categories and DB.
     * @param sizeService is our service which provide API to work with product sizes.
     */
    @Autowired
    public CommonController(UserService userService, UserDetailsService userDetailsService,
                            ProductService productService, BagService bagService,
                            CategoryService categoryService, SizeService sizeService) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.productService = productService;
        this.bagService = bagService;
        this.categoryService = categoryService;
        this.sizeService = sizeService;
    }

    @RequestMapping(value = "/")
    public ModelAndView redirectToHomePage() {
        return new ModelAndView("title");
    }

    @RequestMapping(value = "/home")
    public ModelAndView showHomePage() {
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("products", productService.findAllProducts(false));
        return modelAndView;
    }

    @RequestMapping(value = "/login")
    public String showLoginPage() {
        return "login";
    }

    @RequestMapping(value = "/signUp")
    public String showSignUpPage() {
        return "sign-up";
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public String signUp(@RequestParam(name = "email") String email,
                         @RequestParam(name = "name") String name,
                         @RequestParam(name = "surname") String surname,
                         @RequestParam(name = "password") String password,
                         HttpServletRequest request) {
        User user = new User(email, name, surname, password);
        userService.saveNewUser(user);
        authenticateUserAndSetSession(email, request);
        return "redirect:/account";
    }

    @RequestMapping(value = "/bag/add/{id}", method = RequestMethod.POST)
    public String addToBag(@RequestParam(name = "amount") String amount,
                           @RequestParam(name = "sizeId") String sizeId,
                           @PathVariable(name = "id") String id, HttpServletRequest request) {
        Product product = productService.findProductById(Long.parseLong(id), false);
        Object bag = request.getSession().getAttribute("bag");
        if (bag == null) {
            ArrayList<BagProductDto> bagProducts = new ArrayList<>();
           bagService.addToBag(product.getId(),
                    Integer.parseInt(amount),
                    Long.parseLong(sizeId),
                   Long.parseLong(product.getPrice()),
                    bagProducts);
           request.getSession().setAttribute("bag", bagProducts);
        } else {
            bagService.addToBag(product.getId(),
                    Integer.parseInt(amount),
                    Long.parseLong(sizeId),
                    Long.parseLong(product.getPrice()),
                    (List<BagProductDto>) bag);
        }
        return "redirect:/home";
    }

    /**
     * This method shows bag page. There users can remove product from the bag or
     * edit amount/size of any one. Also returned page provide checkout button which will
     * redirect to checkout page if user is authorised or will ask to login(or sign up).
     * @return ModelAndView object with bag.jsp view.
     */
    @RequestMapping(value = "/bag")
    public ModelAndView showBagPage(HttpServletRequest request) {
        //view
        ModelAndView modelAndView = new ModelAndView("bag");
        //model
        modelAndView.addObject("bag", request.getSession().getAttribute("bag"));
        modelAndView.addObject("totalPrice",
                bagService.calculateTotalPrice((List<BagProductDto>)request.getSession().getAttribute("bag")));

        //log
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info(name + " has visited bag page.");

        return modelAndView;
    }

    /**
     * POST request. Method returns available amount of some product size.
     * @param sizeId which amount must be returned.
     * @return answer(available amount of product size by size ID) in JSON format for requester.
     */
    @RequestMapping(value = "/get/amount", method = RequestMethod.POST)
    public @ResponseBody int getAvailableAmountOfSize(@RequestParam(name = "sizeId") String sizeId) {
        return productService.findAvailableAmountOfSize(Long.parseLong(sizeId));
    }

    /**
     * POST request. Method allows to delete any product by ID from user's bag.
     * @param sizeId of the product that must be deleted from the bag.
     * @param id of the product.
     * @return
     */
    @RequestMapping(value = "/bag/delete/{id}", method = RequestMethod.POST)
    public String deleteFromBag(@RequestParam(name = "sizeId") String sizeId,
                                @PathVariable(value = "id") String id,
                                HttpSession session) {
        List<BagProductDto> bag = (List<BagProductDto>) session.getAttribute("bag");
        bagService.deleteFromBag(Long.parseLong(id),
                Long.parseLong(sizeId),
                bag);

        //log
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info(name + " has deleted product[ID=" + id + "] ffrom the bag.");

        return "redirect:/bag";
    }

    /**
     * Method shows catalog page with men clothes.
     * @return ModelAndView object with catalog.jsp view.
     */
    @RequestMapping(value = "/catalog/mens")
    public ModelAndView showMensPage() {
        ModelAndView modelAndView = new ModelAndView("catalog");
        boolean adminMode = false;
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .contains(new SimpleGrantedAuthority(UserRoleEnum.ROLE_ADMIN.name()))) {
            adminMode = true;
        }
        List<Product> products = productService.findProductsByCategory(categoryService.findCategoryById("1", adminMode), adminMode);
        modelAndView.addObject("catalog", products);
        modelAndView.addObject("options", categoryService.findChilds(categoryService.findCategoryById("1", adminMode), adminMode));
        modelAndView.addObject("isMensActive", true);
        modelAndView.addObject("sizes", sizeService.findSizesFromProducts(products));

        return modelAndView;
    }

    /**
     * Method shows catalog page with men clothes in certain category by ID of the last.
     * @param id of the category.
     * @return ModelAndView object with catalog.jsp page.
     */
    @RequestMapping(value = "/catalog/mens/{id}")
    public ModelAndView showSectionPageFromMens(@PathVariable(name = "id") String id) {
        ModelAndView modelAndView = new ModelAndView("catalog");
        boolean adminMode = false;
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .contains(new SimpleGrantedAuthority(UserRoleEnum.ROLE_ADMIN.name()))) {
            adminMode = true;
        }
        if (categoryService.findCategoryById(id, adminMode) == null) {
            return new ModelAndView("redirect:/404");
        }

        List<Product> products = productService.findProductsByCategory(categoryService.findCategoryById(id, adminMode), adminMode);
        modelAndView.addObject("catalog", products);
        modelAndView.addObject("options", categoryService.findChilds(categoryService.findCategoryById("1", adminMode), adminMode));
        modelAndView.addObject("isMensActive", true);
        modelAndView.addObject("activeOptionId", Long.parseLong(id));
        modelAndView.addObject("sizes", sizeService.findSizesFromProducts(products));

        return modelAndView;
    }

    /**
     * Method shows catalog page with women's clothes in certain category.
     * @param id of the category.
     * @return ModelAndView object with catalog.jsp view.
     */
    @RequestMapping(value = "/catalog/womens/{id}")
    public ModelAndView showSectionPageFromWomens(@PathVariable(name = "id") String id) {
        ModelAndView modelAndView = new ModelAndView("catalog");
        boolean adminMode = false;
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .contains(new SimpleGrantedAuthority(UserRoleEnum.ROLE_ADMIN.name()))) {
            adminMode = true;
        }
        if (categoryService.findCategoryById(id, adminMode) == null) {
            return new ModelAndView("redirect:/404");
        }

        List<Product> products = productService.findProductsByCategory(categoryService.findCategoryById(id, adminMode), adminMode);
        modelAndView.addObject("catalog", products);
        modelAndView.addObject("options", categoryService.findChilds(categoryService.findCategoryById("2", adminMode), adminMode));
        modelAndView.addObject("isWomensActive", true);
        modelAndView.addObject("activeOptionId", Long.parseLong(id));
        modelAndView.addObject("sizes", sizeService.findSizesFromProducts(products));

        return modelAndView;
    }

    /**
     * Method shows catalog page with all(products in all women's categories) women's clothes.
     * @return ModelAndView object with catalog.jsp view.
     */
    @RequestMapping(value = "/catalog/womens")
    public ModelAndView showWomensPage() {
        ModelAndView modelAndView = new ModelAndView("catalog");
        boolean adminMode = false;
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .contains(new SimpleGrantedAuthority(UserRoleEnum.ROLE_ADMIN.name()))) {
            adminMode = true;
        }

        List<Product> products = productService.findProductsByCategory(categoryService.findCategoryById("2", adminMode), adminMode);
        modelAndView.addObject("catalog", products);
        modelAndView.addObject("options", categoryService.findChilds(categoryService.findCategoryById("2", adminMode), adminMode));
        modelAndView.addObject("isWomensActive", true);
        modelAndView.addObject("sizes",sizeService.findSizesFromProducts(products));

        return modelAndView;
    }


    /**
     * Method shows product page by ID of the last.
     * @param id of the product that must be shown.
     * @return ModelAndView object with product.jsp view.
     */
    @RequestMapping(value = "/catalog/{id}")
    public ModelAndView showProductPage(@PathVariable(name = "id") long id) {
        ModelAndView modelAndView = new ModelAndView("product");
        boolean adminMode = false;
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .contains(new SimpleGrantedAuthority(UserRoleEnum.ROLE_ADMIN.name()))) {
            adminMode = true;
        }
        if (productService.findProductById(id, adminMode) == null) {
            return new ModelAndView("redirect:/404");
        }

        modelAndView.addObject("product", productService.findProductById(id, adminMode));
        Category currentCategory = categoryService.findCategoryById(String.valueOf(productService.findProductById(id, adminMode).getCategory().getId()), adminMode);
        Category parentCategory = currentCategory.getParent();
        if (parentCategory.getId() == 1) modelAndView.addObject("isMensActive", true);
        if (parentCategory.getId() == 2) modelAndView.addObject("isWomensActive", true);
        modelAndView.addObject("options", categoryService.findChilds(parentCategory, adminMode));
        modelAndView.addObject("activeOptionId", currentCategory.getId());
        return modelAndView;
    }

    /**
     * Method shows page with 404 error.
     * @return View - nothing-found.jsp.
     */
    @RequestMapping(value = "/404")
    public String show404Page() {
        return "nothing-found";
    }

    /**
     * Method shows error page when user disabled javascript on his browser.
     * Application depends on javascript because a lot of validation and
     * different actions is done by javascript. But the main problem is ajax request.
     * Because more than half of all business logic was constructed with the help of ajax requests.
     * @return View - javascript-disabled.jsp.
     */
    @RequestMapping(value = "/javascript/disabled")
    public String showDisabledJavascriptPage() {
        //log
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info(name + " has visited shop and was redirected to the javascript-disabled page.");

        return "javascript-disabled";
    }

    /**
     * POST request. Method allow us to get filtered product by
     * the next parameters: lower cost bound, upper cost bound, product size.
     * Also controller receives category hidden parameter. It is used for filtering product
     * by parameters above in certain category where user was when clicked filter button.
     * @param categoryId which products must be filtered.
     * @return ModelAndView object which represents fragment (catalog-only-items.jsp) of the catalog page.
     */
    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public ModelAndView getCatalogOnlyItems(@RequestParam(value = "cost-from") String lowerCostBound,
                                            @RequestParam(value = "cost-to") String upperCostBound,
                                            @RequestParam(value = "size") String size,
                                            @RequestParam(value = "category") String categoryId) {
        ModelAndView modelAndView = new ModelAndView("catalog-only-items");
        modelAndView.addObject("catalog", productService.filterProductsByCostAndSize(lowerCostBound, upperCostBound, size, categoryId));
        Category currentCategory = categoryService.findCategoryById(categoryId, false);
        Category parentCategory = currentCategory.getParent();
        if (parentCategory != null) {
            if (parentCategory.getId() == 1) modelAndView.addObject("isMensActive", true);
            if (parentCategory.getId() == 2) modelAndView.addObject("isWomensActive", true);
            modelAndView.addObject("options", categoryService.findChilds(parentCategory, false));
            modelAndView.addObject("activeOptionId", currentCategory.getId());
        } else {
            if (currentCategory.getId() == 1) modelAndView.addObject("isMensActive", true);
            if (currentCategory.getId() == 2) modelAndView.addObject("isWomensActive", true);
            modelAndView.addObject("options", categoryService.findChilds(currentCategory, false));
            modelAndView.addObject("activeOptionId", currentCategory.getId());
        }
        return modelAndView;
    }

    /**
     * Method returns found products in shop which names contains certain term entered by user.
     * This controller is used in search actions.
     * @param term filled by user.
     * @return List of products in Dto format {@link ProductDto} in JSON format for the requester.
     */
    @RequestMapping(value = "/products/json")
    public @ResponseBody List<ProductDto> getProductsJson(@RequestParam(name = "term") String term) {
        boolean adminMode = false;
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .contains(new SimpleGrantedAuthority(UserRoleEnum.ROLE_ADMIN.name()))) {
            adminMode = true;
        }
        return productService.findProductsByTerm(term, adminMode);
    }

    /**
     * Method allows us to get Image as a byte array which stored inside
     * server memory(in our case in tomcat home images folder).
     * @param imageId that must be returned.
     * @return byte representation of found image.
     */
    @RequestMapping(value = "/image/{imageId}")
    public @ResponseBody byte[] getImage(@PathVariable(value = "imageId") String imageId) throws IOException {

        File serverFile = new File(ImageUtil.getImagesDirectoryAbsolutePath() + imageId);

        return Files.readAllBytes(serverFile.toPath());
    }

    /**
     * Task of the method is to authenticate registered user and to give him simple user rights.
     * @param email of the registered user.
     */
    public void authenticateUserAndSetSession(String email, HttpServletRequest request) {
        // generate session if one doesn't exist
        request.getSession();

        UserDetails user = userDetailsService.loadUserByUsername(email);
        Authentication authenticatedUser = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
    }
}
