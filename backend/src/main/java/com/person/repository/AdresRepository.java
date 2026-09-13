package com.person.repository;

import com.person.entites.Adres;
import com.person.entites.Personel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdresRepository extends JpaRepository<Adres, Long> {

    @Query("SELECT a FROM Adres a WHERE a.personelId = :personelId")
    List<Adres> findAdresByPersonelId(Integer personelId);


}
