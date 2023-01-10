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

    private final RequestService requestService;
    @GetMapping()
    public String open(Model model){
        System.out.println(model);

        model.addAttribute("userForm", new UserEntity());
        model.addAttribute("roleForm", new RoleEntity());
        return "register";
    }

    @PostMapping()
    public String register(@ModelAttribute("userForm") UserEntity userForm,@ModelAttribute("roleForm") RoleEntity roleEntity, BindingResult bindingResult){

        // todo radu: optiunea de rol vine din html de implementat
        // todo sa faca si insert in db.
        userValidatorService.validate(userForm, bindingResult);

        if (bindingResult.hasErrors())
            return "register";

        RoleEntity role = roleService.getRoleById(userForm.getRoleCode());
        List<RoleEntity> roles = new ArrayList<RoleEntity>();
        roles.add(role);

        userForm.setRoles(roles);

        userService.save(userForm);
        userService.login(userForm.getEmail(), userForm.getPassword());

        for (RoleEntity r:roles) {
            if (r.getName().equals(Roles.B2B.toString()) ||
                    r.getName().equals(Roles.B2C.toString())){

                Request request = new Request();
                request.setEmail(userForm.getEmail());
                requestService.addNewAccountRequest(request);

            }
        }

        return "redirect:/login";
    }
}
