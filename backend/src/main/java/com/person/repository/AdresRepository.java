package com.person.repository;

import com.person.entites.Adres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdresRepository extends JpaRepository<Adres, Long> {
    List<Adres> findByPersonel_Id(Long personelId);

    void deleteByPersonel_Id(Long personelId);
}
