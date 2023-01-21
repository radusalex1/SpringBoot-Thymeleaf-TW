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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

        System.out.println(this.loggedB2c.getId());

        /// lista produse a b2cului logged
        List<UserProductEntity> listOfB2csWithProducts = userProductService.getAll();
        List<UserProductEntity> result = new ArrayList<UserProductEntity>();
        for (UserProductEntity upe:listOfB2csWithProducts) {
            if (upe.getUser().getId()==this.loggedB2c.getId()) {
                result.add(upe);
            }
        }

        model.addAttribute("loggedB2c",this.loggedB2c);
        model.addAttribute("ProductsOfLoggedB2C",result);

        return "b2c_home";
    }

    @GetMapping("/RequestMarfa")
    public String openRequestMarfa(@ModelAttribute("loggedB2c")UserEntity loggedB2c,Model model){

        System.out.println(this.loggedB2c.getId());
        // lista cu b2b de la care poate b2c sa faca request marfa.
        List<UserProductEntity> listOfB2bsWithProducts = userProductService.getAll();
        List<UserProductEntity> result = new ArrayList<UserProductEntity>();
        for (UserProductEntity upe:listOfB2bsWithProducts) {
            for (RoleEntity r:upe.getUser().getRoles()) {
                if(r.getName().equals(Roles.B2B.toString())){
                    result.add(upe);
                    break;
                }
            }
        }

        model.addAttribute("b2bsWithProducts",result);
        model.addAttribute("loggedB2c",this.loggedB2c);
        return "b2c_request_marfa";
    }

    @PostMapping("/RequestMarfaPost")
    public String RequestMarfaPost(@RequestParam Map<String, String> formData,Model model){

        System.out.println("here");

        return "redirect:/B2CController/Open";
    }
}
