package com.contactplus.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Contact getByContactId(long contactId) {
        return contactRepository.findByContactId(contactId);
    }

    @Override
    public Contact updateContact(Contact contact, long contactId) {
        Contact existingContact = contactRepository.findByContactId(contactId);
        existingContact.setName(contact.getName());
        existingContact.setEmail(contact.getEmail());
        existingContact.setMobNo(contact.getMobNo());
        existingContact.setAddress(contact.getAddress());
        existingContact.setFavorite(contact.isFavorite());
        return contactRepository.save(existingContact);
    }

    @Override
    public void deleteContact(long contactId) {
        Contact contact = contactRepository.findByContactId(contactId);
        if (contact != null) {
            contactRepository.deleteById(contactId);
        } else {
            throw new RuntimeException("Contact not found");
        }
    }

    @Override
    public Page<Contact> getContactsByUser(User user, int page, int size, String sortBy, String direction) {

        Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return contactRepository.findByUser(user, pageable);
    }

}
