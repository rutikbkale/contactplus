package com.contactplus.services;

import org.springframework.data.domain.Page;
import com.contactplus.entities.Contact;
import com.contactplus.entities.User;

public interface ContactService {

    public Contact saveContact(Contact contact);

    public Contact getByContactId(long contactId);

    public Contact updateContact(Contact contact, long contactId);

    public void deleteContact(long contactId);

    public Page<Contact> getContactsByUser(User user, int page, int size, String sortBy, String direction);

}
