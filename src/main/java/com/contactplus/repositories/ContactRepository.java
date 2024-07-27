package com.contactplus.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contactplus.entities.Contact;
import com.contactplus.entities.User;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    public Contact findByContactId(int contactId);

    public Page<Contact> findByUser(User user, Pageable pageable);

}
