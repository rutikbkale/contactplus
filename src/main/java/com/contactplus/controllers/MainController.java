package com.contactplus.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import com.contactplus.entities.Provider;
import com.contactplus.entities.User;
import com.contactplus.forms.UserForm;
import com.contactplus.services.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/contactplus")
public class MainController {

    @Autowired
    private UserService userService;

    // landing page
    @RequestMapping("/")
    public String landingPage() {
        return "index";
    }

    // signup form mapping
    @RequestMapping("/signup")
    public String signupPage(Model model) {
        UserForm userForm = new UserForm();
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
    public String signupForm(@Valid @ModelAttribute UserForm userForm, BindingResult bindingResult, Model model) {

        // server side validation
        if (bindingResult.hasErrors()) {
            return "signup";
        }

        // fetching data from userForm
        User user = User.builder()
                .name(userForm.getName())
                .email(userForm.getEmail())
                .mobNo(userForm.getMobNo())
                .password(userForm.getPassword())
                .provider(Provider.SELF)
                .build();

        // adding user object to database
        User savedUser = userService.saveUser(user);

        if (savedUser != null) {
            model.addAttribute("msg", "Registration Successfully !");
        } else {
            model.addAttribute("msg", "Registration Failed !");
        }
        model.addAttribute("userForm", new UserForm());
        return "signup";
    }

}
