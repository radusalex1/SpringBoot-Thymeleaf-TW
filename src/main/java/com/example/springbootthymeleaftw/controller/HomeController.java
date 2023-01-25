package com.example.springbootthymeleaftw.controller;

import com.example.springbootthymeleaftw.Common.Roles;
import com.example.springbootthymeleaftw.model.entity.RoleEntity;
import com.example.springbootthymeleaftw.model.entity.UserEntity;
import com.example.springbootthymeleaftw.model.entity.UserProductEntity;
import com.example.springbootthymeleaftw.service.UserProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/HomeController")
@RequiredArgsConstructor
public class HomeController {
//    private final SecurityService securityService;

    private final UserProductService  userProductService;
    @PreAuthorize("hasAuthority('Client')")
    @GetMapping("/GetHomeClient")
    public String open(@ModelAttribute("b2cs") List<UserEntity>  b2cs, Model model, String error, String logout) {


        List<UserProductEntity> listOfB2csWithProducts = userProductService.getAll();
        List<UserProductEntity> result = new ArrayList<UserProductEntity>();
        for (UserProductEntity upe:listOfB2csWithProducts) {
            for (RoleEntity u:upe.getUser().getRoles()) {
                if(u.getName().equals(Roles.BTOC.toString())){
                    result.add(upe);
                    break;
                }
            }
        }
        model.addAttribute("b2csWithProducts",result);
//        List<UserEntity> new_b2cs = new ArrayList<UserEntity>();
//        for (UserEntity u: b2cs) {
//            UserEntity user = new UserEntity();
//            user.setId(u.getId());
//            user.setPassword(u.getPassword());
//            user.setProducts(u.getProducts());
//            user.setUsername(u.getUsername());
//            user.setEmail(u.getEmail());
//            new_b2cs.add(user);
//        }

        //model.addAttribute("b2cs",new_b2cs);

        return "client";
    }
}
