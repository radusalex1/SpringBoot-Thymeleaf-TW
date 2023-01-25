package com.example.springbootthymeleaftw.controller;

import com.example.springbootthymeleaftw.Common.Roles;
import com.example.springbootthymeleaftw.model.entity.RoleEntity;
import com.example.springbootthymeleaftw.model.entity.UserEntity;
import com.example.springbootthymeleaftw.model.entity.UserProductEntity;
import com.example.springbootthymeleaftw.service.UserProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/HomeController")
@RequiredArgsConstructor
public class HomeController {
//    private final SecurityService securityService;

    private final UserProductService  userProductService;

    @GetMapping("/GetHomeClient")
    public String open(@RequestParam("filter_categories") String filterCategory,
                       @RequestParam("filter_b2b") String filterB2B,
                       @RequestParam("quantity") String quantity,
                       Model model, String error, String logout) {


        if(quantity.equals(""))
        {
            quantity="-1";
        }

        List<String> allCategories = new ArrayList<>();
        List<String> allB2cs = new ArrayList<>();

        List<UserProductEntity> listOfB2csWithProducts=new ArrayList<>();

        listOfB2csWithProducts = userProductService.getByFilter(filterCategory,filterB2B,Integer.valueOf(quantity));

        List<UserProductEntity> result = new ArrayList<>();

        for (UserProductEntity upe:listOfB2csWithProducts) {
            for (RoleEntity u:upe.getUser().getRoles()) {
                if(u.getName().equals(Roles.B2C.toString())){
                    result.add(upe);
                    if(!allCategories.contains(upe.getProduct().getCategory()))
                    {
                        allCategories.add(upe.getProduct().getCategory());
                    }
                   if(!allB2cs.contains(upe.getUser().getCompanyName()))
                   {
                       allB2cs.add(upe.getUser().getCompanyName());
                   }

                    break;
                }
            }
        }

        model.addAttribute("b2csWithProducts",result);

        model.addAttribute("categories",allCategories);
        model.addAttribute("b2cs",allB2cs);


        return "client";
    }
}
