package com.contactplus.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contactplus.entities.Contact;
import com.contactplus.entities.User;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    public List<Contact> findByUser(User user);

}
