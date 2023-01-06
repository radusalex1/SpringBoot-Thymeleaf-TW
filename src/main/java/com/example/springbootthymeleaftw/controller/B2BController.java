package com.example.springbootthymeleaftw.controller;

import com.example.springbootthymeleaftw.model.entity.Product;
import com.example.springbootthymeleaftw.model.entity.UserEntity;
import com.example.springbootthymeleaftw.model.entity.UserProductEntity;
import com.example.springbootthymeleaftw.service.ProductService;
import com.example.springbootthymeleaftw.service.UserProductService;
import com.example.springbootthymeleaftw.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Controller
@RequestMapping("/B2BController")
@RequiredArgsConstructor
public class B2BController {

    private final ProductService productService;
    private final UserService userService;
    private static UserEntity loggedB2b;

    private final UserProductService userProductService;
    @GetMapping("/Open")
    public String open(@ModelAttribute("loggedB2B")  UserEntity b2b, Model model, String error, String logout) {

        if(loggedB2b==null){
            this.loggedB2b=b2b;
        }else {
            if (!Objects.equals(b2b.getId(), loggedB2b.getId())) {
                this.loggedB2b = b2b;
            }
        }

        model.addAttribute("loggedB2B",b2b);
        return  "b2b";
    }

    @GetMapping("/openAddProductPage")
    public String openAddProductPage(Model model){
        model.addAttribute("productForm", new Product());
        return "addProduct";
    }

    @PostMapping("/addProduct")
    public String addProduct(@ModelAttribute("productForm") Product product, final RedirectAttributes redirectAttributes, BindingResult bindingResult) {
        UserEntity loggedB2b_2 = userService.getUserByEmail(this.loggedB2b.getEmail()).get();
        productService.addNewProduct(product);

        UserProductEntity userProductEntity= new UserProductEntity();
        userProductEntity.setUser(this.loggedB2b);
        userProductEntity.setProduct(product);
        userProductEntity.setQuantity(product.getQuantity());

        userProductService.addNewUserProduct(userProductEntity);

//        Set<Product> products = loggedB2b_2.getProducts();
//        products.add(product);
//        loggedB2b_2.setProducts(products);
        //userService.save(loggedB2b_2);
        redirectAttributes.addFlashAttribute("loggedB2B",this.loggedB2b);
        return "redirect:/B2BController/Open";
    }

}
