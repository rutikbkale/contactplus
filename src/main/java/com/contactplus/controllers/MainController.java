package com.contactplus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class MainController {

    @RequestMapping("/")
    public String landingPage(){
        return "index";
    }

    @RequestMapping("/signup")
    public String signupPage() {
        return new String("signup");
    }

    @RequestMapping("/signin")
    public String signinPage() {
        return new String("signin");
    }
    

}
