package com.example.springbootthymeleaftw.controller;

import com.example.springbootthymeleaftw.Common.Roles;
import com.example.springbootthymeleaftw.model.entity.Request;
import com.example.springbootthymeleaftw.model.entity.RoleEntity;
import com.example.springbootthymeleaftw.model.entity.UserEntity;
import com.example.springbootthymeleaftw.service.RequestService;
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

import java.util.ArrayList;
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

        model.addAttribute("userForm", new UserEntity());

        return "register";
    }

    @PostMapping()
    public String register(@ModelAttribute("userForm") UserEntity userForm,
                           BindingResult bindingResult){

        userValidatorService.validate(userForm, bindingResult);

        List<RoleEntity> roles = new ArrayList<RoleEntity>();

            //todo creere cont client
            if (bindingResult.hasErrors())
                return "register";

            RoleEntity role = roleService.getCRoleByName("Client");

            roles.add(role);

            userForm.setRoles(roles);

            userService.save(userForm);

        return "redirect:/login";
    }
}
