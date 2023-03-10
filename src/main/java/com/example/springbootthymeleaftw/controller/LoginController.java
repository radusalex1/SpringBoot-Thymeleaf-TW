package com.example.springbootthymeleaftw.controller;

import com.example.springbootthymeleaftw.Common.Roles;
import com.example.springbootthymeleaftw.model.entity.RoleEntity;
import com.example.springbootthymeleaftw.model.entity.UserEntity;
import com.example.springbootthymeleaftw.model.entity.UserLoginDto;
import com.example.springbootthymeleaftw.service.*;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
//    private final SecurityService securityService;
    private final UserService userService;

    private final RequestService requestService;

    @GetMapping()
    public String open(Model model, String error, String logout){
//        if (securityService.isAuthenticated()) {
//            return "redirect:/";
//        }


        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        model.addAttribute("userLoginForm",new UserLoginDto());

        return "login";
    }

    @PostMapping("/postlogin")
    public String postlogin(@ModelAttribute("userLoginForm") UserLoginDto user,final RedirectAttributes redirectAttributes ,Model model, String error, String logout){


        System.out.println(model.getAttribute("email"));
        System.out.println(model.getAttribute("password"));

        Optional<UserEntity> optUser = userService.getUserByEmail(user.getEmail());
        if (optUser.isPresent()){

            List<RoleEntity> userRoles  = optUser.get().getRoles().stream().toList();

            for (RoleEntity r:userRoles) {
                    UserDetails userDetail = userService.loadUserByUsername(user.getEmail());
                    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities()));

                    if (r.getName().equals(Roles.Client.toString())) {

                        redirectAttributes.addAttribute("filter_categories","All");
                        redirectAttributes.addAttribute("filter_b2b","All");
                        redirectAttributes.addAttribute("quantity","-1");
                        redirectAttributes.addAttribute("search","");

                        return "redirect:/HomeController/GetHomeClient";
                    }


                    if (r.getName().equals(Roles.BTOC.toString())) {
                        if(requestService.accepted(optUser.get().getEmail())) {

                            redirectAttributes.addFlashAttribute("loggedB2c", optUser.get());// check here if is ok
                            return "redirect:/B2CController/Open";
                        }
                        else
                        {
                            model.addAttribute("message","account didnt activated");
                            return "login";
                        }
                    }

                    if (r.getName().equals(Roles.BTOB.toString())) {
                        if(requestService.accepted(optUser.get().getEmail())) {

                            redirectAttributes.addFlashAttribute("loggedB2B", optUser.get());
                            return "redirect:/B2BController/Open";

                        }
                        else
                        {
                            model.addAttribute("message","account didnt activated");
                            return "login";
                        }
                    }

                    if (r.getName().equals(Roles.Admin.toString())) {
                        redirectAttributes.addFlashAttribute("loggedAdmin", optUser.get());
                        return "redirect:/AdminController/Open";
                    }

            }

//            redirectAttributes.addFlashAttribute("userLoginForm",optUser);
//            return "redirect:/";
        }

        model.addAttribute("message", "invalid credentials");
        return "login";
    }


    @GetMapping("/error")
    public String error(Model model, String error, String logout){
        return "login";
    }



}
