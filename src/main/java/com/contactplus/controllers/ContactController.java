package com.contactplus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contactplus/user/contacts")
public class ContactController {

    @RequestMapping("/addContact")
    public String addContactForm() {
        return "user/addContact";
    }

}
