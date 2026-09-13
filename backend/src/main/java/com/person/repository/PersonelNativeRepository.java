package com.person.repository;

import com.person.entites.Personel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PersonelNativeRepository extends JpaRepository<Personel, Long> {

    //query -> Entity üzerinde
    //nativequery ->Tablo üzerinde
    //namedquery -> Entity üzerinde

    @Query(value = "select * from personel p", nativeQuery = true)
    List<Personel> personelListesi();

    @Query(value = "select * from  personel p where p.id =:id2 or p.id =:id11", nativeQuery = true)
    List<Personel> persnelListsiID2ve11(long id2,long id11);

    @Query(value = "select * from personel p where p.status =:status", nativeQuery = true)
    List<Personel> personelListesistatus(int status);

    @Query(value = "select * from personel p where p.first_name like %:isim%", nativeQuery = true)
    List<Personel> personelListesiLikeKullanimi(String isim);

    @Query(value = "select * from personel p where p.bolum =:bolum", nativeQuery = true)
    List<Personel> personelListesiBolumBilgisi(String bolum);

    @Query(value = "select * from personel p where upper(p.bolum) = upper(:bolum)" , nativeQuery = true)
    List<Personel> personelListesiBolumBilgisiUpper(String bolum);

    @Query(value = "select * from personel p where lower(p.bolum) = lower(:bolum)" , nativeQuery = true)
    List<Personel> personelListesiBolumBilgisiLower(String bolum);

    @Query(value = "select * from personel p where p.birth_day =:dogumGunu" , nativeQuery = true)
    List<Personel> personelListesiDogumGunu(Date dogumGunu);

    @Query(value = "select * from personel p where p.birth_day is null " , nativeQuery = true)
    List<Personel> personelListesiDogumGunuNull();

    @Query(value = "select * from personel p where p.birth_day is not null " , nativeQuery = true)
    List<Personel> personelListesiDogumGunuNotNull();

    @Query(value = "select count(*) from personel p" , nativeQuery = true)
    int personelListesiCount();

    @Query(value = "select * from personel p where p.id in :dtoIdIn" , nativeQuery = true)
    List<Personel> personelListesiInKullanimi(List<Long> dtoIdIn);

    @Query(value = "select count(*) from personel p where p.status =:status" , nativeQuery = true)
    int personelListesiCountvestatus(int status);

}
