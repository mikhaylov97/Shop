package com.tsystems.shop.controller;

import com.tsystems.shop.model.BagProduct;
import com.tsystems.shop.model.Category;
import com.tsystems.shop.model.Product;
import com.tsystems.shop.model.User;
import com.tsystems.shop.model.enums.UserRoleEnum;
import com.tsystems.shop.service.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Controller
//@SessionAttributes(value = "bag")
public class CommonController {
    private static final String IMAGES = "images";
    private static final String TOMCAT_HOME_PROPERTY = "catalina.home";
    private static final String TOMCAT_HOME_PATH = System.getProperty(TOMCAT_HOME_PROPERTY);
    private static final String IMAGES_PATH = TOMCAT_HOME_PATH + File.separator + IMAGES;

    private static final File IMAGES_DIR = new File(IMAGES_PATH);
    private static final String IMAGES_DIR_ABSOLUTE_PATH = IMAGES_DIR.getAbsolutePath() + File.separator;

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
        modelAndView.addObject("products", productService.findAllProducts());
        return modelAndView;
    }

    @RequestMapping(value = "/login")
    public String showLoginPage() {
        return "login";
    }

    @RequestMapping(value = "/redirect")
    public String redirectToAccount() {
        if (isCurrentAuthenticationAnonymous()) {
            return "redirect:/login";
        }
        else {
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            String role = userService.findUserByEmail(userName).getRole();
            if (role.equals(UserRoleEnum.ROLE_USER.name())) return "redirect:/user";
            if (role.equals(UserRoleEnum.ROLE_ADMIN.name())
                    || role.equals(UserRoleEnum.ROLE_SUPER_ADMIN.name())) return "redirect:/admin/orders";
            return "redirect:/home";
        }
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


    //    @RequestMapping(value = "/addToBag/{id}")
//    public String addToBag(@PathVariable(name = "id") String id, @ModelAttribute("bag") List<Product> products) {
//        Product product = productService.findProductById(Long.parseLong(id));
//        products.add(product);
//        return "redirect:/home";
//    }
    @RequestMapping(value = "/bag/add/{id}", method = RequestMethod.POST)
    public String addToBag(@RequestParam(name = "amount") String amount,
                           @RequestParam(name = "sizeId") String sizeId,
                           @PathVariable(name = "id") String id, HttpServletRequest request) {
        Product product = productService.findProductById(Long.parseLong(id));
        Object bag = request.getSession().getAttribute("bag");
        if (bag == null) {
            List<BagProduct> bagProducts = new ArrayList<>();
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
                    (List<BagProduct>) bag);
        }
        return "redirect:/home";
    }

//    @RequestMapping(value = "/bag")
//    public ModelAndView showBagPage(@ModelAttribute("bag")List<Product> products) {
//        ModelAndView modelAndView = new ModelAndView("bagTest");
//        modelAndView.addObject("bag", products);
//        return modelAndView;
//    }

    @RequestMapping(value = "/bag")
    public ModelAndView showBagPage(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("bag");
        modelAndView.addObject("bag", request.getSession().getAttribute("bag"));
        modelAndView.addObject("totalPrice",
                bagService.figureOutTotalPrice((List<BagProduct>)request.getSession().getAttribute("bag")));
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
        List<BagProduct> bag = (List<BagProduct>) session.getAttribute("bag");
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
        modelAndView.addObject("catalog", productService.findProductsByCategory(categoryService.findCategoryById("1")));
        modelAndView.addObject("options", categoryService.findChilds(categoryService.findCategoryById("1")));
        modelAndView.addObject("isMensActive", true);

        return modelAndView;
    }

    @RequestMapping(value = "/catalog/mens/{id}")
    public ModelAndView showSectionPageFromMens(@PathVariable(name = "id") String id) {
        ModelAndView modelAndView = new ModelAndView("catalog");
        modelAndView.addObject("catalog", productService.findProductsByCategory(categoryService.findCategoryById(id)));
        modelAndView.addObject("options", categoryService.findChilds(categoryService.findCategoryById("1")));
        modelAndView.addObject("isMensActive", true);
        modelAndView.addObject("activeOptionId", Long.parseLong(id));

        return modelAndView;
    }

    @RequestMapping(value = "/catalog/womens/{id}")
    public ModelAndView showSectionPageFromWomens(@PathVariable(name = "id") String id) {
        ModelAndView modelAndView = new ModelAndView("catalog");
        modelAndView.addObject("catalog", productService.findProductsByCategory(categoryService.findCategoryById(id)));
        modelAndView.addObject("options", categoryService.findChilds(categoryService.findCategoryById("2")));
        modelAndView.addObject("isWomensActive", true);
        modelAndView.addObject("activeOptionId", Long.parseLong(id));

        return modelAndView;
    }

    @RequestMapping(value = "/catalog/womens")
    public ModelAndView showWomensPage() {
        ModelAndView modelAndView = new ModelAndView("catalog");
        modelAndView.addObject("catalog", productService.findProductsByCategory(categoryService.findCategoryById("2")));
        modelAndView.addObject("options", categoryService.findChilds(categoryService.findCategoryById("2")));
        modelAndView.addObject("isWomensActive", true);

        return modelAndView;
    }


    @RequestMapping(value = "/catalog/{id}")
    public ModelAndView showProductPage(@PathVariable(name = "id") long id) {
        ModelAndView modelAndView = new ModelAndView("product");
        modelAndView.addObject("product", productService.findProductById(id));

        return modelAndView;
    }

    private void authenticateUserAndSetSession(String email, String password, HttpServletRequest request) {
        // generate session if one doesn't exist
        request.getSession();

        UserDetails user = userDetailsService.loadUserByUsername(email);
        Authentication authenticatedUser = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
    }

    private boolean isCurrentAuthenticationAnonymous() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication instanceof AnonymousAuthenticationToken;
    }

    @RequestMapping(value = "/test")
    public ModelAndView showTestPage(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("test");
        String a = request.getContextPath();
        String b = request.getPathInfo();
        String c = request.getPathTranslated();
        try {
            URL url = request.getServletContext().getResource("classpath:");
            String a2 = url.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //modelAndView.addObject("order", orderService.findOrderById("1"));
        List<Category> root = categoryService.findRootCategories();
        List<Category> child = categoryService.findChilds(root.get(0));
//        List<BagProduct> bagProducts = new ArrayList<>();
//        bagProducts.add(new BagProduct(1, 2, 2000, Arrays.asList(1L, 2L)));
//        bagProducts.add(new BagProduct(2, 1, 2000, Arrays.asList(4L)));
//        orderService.addNewOrder("test",
//                userService.findUserByEmail("mi.mi.mikhaylov97@gmail.com"),
//                new Payment("test", "test", "test"),
//                bagProducts);

        return modelAndView;
    }

    public void setBagSizeToModelAndView(HttpSession session, ModelAndView mav) {
        Object bag = session.getAttribute("bag");
        if (bag == null)  {
            session.setAttribute("bag", new ArrayList<BagProduct>());
            mav.addObject("bagSize", 0);
        } else {
            mav.addObject("bagSize", ((List<BagProduct>) bag).size());
        }
    }

    @RequestMapping(value = "/image/{imageName}")
    @ResponseBody
    public byte[] getImage(@PathVariable(value = "imageName") String imageName) throws IOException {
       // createImagesDirectoryIfNeeded();

        File serverFile = new File(IMAGES_DIR_ABSOLUTE_PATH + imageName); //+ ".jpg");

        return Files.readAllBytes(serverFile.toPath());
    }
}
