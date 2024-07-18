package com.contactplus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.contactplus.entities.User;
import com.contactplus.forms.UserForm;
import com.contactplus.services.UserService;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("contactplus")
public class MainController {

    private UserService userService;

    // dependency injection using contruction injection
    public MainController(UserService userService) {
        this.userService = userService;
    }

    // landing page
    @RequestMapping("/")
    public String landingPage() {
        return "index";
    }

    // signup form mapping
    @RequestMapping("/signup")
    public String signupPage(Model model) {
        UserForm userForm = new UserForm("Rutik Kale", "kalerutik656@gmail.com", "9172475163", "12345");
        model.addAttribute("userForm", userForm);
        return new String("signup");
    }

    // signin form mapping
    @RequestMapping("/signin")
    public String signinPage() {
        return new String("signin");
    }

    // recieving form data using userForm object & inserting to database
    @PostMapping("/doSignup")
    public String signupForm(@ModelAttribute UserForm userForm) {

        // fetching data from userForm
        User user = User.builder()
                .name(userForm.getName())
                .email(userForm.getEmail())
                .mobNo(userForm.getMobNo())
                .password(userForm.getPassword())
                .build();

        // adding user object to database
        userService.saveUser(user);

        return "redirect:signup";
    }

}
