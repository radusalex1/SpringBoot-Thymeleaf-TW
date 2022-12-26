package com.example.springbootthymeleaftw.controller;

import com.example.springbootthymeleaftw.model.entity.RoleEntity;
import com.example.springbootthymeleaftw.model.entity.UserEntity;
import com.example.springbootthymeleaftw.service.RoleService;
import com.example.springbootthymeleaftw.service.UserService;
import com.example.springbootthymeleaftw.service.UserValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {
    private final UserValidatorService userValidatorService;
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping()
    public String open(Model model){
        System.out.println(model);

        List<RoleEntity> availableRoles = roleService.getAllRoles();

        model.addAttribute("userForm", new UserEntity());
        model.addAttribute("availableRoles",availableRoles);

        return "register";
    }

    @PostMapping()
    public String register(@ModelAttribute("userForm") UserEntity userForm, BindingResult bindingResult){
        userValidatorService.validate(userForm, bindingResult);

        if (bindingResult.hasErrors())
            return "register";

        userService.save(userForm);
        userService.login(userForm.getEmail(), userForm.getPassword());
        return "index";
    }
}
