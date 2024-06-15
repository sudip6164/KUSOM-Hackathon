package com.pollution.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pollution.model.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

}
