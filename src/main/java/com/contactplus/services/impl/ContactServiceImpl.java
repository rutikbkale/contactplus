package com.contactplus.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contactplus.entities.Contact;
import com.contactplus.entities.User;
import com.contactplus.repositories.ContactRepository;
import com.contactplus.services.ContactService;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public Contact saveContact(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public List<Contact> getContactsByUser(User user) {
        return contactRepository.findByUser(user);
    }

}
