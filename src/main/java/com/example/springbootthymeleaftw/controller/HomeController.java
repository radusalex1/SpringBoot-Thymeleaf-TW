package com.example.springbootthymeleaftw.controller;

import com.example.springbootthymeleaftw.Common.Roles;
import com.example.springbootthymeleaftw.model.entity.RoleEntity;
import com.example.springbootthymeleaftw.model.entity.UserEntity;
import com.example.springbootthymeleaftw.model.entity.UserLoginDto;
import com.example.springbootthymeleaftw.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Optional;

@Controller
//@RequestMapping("/") // this is default
@RequiredArgsConstructor
public class HomeController {
//    private final SecurityService securityService;

    @GetMapping()
    public String open(@ModelAttribute("b2cs") List<UserEntity>  b2cs, Model model, String error, String logout){
//            UserEntity user = optUser.get();
//            List<RoleEntity> userRoles  = user.getRoles().stream().toList();

//        for (RoleEntity r:userRoles) {
//            if (r.getName()== Roles.Client.toString()){
//                return "ClientHome";
//            }
//        }

//        if (!securityService.isAuthenticated()) {
//            return "login";
//        }

        return "index";
    }
}
