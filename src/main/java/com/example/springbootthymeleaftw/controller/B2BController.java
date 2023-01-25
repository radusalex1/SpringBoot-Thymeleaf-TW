package com.example.springbootthymeleaftw.controller;

import com.example.springbootthymeleaftw.model.entity.CargoRequest;
import com.example.springbootthymeleaftw.model.entity.Product;
import com.example.springbootthymeleaftw.model.entity.UserEntity;
import com.example.springbootthymeleaftw.model.entity.UserProductEntity;
import com.example.springbootthymeleaftw.repository.CargoRequestRepository;
import com.example.springbootthymeleaftw.service.CargoRequestService;
import com.example.springbootthymeleaftw.service.ProductService;
import com.example.springbootthymeleaftw.service.UserProductService;
import com.example.springbootthymeleaftw.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Controller
@RequestMapping("/B2BController")
@RequiredArgsConstructor
public class B2BController {

    private final ProductService productService;
    private final UserService userService;
    private static UserEntity loggedB2b;
    private final CargoRequestService cargoRequestService;

    private final UserProductService userProductService;
    @GetMapping("/Open")
    public String open(@ModelAttribute("loggedB2B")  UserEntity b2b, Model model, String error, String logout) {

        if(loggedB2b==null){
            this.loggedB2b=b2b;
        } else {
            if (!Objects.equals(b2b.getId(), loggedB2b.getId())) {
                this.loggedB2b = b2b;
            }
        }
        List<UserProductEntity> userProductEntities = userProductService.getAllByB2bId(b2b.getId());
        model.addAttribute("userProductEntities",userProductEntities);
        model.addAttribute("loggedB2B",b2b);
        return "b2b_home";
    }

    @GetMapping("/openAddProductPage")
    public String openAddProductPage(Model model){
        model.addAttribute("productForm", new Product());
        return "addProduct";
    }

    @GetMapping("/ApproveMarfa")
    public String openApproveMarfaPage(Model model) {

        List<CargoRequest> result = cargoRequestService.getAllUnacceptedForB2b(this.loggedB2b.getId());

        for (CargoRequest cr : result) {
            cr.setFromUser(userService.getById(cr.getFromUserId()));
            cr.setToUser(userService.getById(cr.getToUserId()));
            cr.setProduct(productService.getById(cr.getProductId()));
        }

        model.addAttribute("unacceptedCargoRequest", result);
        model.addAttribute("loggedB2B", this.loggedB2b);
        return "b2b_approve_marfa";
    }

    @PostMapping("/SubmitApproveMarfa")
    public String ApproveMarfa(@RequestParam("unacceptedCargoRequest")List<String>selectedOptions){
        //get the cargo requests by id
        List<CargoRequest> acceptedRequests = new ArrayList<CargoRequest>();
        for (String option: selectedOptions) {
            acceptedRequests.add(cargoRequestService.getById(Long.valueOf(option)));
        }

        //set the accepted field to true
        for (CargoRequest cr: acceptedRequests) {
            cr.setAccepted(true);
            cargoRequestService.add(cr);
            cr.setToUser(userService.getById(cr.getToUserId()));
            cr.setFromUser(userService.getById(cr.getFromUserId()));
            cr.setProduct(productService.getById(cr.getProductId()));
        }

        //update quantities;
        for(CargoRequest cr:acceptedRequests){
            //update b2b quantity
            UserProductEntity userProductEntity_fromB2bPov = userProductService.getByB2bAndProduct(cr.getToUserId(),cr.getProductId());
            if(userProductEntity_fromB2bPov!=null){
                int q = userProductEntity_fromB2bPov.getQuantity();
                userProductEntity_fromB2bPov.setQuantity(q-cr.getQuantity());
                userProductService.addNewUserProduct(userProductEntity_fromB2bPov);
            }

            //update b2c quntity
            UserProductEntity userProductEntity_fromB2cPov = userProductService.getByB2cAndProduct(cr.getFromUserId(),cr.getProductId());

            if(userProductEntity_fromB2cPov!=null){
                int q = userProductEntity_fromB2cPov.getQuantity();
                userProductEntity_fromB2cPov.setQuantity(q+cr.getQuantity());
                userProductService.addNewUserProduct(userProductEntity_fromB2cPov);
            }
            else
            {
                UserProductEntity userProductEntity_fromB2cPov_new = new UserProductEntity();
                userProductEntity_fromB2cPov_new.setQuantity(cr.getQuantity());
                userProductEntity_fromB2cPov_new.setUser(cr.getFromUser());
                userProductEntity_fromB2cPov_new.setProduct(cr.getProduct());
                userProductService.addNewUserProduct(userProductEntity_fromB2cPov_new);
            }
        }

        return "redirect:/B2BController/ApproveMarfa";
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

        redirectAttributes.addFlashAttribute("loggedB2B",this.loggedB2b);
        return "redirect:/B2BController/Open";
    }

    private UserProductEntity getUserProductEntity(String key)
    {
        String[] subStrings = key.split("--");
        UserProductEntity userProductEntity = userProductService.getByUserAndProduct(Long.valueOf(subStrings[0]),Long.valueOf(subStrings[1]));
        return userProductEntity;
    }
    @PostMapping("/SetInventory")
    public String SetInventory(@RequestParam Map<String, String> formData,Model model) {
        System.out.println("here");
        for (Map.Entry<String, String> entry : formData.entrySet()) {
            if (!entry.getKey().toString().startsWith("_csrf") && !entry.getValue().equals("")) {
                UserProductEntity userProductEntity = getUserProductEntity(entry.getKey());
                userProductEntity.setQuantity(Integer.valueOf(entry.getValue()));
                userProductService.addNewUserProduct(userProductEntity);
            }

        }
        return "redirect:/B2BController/Open";
    }

}
