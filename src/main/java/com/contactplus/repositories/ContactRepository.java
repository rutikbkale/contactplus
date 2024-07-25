package com.contactplus.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.contactplus.entities.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

}
