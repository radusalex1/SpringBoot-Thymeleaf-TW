package com.example.springbootthymeleaftw.controller;

import com.example.springbootthymeleaftw.Common.Roles;
import com.example.springbootthymeleaftw.model.entity.RoleEntity;
import com.example.springbootthymeleaftw.model.entity.UserEntity;
import com.example.springbootthymeleaftw.model.entity.UserProductEntity;
import com.example.springbootthymeleaftw.service.UserProductService;
import com.example.springbootthymeleaftw.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/B2CController")
@RequiredArgsConstructor
public class B2CController {

    private final UserService userService;
    private UserEntity loggedB2c;
private final UserProductService userProductService;
    @GetMapping("/Open")
    public String open(@ModelAttribute("loggedB2c")UserEntity loggedB2c, Model model, String error, String logout){
        if(this.loggedB2c==null){
            this.loggedB2c=loggedB2c;
        } else {
            if (!Objects.equals(this.loggedB2c.getId(), loggedB2c.getId())) {
                this.loggedB2c = loggedB2c;
            }
        }

        List<UserProductEntity> listOfB2csWithProducts = userProductService.getAll();
        List<UserProductEntity> result = new ArrayList<UserProductEntity>();
        for (UserProductEntity upe:listOfB2csWithProducts) {
            for (RoleEntity u:upe.getUser().getRoles()) {
                if(u.getName().equals(Roles.B2B.toString())){
                    result.add(upe);
                    break;
                }
            }
        }

        model.addAttribute("loggedB2c",this.loggedB2c);
        model.addAttribute("b2bs",result);
        return "b2c";
    }

}
