package com.person.repository;

import com.person.entites.Personel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonelRepository extends JpaRepository<Personel, Long> {

    //query -> Entity üzerinde
    //nativequery ->Tablo üzerinde
    //namedquery -> Entity üzerinde
    @Query("select p from Personel p order by p.createDate desc")
    List<Personel> findAllPersons();


}
