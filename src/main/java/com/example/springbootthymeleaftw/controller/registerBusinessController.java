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
@RequestMapping("/registerBusiness")
@RequiredArgsConstructor
public class registerBusinessController {

    private final UserValidatorService userValidatorService;
    private final UserService userService;
    private final RoleService roleService;

    private final RequestService requestService;

    @GetMapping()
    public String open_business_register(Model model){

        System.out.println(model);

        model.addAttribute("userForm", new UserEntity());

        model.addAttribute("roleForm", new RoleEntity());

        return "registerBusiness";
    }


    @PostMapping()
    public String register(@ModelAttribute("userForm") UserEntity userForm,
                           @ModelAttribute("roleForm") RoleEntity roleEntity,Model model,
                           BindingResult bindingResult){

        //userValidatorService.validate(userForm, bindingResult);///todo asta nu merge idk why

        //validare lvl999
        if(userForm.getUsername().isEmpty() || userForm.getPassword().isEmpty()
        || userForm.getEmail().isEmpty() || userForm.getCompanyAddress().isEmpty()
        || userForm.getCompanyCode().isEmpty() || userForm.getCompanyName().isEmpty() ||
        !userForm.getPassword().equals(userForm.getPasswordConfirm())){

             return "redirect:/registerBusiness";
        }

        List<RoleEntity> roles = new ArrayList<RoleEntity>();

//            //todo creere cont business
//            if (bindingResult.hasErrors())
//                return "registerBusiness";

            RoleEntity role = roleService.getCRoleByName(roleEntity.getName());
            roles.add(role);

            userForm.setRoles(roles);

            userService.save(userForm);

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
