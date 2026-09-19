package com.person.repository;

import com.person.entites.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByPersonel_Id(Long personelId);

    void deleteByPersonel_Id(Long personelId);
}
