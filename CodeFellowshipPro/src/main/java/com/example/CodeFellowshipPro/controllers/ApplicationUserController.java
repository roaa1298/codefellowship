package com.example.CodeFellowshipPro.controllers;

import com.example.CodeFellowshipPro.models.ApplicationUser;
import com.example.CodeFellowshipPro.models.Post;
import com.example.CodeFellowshipPro.repositories.ApplicationUserRepository;
import com.example.CodeFellowshipPro.repositories.PostRepository;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ApplicationUserController {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PostRepository postRepository;

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

    @GetMapping("/myProfile")
    public String getMyProfile(Principal p, Model model) {
        model.addAttribute("userProfile", applicationUserRepository.findUserByUsername(p.getName()));
        return "profile";
    }
    @GetMapping("/user/{id}")
    public String getProfile(@PathVariable long id, Model model) {

        model.addAttribute("username", applicationUserRepository.getById(id));
        return "otherProfile";
    }

    @PostMapping("/addPost")
    public RedirectView addPost(Principal p, String body) {
        ApplicationUser newUser = applicationUserRepository.findUserByUsername(p.getName());
        Post post = new Post(newUser, body);
        postRepository.save(post);
        return new RedirectView("/myProfile");
    }
    @GetMapping("/allUsers")
    public String getAllUsers(Principal p, Model model){
        List<ApplicationUser> allAccounts = new ArrayList<>(applicationUserRepository.findAll());
        allAccounts.remove(applicationUserRepository.findUserByUsername(p.getName()));
        model.addAttribute("usersList", allAccounts);
        return "allUsers";
    }

    @PostMapping("/allUsers")
    public RedirectView viewProfile( Long id){

        return new RedirectView("/user/"+id);
    }



}
