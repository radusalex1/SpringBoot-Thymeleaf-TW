package com.example.springbootthymeleaftw.controller;

import com.example.springbootthymeleaftw.model.entity.UserEntity;
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

    @GetMapping("/Open")
    public String open(@ModelAttribute("loggedB2c")UserEntity loggedB2c, Model model, String error, String logout){
        if(this.loggedB2c==null){
            this.loggedB2c=loggedB2c;
        }else {
            if (!Objects.equals(this.loggedB2c.getId(), loggedB2c.getId())) {
                this.loggedB2c = loggedB2c;
            }
        }
        List<UserEntity> b2bs = new ArrayList<UserEntity>();
        b2bs = userService.getB2Bs();
        model.addAttribute("loggedB2c",this.loggedB2c);
        model.addAttribute("b2bs",b2bs);
        return "b2c";
    }

}
