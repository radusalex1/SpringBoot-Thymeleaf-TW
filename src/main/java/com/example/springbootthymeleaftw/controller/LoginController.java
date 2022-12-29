package com.example.springbootthymeleaftw.controller;

import com.example.springbootthymeleaftw.model.entity.UserEntity;
import com.example.springbootthymeleaftw.model.entity.UserLoginDto;
import com.example.springbootthymeleaftw.service.SecurityService;
import com.example.springbootthymeleaftw.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.http.HttpClient;
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
            redirectAttributes.addFlashAttribute("userLoginForm",optUser);
            return "redirect:/";
        }

        model.addAttribute("message", "invalid credentials");
        return "login";
    }


    @GetMapping("/error")
    public String error(Model model, String error, String logout){
        return "login";
    }

}
