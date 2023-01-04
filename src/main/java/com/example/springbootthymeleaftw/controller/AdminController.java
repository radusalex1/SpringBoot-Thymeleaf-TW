package com.example.springbootthymeleaftw.controller;

import com.example.springbootthymeleaftw.model.entity.Request;
import com.example.springbootthymeleaftw.model.entity.UserEntity;
import com.example.springbootthymeleaftw.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/AdminController")
@RequiredArgsConstructor
public class AdminController {

    private final RequestService requestService;
    private  static UserEntity loggedAdmin;

    private  List<Request> unacceptedRequests1;
    @GetMapping("/Open")
    public String open(@ModelAttribute("loggedAdmin") UserEntity loggedAdmin, Model model){

        if(this.loggedAdmin==null){
            this.loggedAdmin=loggedAdmin;
        }else {
            if (!Objects.equals(loggedAdmin.getId(), loggedAdmin.getId())) {
                this.loggedAdmin = loggedAdmin;
            }
        }

        model.addAttribute("loggedAdmin",this.loggedAdmin);

        List<Request> unacceptedRequests  = requestService.getUnacceptedRequests();

        model.addAttribute("unacceptedRequests",unacceptedRequests);
        this.unacceptedRequests1 = unacceptedRequests;
        return "admin";
    }

    @PostMapping("/AcceptRequests")
    public String acceptRequests(Model model,final RedirectAttributes redirectAttributes){

        for (Request r: this.unacceptedRequests1) {
            r.setAccepted(true);
            requestService.addNewAccountRequest(r);
        }
        redirectAttributes.addFlashAttribute("loggedAdmin",this.loggedAdmin);
        return "redirect:/AdminController/Open";
    }
}
