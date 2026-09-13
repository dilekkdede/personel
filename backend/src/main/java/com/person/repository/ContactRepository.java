package com.person.repository;

import com.person.entites.Adres;
import com.person.entites.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Query("SELECT a FROM Contact a WHERE a.personelId = :personelId")
    List<Contact> findContactByPersonelId(Long personelId);

}
