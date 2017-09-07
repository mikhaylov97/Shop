package com.tsystems.shop.controller;

import com.tsystems.shop.model.Category;
import com.tsystems.shop.model.Product;
import com.tsystems.shop.model.User;
import com.tsystems.shop.model.dto.BagProductDto;
import com.tsystems.shop.model.dto.ProductDto;
import com.tsystems.shop.model.enums.UserRoleEnum;
import com.tsystems.shop.service.api.*;
import com.tsystems.shop.util.ImageSourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
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

@Controller
//@SessionAttributes(value = "bag")
public class CommonController {

    @Autowired
    private
    UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private BagService bagService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SizeService sizeService;

//    @ModelAttribute("bag")
//    List<Product> createBag() { return new ArrayList<>(); }

    @RequestMapping(value = "/")
    public ModelAndView redirectToHomePage() {
        ModelAndView modelAndView = new ModelAndView("title");
        return modelAndView;
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
        authenticateUserAndSetSession(email, password, request);
        return "redirect:/home";
    }

    @RequestMapping(value = "/bag/add/{id}", method = RequestMethod.POST)
    public String addToBag(@RequestParam(name = "amount") String amount,
                           @RequestParam(name = "sizeId") String sizeId,
                           @PathVariable(name = "id") String id, HttpServletRequest request) {
        Product product = productService.findProductById(Long.parseLong(id), false);
        Object bag = request.getSession().getAttribute("bag");
        if (bag == null) {
            List<BagProductDto> bagProducts = new ArrayList<>();
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

    @RequestMapping(value = "/bag")
    public ModelAndView showBagPage(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("bag");
        modelAndView.addObject("bag", request.getSession().getAttribute("bag"));
        modelAndView.addObject("totalPrice",
                bagService.figureOutTotalPrice((List<BagProductDto>)request.getSession().getAttribute("bag")));
        return modelAndView;
    }

    @RequestMapping(value = "/get/amount", method = RequestMethod.POST)
    public @ResponseBody int getAvailableAmountOfSize(@RequestParam(name = "sizeId") String sizeId) {
        return productService.findAvailableAmountOfSize(Long.parseLong(sizeId));
    }

    @RequestMapping(value = "/bag/delete/{id}", method = RequestMethod.POST)
    public String deleteFromBag(@RequestParam(name = "sizeId") String sizeId,
                                @PathVariable(value = "id") String id,
                                HttpSession session) {
        List<BagProductDto> bag = (List<BagProductDto>) session.getAttribute("bag");
        bagService.deleteFromBag(Long.parseLong(id),
                Long.parseLong(sizeId),
                bag);
        return "redirect:/bag";
    }

//    @RequestMapping(value = "/catalog")
//    public ModelAndView showCatalogPage() {
//        ModelAndView modelAndView = new ModelAndView("catalog");
//        modelAndView.addObject("catalog",productService.findProductsByCategory(productService.));
//
//        return modelAndView;
//    }

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

    @RequestMapping(value = "/404")
    public String show404Page() {
        return "nothing-found";
    }

    @RequestMapping(value = "/javascript/disabled")
    public String showDisabledJavascriptPage() {
        return "javascript-disabled";
    }

    private void authenticateUserAndSetSession(String email, String password, HttpServletRequest request) {
        // generate session if one doesn't exist
        request.getSession();

        UserDetails user = userDetailsService.loadUserByUsername(email);
        Authentication authenticatedUser = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public ModelAndView getCatalogOnlyItems(@RequestParam(value = "cost") String cost,
                                                          @RequestParam(value = "size") String size,
                                                          @RequestParam(value = "category") String categoryId) {
        ModelAndView modelAndView = new ModelAndView("catalog-only-items");
        modelAndView.addObject("catalog", productService.filterProductsByCostAndSize(cost, size, categoryId));
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

//    @RequestMapping(value = "/")

    @RequestMapping(value = "/products/json")
    public @ResponseBody List<ProductDto> getProductsJson(@RequestParam(name = "term") String term) {
        return productService.findProductsByTerm(term, false);
    }

    private boolean isCurrentAuthenticationAnonymous() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication instanceof AnonymousAuthenticationToken;
    }

    public void setBagSizeToModelAndView(HttpSession session, ModelAndView mav) {
        Object bag = session.getAttribute("bag");
        if (bag == null)  {
            session.setAttribute("bag", new ArrayList<BagProductDto>());
            mav.addObject("bagSize", 0);
        } else {
            mav.addObject("bagSize", ((List<BagProductDto>) bag).size());
        }
    }

    @RequestMapping(value = "/image/{imageId}")
    @ResponseBody
    public byte[] getImage(@PathVariable(value = "imageId") String imageId) throws IOException {
       // createImagesDirectoryIfNeeded();

        File serverFile = new File(ImageSourceUtil.getImagesDirectoryAbsolutePath() + imageId); //+ ".jpg");

        return Files.readAllBytes(serverFile.toPath());
    }
}
