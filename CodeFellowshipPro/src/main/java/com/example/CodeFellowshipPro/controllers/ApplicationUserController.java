package com.example.CodeFellowshipPro.controllers;

import com.example.CodeFellowshipPro.models.ApplicationUser;
import com.example.CodeFellowshipPro.repositories.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;

@Controller
public class ApplicationUserController {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @GetMapping("/")
    public String getHome(Principal p, Model model) {
        if(p != null){
            ApplicationUser applicationUser=applicationUserRepository.findUserByUsername(p.getName());
            model.addAttribute("user", applicationUser);
            return "mainPage";
        }
        else{
            return "home";
        }
    }

    @GetMapping("/signup")
    public String getSignUpPage() {
        return "signup";
    }

    @GetMapping("/login")
    public String getSignInPage(Principal p, Model m) {
//        ApplicationUser currentUser = applicationUserRepository.findUserByUsername(p.getName());
//        if (currentUser != null) {
//            new RedirectView("/");
//        }
        return "login";
    }

    @PostMapping("/signup")
    public RedirectView attemptSignUp(@ModelAttribute ApplicationUser user) {
        if (applicationUserRepository.findUserByUsername(user.getUsername()) == null) {
        ApplicationUser newUser = new ApplicationUser(user.getUsername(),
                encoder.encode(user.getPassword()),
                user.getFirstName(), user.getLastName(), user.getDateOfBirth(), user.getBio());
        applicationUserRepository.save(newUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView("/");
        } else {
            return new RedirectView("/signup?taken=true");
        }
    }

//    @PostMapping("/login")
//    public RedirectView loginResponse(@ModelAttribute ApplicationUser user, Model model) {
//        model.addAttribute("username", applicationUserRepository.findUserByUsername(user.getUsername()));
//        return new RedirectView("/");
//    }

}
