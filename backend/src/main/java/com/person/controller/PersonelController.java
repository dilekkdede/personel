package com.person.controller;

import com.person.dto.PersonelSaveDto;
import com.person.dto.dtoBase.BaseResponse;
import com.person.dto.dtoQuery.*;
import com.person.services.IPersonelServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@CrossOrigin(allowedHeaders = "*", origins = "*")
@RestController
@RequestMapping("/rest/api/personel")
public class PersonelController {

    @Autowired
    private IPersonelServices personelServices;


    @PostMapping(path = "/save")
    public BaseResponse save(@RequestBody PersonelSaveDto personel) {
        return personelServices.save(personel);
    }

    @GetMapping(path = "/list")
    public BaseResponse findAll() {
        return personelServices.findAll();

    }

    @GetMapping(path = "/get-id/{id}")
    public BaseResponse findById(@PathVariable(name = "id") Long id) {
        return personelServices.findById(id);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping(path = "/delete/{id}")
    public BaseResponse deleteById(@PathVariable(name = "id") Long id) {
        return personelServices.deleteById(id);
    }


    @PutMapping(path = "/update/{id}")
    public BaseResponse updatePersonel(@PathVariable(name = "id") Long id, @RequestBody PersonelSaveDto personel) {
        return personelServices.update(id, personel);
    }

    //@uery işlemleri

    @GetMapping(path = "/personel-listesi")
    public List<BaseResponse> personelListesi() {
        return personelServices.personelListesi();
    }

    @GetMapping(path = "/personel-listesi-id2-id11/{id2}/{id11}")
    public List<BaseResponse> personelListsiID2ve11(@PathVariable(name = "id2") long id2, @PathVariable(name = "id11") long id11) {
        return personelServices.personelListsiID2ve11(id2, id11);
    }


    @GetMapping(path = "/personel-listesi-status/{status}")
    public List<BaseResponse> personelListsistatus(@PathVariable(name = "status") int status) {
        return personelServices.personelListesistatus(status);
    }

    @GetMapping(path = "/personel-listesi-like-kullanimi/{isim}")
    public List<BaseResponse> personelListsistatus(@PathVariable(name = "isim") String isim) {
        return personelServices.personelListesiLikeKullanimi(isim);
    }

    @GetMapping(path = "/personel-listesi-bolum/{bolum}")
    public List<BaseResponse> personelListesiBolumBilgisi(@PathVariable(name = "bolum") String bolum) {
        return personelServices.personelListesiBolumBilgisi(bolum);
    }

    @GetMapping(path = "/personel-listesi-bolum-upper/{bolum}")
    public List<BaseResponse> personelListesiBolumBilgisiUpper(@PathVariable(name = "bolum") String bolum) {
        return personelServices.personelListesiBolumBilgisiUpper(bolum);
    }

    @GetMapping(path = "/personel-listesi-bolum-lower/{bolum}")
    public List<BaseResponse> personelListesiBolumBilgisiLower(@PathVariable(name = "bolum") String bolum) {
        return personelServices.personelListesiBolumBilgisiLower(bolum);
    }

    @PostMapping(path = "/personel-listesi-dogum-gunu")
    public List<BaseResponse> personelListesiDogumGunu(@RequestBody PersonelDtoDogumGunu dto) {
        return personelServices.personelListesiDogumGunu(dto);
    }

    @GetMapping(path = "/personel-listesi-dogum-gunu-null")
    public List<BaseResponse> personelListesiDogumGunuNull() {
        return personelServices.personelListesiDogumGunuNull();
    }

    @GetMapping(path = "/personel-listesi-dogum-gunu-not-null")
    public List<BaseResponse> personelListesiDogumGunuNotNull() {
        return personelServices.personelListesiDogumGunuNotNull();
    }

    @GetMapping(path = "personel-listesi-count")
    public BaseResponse personelListesiCount() {
        return personelServices.personelListesiCount();
    }


    @PostMapping(path = "personel-listesi-id-in")
    public List<BaseResponse> personelListesiInKullanimi(@RequestBody List<PersonelDtoIdIn> dtoIdIn) {
        return personelServices.personelListesiInKullanimi(dtoIdIn);
    }

    @GetMapping(path = "personel-listesi-count-status/{status}")
    public BaseResponse personelListesiCountvestatus(@PathVariable(name = "status") int status) {
        return personelServices.personelListesiCountvestatus(status);
    }

    //JPA QUERYS

    @GetMapping(path = "/personel-listesi-jpa")
    public List<BaseResponse> personelListesiJpa() {
        return personelServices.personelListesiJpa();
    }

    @GetMapping(path = "/personel-listesi-id-jpa/{id2}/{id11}")
    public List<BaseResponse> personelListesiid2veyaid11jpa(@PathVariable(name = "id2") long id2, @PathVariable(name = "id11") long id11) {
        return personelServices.personelListesiid2veyaid11jpa(id2, id11);
    }

    @GetMapping(path = "/personel-listesi-status-jpa/{status}")
    public List<BaseResponse> personelListesiStatusJpa(@PathVariable(name = "status") int status) {
        return personelServices.personelListesiStatusJpa(status);
    }

    @GetMapping(path = "/personel-listesi-like-jpa/{karakter}")
    public List<BaseResponse> personelListesiLikeJpa(@PathVariable(name = "karakter") String karakter) {
        return personelServices.personelListesiLikeJpa(karakter);
    }

    @GetMapping(path = "/personel-listesi-bolum-jpa/{bolum}")
    public List<BaseResponse> personelListesiBolumJpa(@PathVariable(name = "bolum") String bolum) {
        return personelServices.personelListesibolumJpa(bolum);
    }

    @GetMapping(path = "/personel-listesi-upper-bolum-jpa/{bolum}")
    public List<BaseResponse> personelListesiUpperBolumJpa(@PathVariable(name = "bolum") String bolum) {
        return personelServices.personelListesibolumUpperJpa(bolum);
    }


    @GetMapping(path = "/personel-listesi-lower-bolum-jpa/{bolum}")
    public List<BaseResponse> personelListesiLowerBolumJpa(@PathVariable(name = "bolum") String bolum) {
        return personelServices.personelListesibolumLowerJpa(bolum);
    }

    @PostMapping(path = "/personel-listesi-dogum-gunu-jpa")
    public List<BaseResponse> personelListesiDogumGunuJpa(@RequestBody PersonelDtoDogumGunu dto) {
        return personelServices.personelListesiDogumGunuJpa(dto);
    }

    @GetMapping(path = "/personel-listesi-dogum-not-null-jpa")
    public List<BaseResponse> personelListesiDogumGunuIsNotNullJpa() {
        return personelServices.personelListesiDogumGunuIsNotNullJpa();
    }

    @GetMapping(path = "/personel-listesi-count-jpa")
    public BaseResponse personelListesiCountJpa() {
        return personelServices.personelListesiCountJpa();
    }

    @PostMapping(path = "/personel-listesi-in-jpa")
    public List<BaseResponse> personelListesiInKullanimiJpa(@RequestBody List<PersonelDtoIdIn> dto) {
        return personelServices.personelListesiInKullanimiJpa(dto);
    }

    @PostMapping(path = "/personel-listesi-in-createBy-jpa")
    public List<BaseResponse> personelListesiInCreateByJpa(@RequestBody List<PersonelDtoCreateByIn> dto) {
        return personelServices.personelListesiInCreateByJpa(dto);
    }

    @GetMapping(path = "/personel-listesi-isim-ve-soyisim-haric-null")
    public List<BaseResponse> personelListesiIsimVeSoyisimHaricDigerleriNull() {
        return personelServices.personelListesiIsimVeSoyisimHaricDigerleriNull();
    }

    @GetMapping(path = "/personel-listesi-ad-soyad-bolum-jpa")
    public List<PersonelDtoAdSoyadBolum> personelListesiAdSoyadBolum() {
        return personelServices.personelListesiAdSoyadBolum();
    }

    @GetMapping(path = "/personel-listesi-ad-soyad-bolum-kucuk-esit-jpa")
    public List<PersonelDtoAdSoyadBolum> personelListesiAdSoyadBolumKucukEsit4() {
        return personelServices.personelListesiAdSoyadBolumKucukEsit4();
    }

    @GetMapping(path = "/personel-listesi-birth-day-not-olana-sistem-tarihini-setleme-jpa")
    public List<BaseResponse> personelListesiBirthDayNotOlanaSistemTarihiSetleme() {
        return personelServices.personelListesiBirthDayNotOlanaSistemTarihiSetleme();
    }

    @PostMapping(path = "/personel-listesi-tarih-araligi-dondurme")
    public List<BaseResponse> personelListesiIkiTarihAraliginiDondurme(@RequestBody PersonelDtoTarihAraligi dto) throws ParseException {
        return personelServices.personelListesiIkiTarihAraliginiDondurme(dto);
    }

    @PostMapping(path = "/personel-listesi-tarih-araligi-createDate-dondurme")
    public List<BaseResponse> personelListesiIkiTarihAraligindakiCreateDate(@RequestBody PersonelDtoTarihAraligi dto) throws ParseException {
        return personelServices.personelListesiIkiTarihAraligindakiCreateDate(dto);
    }

    @GetMapping(path = "/personel-listesi-createDate-with-sistem-tarihi")
    public List<BaseResponse> personelIkiTarihAraligindakiCreateDateWithSistemTarihi() throws ParseException {
        return personelServices.personelListesiIkiTarihAraligindakiCreateDateWithSistemTarihi();
    }

    @GetMapping("/countByUnit")
    public BaseResponse countByUnit() {
        return personelServices.findPersonelCountByUnit();
    }

    @GetMapping("/countByCity")
    public BaseResponse countByCity() {
        return personelServices.findPersonelCountByCity();
    }


}
