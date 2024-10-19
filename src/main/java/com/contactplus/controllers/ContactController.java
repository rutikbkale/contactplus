package com.contactplus.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.contactplus.entities.Contact;
import com.contactplus.entities.User;
import com.contactplus.forms.ContactForm;
import com.contactplus.helpers.Helper;
import com.contactplus.services.ContactService;
import com.contactplus.services.UserService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/contactplus/contacts")
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

    // view contacts handler
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

    // updating contact handler
    @RequestMapping("/update/{contactId}")
    public String showUpdateContactForm(@PathVariable("contactId") long contactId, Model model) {
        Contact contact = contactService.getByContactId(contactId);
        model.addAttribute("contactId", contactId);
        model.addAttribute("contactForm", contact);
        return "user/editContact"; // This will load the page with the modal
    }

    // updating handler api
    @PostMapping("/updateContact/{contactId}")
    public String updateContact(@Valid @ModelAttribute Contact contactForm, @PathVariable long contactId,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            return "user/viewContacts"; // Show the form again with errors
        }

        contactService.updateContact(contactForm, contactId);
        redirectAttributes.addFlashAttribute("msg", "Contact updated successfully!");

        return "redirect:/contactplus/contacts/viewContacts";
    }

    // deteting contact
    @RequestMapping("/delete/{contactId}")
    public String deleteContact(@PathVariable("contactId") long contactId, RedirectAttributes redirectAttributes) {
        contactService.deleteContact(contactId);

        // Add a flash attribute to pass the message after the redirect
        redirectAttributes.addFlashAttribute("msg", "Contact Deleted successfully!");

        return "redirect:/contactplus/contacts/viewContacts";
    }
    
    // @RequestMapping("/viewContact")
    // public String viewContactById(@RequestParam("contactId") long contactId,
    // Model model) {

    // Contact currentContact = contactService.getByContactId(contactId);

    // model.addAttribute("currentContact", currentContact);

    // return "redirect:user/viewContacts";
    // }

}
