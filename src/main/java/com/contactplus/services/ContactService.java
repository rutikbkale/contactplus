package com.contactplus.services;

import java.util.List;
import com.contactplus.entities.Contact;
import com.contactplus.entities.User;

public interface ContactService {

    public Contact saveContact(Contact contact);

    public List<Contact> getContactsByUser(User user);

}
