package com.person.services;

import com.person.dto.PersonelSaveDto;
import com.person.dto.dtoBase.BaseResponse;
import com.person.dto.dtoQuery.*;

import java.text.ParseException;
import java.util.List;

public interface IPersonelServices {


    /// ////////////CRUD İŞLEMLERİ //////////////////////////
    BaseResponse save(PersonelSaveDto dto);

    BaseResponse findAll();

    BaseResponse findById(Long id);

    BaseResponse deleteById(Long id);

    BaseResponse update(Long id, PersonelSaveDto dto);

    /// ////////////////////////////////////////////////////////////////////////////////////////


    List<BaseResponse> personelListesi();

    List<BaseResponse> personelListsiID2ve11(long id2, long id11);

    List<BaseResponse> personelListesistatus(int status);

    List<BaseResponse> personelListesiLikeKullanimi(String isim);

    List<BaseResponse> personelListesiBolumBilgisi(String bolum);

    List<BaseResponse> personelListesiBolumBilgisiUpper(String bolum);

    List<BaseResponse> personelListesiBolumBilgisiLower(String bolum);

    List<BaseResponse> personelListesiDogumGunu(PersonelDtoDogumGunu dto);

    List<BaseResponse> personelListesiDogumGunuNull();

    List<BaseResponse> personelListesiDogumGunuNotNull();

    BaseResponse personelListesiCount();

    List<BaseResponse> personelListesiInKullanimi(List<PersonelDtoIdIn> dtoIdIn);

    BaseResponse personelListesiCountvestatus(int status);


    //JPA QUERYS

    List<BaseResponse> personelListesiJpa();

    List<BaseResponse> personelListesiid2veyaid11jpa(long id2, long id11);

    List<BaseResponse> personelListesiStatusJpa(int status);

    List<BaseResponse> personelListesiLikeJpa(String karakter);

    List<BaseResponse> personelListesibolumJpa(String bolum);

    List<BaseResponse> personelListesibolumUpperJpa(String bolum);

    List<BaseResponse> personelListesibolumLowerJpa(String bolum);

    List<BaseResponse> personelListesiDogumGunuJpa(PersonelDtoDogumGunu dto);

    List<BaseResponse> personelListesiDogumGunuIsNotNullJpa();

    BaseResponse personelListesiCountJpa();

    List<BaseResponse> personelListesiInKullanimiJpa(List<PersonelDtoIdIn> dtoIdIn);

    List<BaseResponse> personelListesiInCreateByJpa(List<PersonelDtoCreateByIn> dtoIn);

    List<BaseResponse> personelListesiIsimVeSoyisimHaricDigerleriNull();

    List<PersonelDtoAdSoyadBolum> personelListesiAdSoyadBolum();

    List<PersonelDtoAdSoyadBolum> personelListesiAdSoyadBolumKucukEsit4();

    List<BaseResponse> personelListesiBirthDayNotOlanaSistemTarihiSetleme();

    List<BaseResponse> personelListesiIkiTarihAraliginiDondurme(PersonelDtoTarihAraligi dto) throws ParseException;

    List<BaseResponse> personelListesiIkiTarihAraligindakiCreateDate(PersonelDtoTarihAraligi dto) throws ParseException;

    List<BaseResponse> personelListesiIkiTarihAraligindakiCreateDateWithSistemTarihi() throws ParseException;

    BaseResponse findPersonelCountByUnit();

    BaseResponse findPersonelCountByCity();

}


