package com.example.springbootthymeleaftw.controller;

import com.example.springbootthymeleaftw.Common.Roles;
import com.example.springbootthymeleaftw.model.entity.Request;
import com.example.springbootthymeleaftw.model.entity.RoleEntity;
import com.example.springbootthymeleaftw.model.entity.UserEntity;
import com.example.springbootthymeleaftw.service.RequestService;
import com.example.springbootthymeleaftw.service.UserService;
import com.example.springbootthymeleaftw.service.UserValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/AdminController")
@RequiredArgsConstructor
public class AdminController {

    private final UserValidatorService userValidatorService;
    private final RequestService requestService;
    private  UserEntity loggedAdmin;

    private final JavaMailSender javaMailSender;
    private final UserService userService;
    private  List<Request> unacceptedRequests1;
    @GetMapping("/Open")
    public String open(@ModelAttribute("loggedAdmin") UserEntity loggedAdmin, Model model){

        if(this.loggedAdmin==null){
            this.loggedAdmin=loggedAdmin;
        } else {
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

    @GetMapping("/OpenChangePasswordPage")
    public String openChangePasswordPage(Model model){

        model.addAttribute("loggedAdmin",new UserEntity());
        model.addAttribute("invalidCredentials","");
        return "changePass";
    }


    @PostMapping("/ChangePassword")
    public String ChangePassword(Model model,@ModelAttribute("loggedAdmin") UserEntity userForm,final RedirectAttributes redirectAttributes, BindingResult bindingResult){

        userForm.setUsername(this.loggedAdmin.getUsername());
        userForm.setPasswordConfirm(userForm.getPassword());
        userValidatorService.validate(userForm, bindingResult);
        Optional<UserEntity> optUser = userService.getUserByEmail(userForm.getEmail());
        if (bindingResult.hasErrors() || !optUser.isPresent()) {
            model.addAttribute("invalidCredentials", "invalidCredentials");
            return "changePass";
        }

        this.loggedAdmin.setPassword(userForm.getPassword());
        userService.save(this.loggedAdmin);
        redirectAttributes.addFlashAttribute("loggedAdmin",this.loggedAdmin);
        return "redirect:/AdminController/Open";
    }


    @PostMapping("/AcceptRequests")
    public String acceptRequests(@RequestParam("unacceptedRequests") List<String> selectedOptions,Model model,final RedirectAttributes redirectAttributes){

        for(String option:selectedOptions){
            Request r =  requestService.getById(Long.valueOf(option));
            r.setAccepted(true);
            requestService.addNewAccountRequest(r);
            try {
                sendMail(r.getEmail(),"");
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }

        redirectAttributes.addFlashAttribute("loggedAdmin",this.loggedAdmin);

        return "redirect:/AdminController/Open";

    }
    private void sendMail(String email, String businessName) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("stare_shop@gmail.com", "Stare Shop");
        helper.setTo(email);
        helper.setSubject("Business accepted");

        String content = "<p>Congratulations \uD83D\uDCBC \uD83D\uDCC8 \uD83E\uDD1D \uD83D\uDCCA!</p>"
                + "<p>Your business " + businessName + " got accepted!</p>"
                + "<p>Welcome to Stare Shop!</p>";

        helper.setText(content, true);

        javaMailSender.send(message);
    }
}
