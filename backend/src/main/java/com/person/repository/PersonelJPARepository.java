package com.person.repository;

import com.person.dto.PersonelCityCountDto;
import com.person.dto.PersonelUnitCountDto;
import com.person.entites.Personel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PersonelJPARepository extends JpaRepository<Personel, Long> {

    //query -> Entity üzerinde
    //nativequery ->Tablo üzerinde
    //namedquery -> Entity üzerinde

    @Query(value = "select p from Personel p")
    List<Personel> personelListesi();

    @Query(value = "select p from Personel p where p.id =:id2 or p.id =:id11")
    List<Personel> personelListesiid2veyaid11jpa(long id2, long id11);

    @Query(value = "select p from Personel p where p.status =:status")
    List<Personel> personelListesiStatusJpa(int status);


    @Query(value = "select p from Personel p where p.firstName like %:karakter%")
    List<Personel> personelListesiLikeJpa(String karakter);

    @Query(value = "select p from Personel p where p.bolum =:bolum")
    List<Personel> personelListesibolumJpa(String bolum);

    @Query(value = "select p from Personel p where upper(p.bolum) =upper(:bolum) ")
    List<Personel> personelListesibolumUpperJpa(String bolum);

    @Query(value = "select p from Personel p where lower(p.bolum) =lower(:bolum) ")
    List<Personel> personelListesibolumLowerJpa(String bolum);

    @Query(value = "select p from Personel p where p.birthDate =:dogumGunu  ")
    List<Personel> personelListesiDogumGunuJpa(Date dogumGunu);

    @Query(value = "select p from Personel p where p.birthDate is not null ")
    List<Personel> personelListesiDogumGunuIsNotNullJpa();

    @Query(value = "select count (p) from Personel p")
    int personelListesiCountJpa();

    @Query(value = "select p from Personel p where p.id in :dtoIdIn")
    List<Personel> personelListesiInKullanimiJpa(List<Long> dtoIdIn);

    @Query(value = "select p from Personel p where p.createBy in :dtoIn")
    List<Personel> personelListesiInCreateByJpa(List<String> dtoIn);

    @Query(value = "select p from Personel p where p.birthDate between to_date(:dateBas,'YYYY-MM-DD') and to_date(:dateSon,'YYYY-MM-DD')")
    List<Personel> personelListesiIkiTarihAraliginiDondurme(Date dateBas, Date dateSon);


    @Query(value = "select p from Personel p where p.createDate between to_date(:dateBas,'YYYY-MM-DD') and to_date(:dateSon,'YYYY-MM-DD')")
    List<Personel> personelListesiIkiTarihAraligindakiCreateDate(Date dateBas, Date dateSon);

    @Query("SELECT new com.person.dto.PersonelUnitCountDto(u.code, u.name, COUNT(p))  " +
            "FROM Unit u LEFT JOIN Personel p ON p.unit = u " +
            "GROUP BY u.code, u.name")
    List<PersonelUnitCountDto> findPersonelCountByUnit();

    @Query("SELECT new com.person.dto.PersonelCityCountDto(u.name ,COUNT(p)) " +
            "FROM City u INNER JOIN Personel p ON p.city = u " +
            "GROUP BY  u.name")
    List<PersonelCityCountDto> findPersonelCountByCity();

}
