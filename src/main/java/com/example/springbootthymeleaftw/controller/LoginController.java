package com.example.springbootthymeleaftw.controller;

import com.example.springbootthymeleaftw.Common.Roles;
import com.example.springbootthymeleaftw.model.entity.RoleEntity;
import com.example.springbootthymeleaftw.model.entity.UserEntity;
import com.example.springbootthymeleaftw.model.entity.UserLoginDto;
import com.example.springbootthymeleaftw.service.SecurityService;
import com.example.springbootthymeleaftw.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.http.HttpClient;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
//    private final SecurityService securityService;
    private final UserService userService;
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
                if (r.getName().equals(Roles.Client.toString())){

                    List<UserEntity> b2cs = userService.getB2Cs();
                    redirectAttributes.addFlashAttribute("b2cs",b2cs);
                    return "redirect:/HomeController/GetHomeClient";
                }

                if(r.getName().equals(Roles.B2C.toString())){
                    List<UserEntity> b2bs = userService.getB2Bs();
                    redirectAttributes.addFlashAttribute("loggedB2C",user);
                    return "redirect:/B2C";
                }

                if(r.getName().equals(Roles.B2B.toString())){

                    redirectAttributes.addFlashAttribute("loggedB2B",optUser.get());
                    return "redirect:/B2BController/Open";
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
