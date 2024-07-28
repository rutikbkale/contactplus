package com.contactplus.services;

import org.springframework.data.domain.Page;
import com.contactplus.entities.Contact;
import com.contactplus.entities.User;

public interface ContactService {

    public Contact saveContact(Contact contact);

    public Contact getByContactId(int contactId);

    public void deleteContact(int contactId);

    public Page<Contact> getContactsByUser(User user, int page, int size, String sortBy, String direction);

}
