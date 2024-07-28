package com.contactplus.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.contactplus.entities.Contact;
import com.contactplus.entities.User;
import com.contactplus.forms.ContactForm;
import com.contactplus.helpers.Helper;
import com.contactplus.services.ContactService;
import com.contactplus.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/contactplus/user/contacts")
public class ContactController {

    @Autowired
    private UserService userService;

    @Autowired
    private ContactService contactService;

    @RequestMapping("/addContact")
    public String addContactForm(Model model) {
        ContactForm contactForm = new ContactForm();
        model.addAttribute("contactForm", contactForm);
        return "user/addContact";
    }

    @PostMapping("/addContact")
    public String addContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult bindingResult, Model model,
            Authentication authentication) {

        // server side validation
        if (bindingResult.hasErrors()) {
            return "user/addContact";
        }

        // fetching user with thier email id
        String email = Helper.getAuthenticateUserName(authentication);
        User loggedUser = userService.getUserByEmail(email);

        // fetching data from contactForm
        Contact contact = Contact.builder()
                .name(contactForm.getName())
                .email(contactForm.getEmail())
                .mobNo(contactForm.getMobNo())
                .address(contactForm.getAddress())
                .favorite(contactForm.isFavorite())
                .user(loggedUser)
                .build();

        // save contact to the database
        Contact savedContact = contactService.saveContact(contact);

        // checking if the contact added or not
        if (savedContact != null) {
            model.addAttribute("msg", "Contact Added Successfully !");
        } else {
            model.addAttribute("msg", "Error occured !");
        }

        // clearing the form fields
        model.addAttribute("contactForm", new ContactForm());

        return "user/addContact";
    }

    @RequestMapping("/viewContacts")
    public String viewContacts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "sortBy", defaultValue = "contactId") String sortFBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Authentication authentication, Model model) {

        // fetching user with thier email id
        String email = Helper.getAuthenticateUserName(authentication);
        User loggedUser = userService.getUserByEmail(email);

        // fetching contacts list from database
        Page<Contact> contactPage = contactService.getContactsByUser(loggedUser, page, size, sortFBy, direction);

        // sending contacts list to the view
        model.addAttribute("contactPage", contactPage);

        return "user/viewContacts";
    }

    @RequestMapping("/viewContact")
    public String viewContactById(@RequestParam("contactId") int contactId, Model model) {

        Contact currentContact = contactService.getByContactId(contactId);

        model.addAttribute("currentContact", currentContact);

        return "redirect : user/viewContacts";
    }

    @RequestMapping("/delete/{contactId}")
    public String deleteContact(@PathVariable("contactId") int contactId, HttpSession session) {

        contactService.deleteContact(contactId);
        session.setAttribute("msg", "Contact Deleted successfully !");
        return "redirect :/user/viewContacts";
    }
}
