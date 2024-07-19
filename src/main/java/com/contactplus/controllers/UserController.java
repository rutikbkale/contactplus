package com.contactplus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contactplus/user")
public class UserController {

    @GetMapping("/dashboard")
    public String userDashboard() {
        return "user/dashboard";
    }

}
