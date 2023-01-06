package com.example.springbootthymeleaftw.controller;

import com.example.springbootthymeleaftw.model.entity.UserEntity;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/GetHomeClient")
    public String open(@ModelAttribute("b2cs") List<UserEntity>  b2cs, Model model, String error, String logout) {
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
