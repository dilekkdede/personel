package com.person.services.impl;

import com.person.dto.*;
import com.person.dto.dtoBase.BaseResponse;
import com.person.dto.dtoQuery.*;
import com.person.entites.*;
import com.person.enums.RecordStatus;
import com.person.repository.*;
import com.person.services.IPersonelServices;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class PersonelServicesImpl implements IPersonelServices {

    private static SimpleDateFormat dF = new SimpleDateFormat("yyyy/M/dd", Locale.ENGLISH);

    private static Calendar calendar = Calendar.getInstance();  // STATİC YAPMAMIZIN NEDENİ BİR KERE TANIMLAYIP DEFALARCA KULLANIYORUZ.DEĞİŞMİYOR

    @Autowired
    private PersonelRepository personelRepository;

    @Autowired
    private PersonelNativeRepository personelNativeRepository;

    @Autowired
    private PersonelJPARepository personelJPARepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private AdresRepository adresRepository;
    @Autowired
    private ModelMapper modelMapper;


    /// CRUD İŞLEMLERİ////
    @Override
    public BaseResponse save(PersonelSaveDto dto) {

        BaseResponse response = new BaseResponse();


        //KONTROLLER
        if (dto.getFirstName() == null || dto.getLastName() == null) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setMessage("İsim/Soyisim alanları boş geçilemez!");
            response.setData(null);
            return response;
        }

        if (dto.getBolum() == null) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setMessage("Bölüm bilgisi giriniz!");
            response.setData(null);
            return response;
        }

        if (dto.getUnit().getId() == null || dto.getCity().getId() == null) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setMessage("Unit ıd / City ıd / Adres ıd alanları boş geçilemez!");
            response.setData(null);
            return response;
        }


        Personel personel = new Personel();
        personel.setFirstName(dto.getFirstName());
        personel.setLastName(dto.getLastName());
        personel.setUserName(dto.getUserName());
        personel.setCreateBy("Dilek");
        personel.setDescription(dto.getDescription());
        personel.setStatus(RecordStatus.ACTIVE.getValue());
        personel.setCreateDate(new Date());
        personel.setBolum(dto.getBolum());
        personel.setBirthDate(dto.getBirthDate());


        Optional<City> findyCity = cityRepository.findById(dto.getCity().getId());
        if (findyCity.isPresent()) {
            personel.setCity(findyCity.get());
        }


        Optional<Unit> findUnit = unitRepository.findById(dto.getUnit().getId());
        if (findUnit.isPresent()) {
            personel.setUnit(findUnit.get());
        }


        Personel dbPersonel = personelRepository.save(personel);

        Adres adres = new Adres();
        adres.setPersonelId(dbPersonel.getId().intValue());
        adres.setDescription(dto.getAdres().getDescription());
        adres.setStatus(RecordStatus.ACTIVE.getValue());
        adres.setCreateDate(new Date());

        Adres dbAdres = adresRepository.save(adres);

        PersonelDto dtoPersonel = modelMapper.map(dbPersonel, PersonelDto.class);
        AdresDto adresDto = modelMapper.map(dbAdres, AdresDto.class);


        response.setStatus(HttpStatus.CREATED.value());
        response.setMessage("Personel saved successfully");
        response.setData(dtoPersonel);
        return response;

    }

    @Override
    public BaseResponse findAll() {
        BaseResponse response = new BaseResponse();

        List<Personel> personelList = personelRepository.findAllPersons();

        List<PersonelDto> dtoList = modelMapper.map(personelList, new TypeToken<List<PersonelDto>>() {
        }.getType());

        response.setStatus(HttpStatus.OK.value());
        response.setData(dtoList);
        response.setMessage("Personels found successfully");
        return response;
    }


    @Override
    public BaseResponse findById(Long id) {

        BaseResponse response = new BaseResponse();
        Optional<Personel> findPersonel = personelRepository.findById(id);

        if (findPersonel.isEmpty()) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("Personel not found");
            response.setData(null);
            return response;
        }

        PersonelDto personelDto = modelMapper.map(findPersonel.get(), PersonelDto.class);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Personel found successfully");
        response.setData(personelDto);
        return response;
    }

    @Transactional
    public BaseResponse deleteById(Long id) {
        BaseResponse response = new BaseResponse();
        Optional<Personel> optional = personelRepository.findById(id);

        if (optional.isEmpty()) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("Girilen Id değerine ait kayıt bulunamadı.");
            return response;
        }

        Personel personel = optional.get();

        // Adres bağlantılarını kopar
        List<Adres> adresList = adresRepository.findAll();
        for (Adres adres : adresList) {
            if (adres.getPersonelId() != null && adres.getPersonelId().equals(id.intValue())) {
                adres.setPersonelId(null);
                adresRepository.save(adres);
            }
        }

        // Contact bağlantılarını kopar (örnek)
        List<Contact> contactList = contactRepository.findAll();
        for (Contact contact : contactList) {
            if (contact.getPersonelId() != null && contact.getPersonelId().equals(id)) {
                contact.setPersonelId(null);
                contactRepository.save(contact);
            }
        }

        // City ve Unit ilişkilerini kopar
        personel.setCity(null);
        personel.setUnit(null);

        // Personel silinir
        personelRepository.delete(personel);

        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Personel başarıyla silindi.");
        response.setData(null);
        log.info("Personel silindi");
        return response;
    }


    @Override
    public BaseResponse update(Long id, PersonelSaveDto dto) {

        BaseResponse response = new BaseResponse();

        Optional<Personel> findPersonel = personelRepository.findById(id);
        if (findPersonel.isPresent()) {

            if (dto.getFirstName() != null) {
                findPersonel.get().setFirstName(dto.getFirstName());
            }
            if (dto.getLastName() != null) {
                findPersonel.get().setLastName(dto.getLastName());
            }
            if (dto.getUserName() != null) {
                findPersonel.get().setUserName(dto.getUserName());
            }
            if (dto.getDescription() != null) {
                findPersonel.get().setDescription(dto.getDescription());
            }
            if (dto.getBolum() != null) {
                findPersonel.get().setBolum(dto.getBolum());
            }

            if (dto.getBirthDate() != null) {
                findPersonel.get().setBirthDate(dto.getBirthDate());
            }

            if (dto.getUnit() != null && dto.getUnit().getId() != null) {
                Optional<Unit> findUnit = unitRepository.findById(dto.getUnit().getId());
                findPersonel.get().setUnit(findUnit.get());

            }

            if (dto.getCity() != null && dto.getCity().getId() != null) {
                Optional<City> findCity = cityRepository.findById(dto.getCity().getId());
                findPersonel.get().setCity(findCity.get());
            }


            Personel dbPersonel = personelRepository.save(findPersonel.get());
            PersonelDto dtoPersonel = modelMapper.map(dbPersonel, PersonelDto.class);

            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Personel update successfully");
            response.setData(dtoPersonel);
            return response;
        }

        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage("Güncelleneck Id bulunamadı");
        response.setData(null);
        return response;


    }


    /// ////////////QUERYIMPL

    @Override
    public List<BaseResponse> personelListesi() {
        List<BaseResponse> personelListesi = new ArrayList<>();

        List<Personel> personelList = personelNativeRepository.personelListesi();
        BaseResponse response = new BaseResponse();

        response.setData(personelList);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);

        return personelListesi;
    }

    @Override
    public List<BaseResponse> personelListsiID2ve11(long id2, long id11) {
        List<BaseResponse> responseList = new ArrayList<>();
        List<Personel> personelList = personelNativeRepository.persnelListsiID2ve11(id2, id11);
        BaseResponse response = new BaseResponse();
        response.setData(personelList);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Girilen Id bilgileri");
        responseList.add(response);
        return responseList;
    }

    @Override
    public List<BaseResponse> personelListesistatus(int status) {
        List<BaseResponse> personelListesi = new ArrayList<>();

        List<Personel> personelList = personelNativeRepository.personelListesistatus(status);
        BaseResponse response = new BaseResponse();
        response.setData(personelList);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Girilen status bilgileri");
        personelListesi.add(response);

        return personelListesi;
    }

    @Override
    public List<BaseResponse> personelListesiLikeKullanimi(String isim) {
        List<BaseResponse> personelListesi = new ArrayList<>();
        List<Personel> personelList = personelNativeRepository.personelListesiLikeKullanimi(isim);
        BaseResponse response = new BaseResponse();
        response.setData(personelList);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);
        return personelListesi;
    }

    @Override
    public List<BaseResponse> personelListesiBolumBilgisi(String bolum) {
        List<BaseResponse> personelListesi = new ArrayList<>();
        List<Personel> personelList = personelNativeRepository.personelListesiBolumBilgisi(bolum);
        BaseResponse response = new BaseResponse();
        response.setData(personelList);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);
        return personelListesi;
    }

    @Override
    public List<BaseResponse> personelListesiBolumBilgisiUpper(String bolum) {
        List<BaseResponse> personelListesi = new ArrayList<>();
        List<Personel> personelList = personelNativeRepository.personelListesiBolumBilgisiUpper(bolum);
        BaseResponse response = new BaseResponse();
        response.setData(personelList);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);
        return personelListesi;
    }

    @Override
    public List<BaseResponse> personelListesiBolumBilgisiLower(String bolum) {
        List<BaseResponse> personelListesi = new ArrayList<>();
        List<Personel> personelList = personelNativeRepository.personelListesiBolumBilgisiLower(bolum);
        BaseResponse response = new BaseResponse();
        response.setData(personelList);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);

        return personelListesi;
    }

    @Override
    public List<BaseResponse> personelListesiDogumGunu(PersonelDtoDogumGunu dto) {
        List<BaseResponse> personelListesi = new ArrayList<>();
        List<Personel> personelList = personelNativeRepository.personelListesiDogumGunu(dto.getDogumGunu());
        BaseResponse response = new BaseResponse();
        response.setData(personelList);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);
        return personelListesi;
    }

    @Override
    public List<BaseResponse> personelListesiDogumGunuNull() {
        List<BaseResponse> personelListesi = new ArrayList<>();
        List<Personel> personelList = personelNativeRepository.personelListesiDogumGunuNull();
        BaseResponse response = new BaseResponse();
        response.setData(personelList);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);
        return personelListesi;
    }

    @Override
    public List<BaseResponse> personelListesiDogumGunuNotNull() {
        List<BaseResponse> personelListesi = new ArrayList<>();
        List<Personel> personelList = personelNativeRepository.personelListesiDogumGunuNotNull();
        BaseResponse response = new BaseResponse();
        response.setData(personelList);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);
        return personelListesi;
    }

    @Override
    public BaseResponse personelListesiCount() {
        BaseResponse response = new BaseResponse();
        int count = personelNativeRepository.personelListesiCount();
        response.setData(count);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Peronel sayısı: " + count);
        return response;

    }

    @Override
    public List<BaseResponse> personelListesiInKullanimi(List<PersonelDtoIdIn> dtoIdIn) {
        List<BaseResponse> personelListesi = new ArrayList<>();
        List<Long> idList = new ArrayList<>();
        for (PersonelDtoIdIn personelDtoIdIn : dtoIdIn) {
            idList.add(personelDtoIdIn.getId());
        }
        List<Personel> personelList = personelNativeRepository.personelListesiInKullanimi(idList);
        BaseResponse response = new BaseResponse();
        response.setData(personelList);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);

        return personelListesi;
    }

    @Override
    public BaseResponse personelListesiCountvestatus(int status) {
        int count = personelNativeRepository.personelListesiCountvestatus(status);
        BaseResponse response = new BaseResponse();
        response.setData(count);
        response.setStatus(HttpStatus.OK.value());

        return response;
    }

    //JPA QUERYS

    @Override
    public List<BaseResponse> personelListesiJpa() {
        List<BaseResponse> personelListesi = new ArrayList<>();
        List<Personel> liste = personelJPARepository.personelListesi();
        BaseResponse response = new BaseResponse();
        response.setData(liste);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);
        return personelListesi;
    }

    @Override
    public List<BaseResponse> personelListesiid2veyaid11jpa(long id2, long id11) {
        List<BaseResponse> personelListesi = new ArrayList<>();
        List<Personel> list = personelJPARepository.personelListesiid2veyaid11jpa(id2, id11);
        BaseResponse response = new BaseResponse();
        response.setData(list);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);
        return personelListesi;
    }

    @Override
    public List<BaseResponse> personelListesiStatusJpa(int status) {
        List<BaseResponse> personelListesi = new ArrayList<>();
        List<Personel> list = personelJPARepository.personelListesiStatusJpa(status);
        BaseResponse response = new BaseResponse();
        response.setData(list);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);
        return personelListesi;
    }

    @Override
    public List<BaseResponse> personelListesiLikeJpa(String karakter) {
        List<BaseResponse> personelListesi = new ArrayList<>();
        List<Personel> list = personelJPARepository.personelListesiLikeJpa(karakter);
        BaseResponse response = new BaseResponse();
        response.setData(list);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);
        return personelListesi;
    }

    @Override
    public List<BaseResponse> personelListesibolumJpa(String bolum) {
        List<BaseResponse> personelListesi = new ArrayList<>();
        List<Personel> list = personelJPARepository.personelListesibolumJpa(bolum);
        BaseResponse response = new BaseResponse();
        response.setData(list);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);
        return personelListesi;
    }

    @Override
    public List<BaseResponse> personelListesibolumUpperJpa(String bolum) {
        List<BaseResponse> personelListesi = new ArrayList<>();
        List<Personel> list = personelJPARepository.personelListesibolumUpperJpa(bolum);
        BaseResponse response = new BaseResponse();
        response.setData(list);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);
        return personelListesi;
    }

    @Override
    public List<BaseResponse> personelListesibolumLowerJpa(String bolum) {
        List<BaseResponse> personelListesi = new ArrayList<>();
        List<Personel> list = personelJPARepository.personelListesibolumLowerJpa(bolum);
        BaseResponse response = new BaseResponse();
        response.setData(list);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);
        return personelListesi;
    }

    @Override
    public List<BaseResponse> personelListesiDogumGunuJpa(PersonelDtoDogumGunu dto) {
        List<BaseResponse> personelListesi = new ArrayList<>();
        List<Personel> list = personelJPARepository.personelListesiDogumGunuJpa(dto.getDogumGunu());
        BaseResponse response = new BaseResponse();
        response.setData(list);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);
        return personelListesi;
    }

    @Override
    public List<BaseResponse> personelListesiDogumGunuIsNotNullJpa() {
        List<BaseResponse> personelListesi = new ArrayList<>();
        List<Personel> list = personelJPARepository.personelListesiDogumGunuIsNotNullJpa();
        BaseResponse response = new BaseResponse();
        response.setData(list);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);
        return personelListesi;
    }

    @Override
    public BaseResponse personelListesiCountJpa() {
        BaseResponse response = new BaseResponse();
        int liste = personelJPARepository.personelListesiCountJpa();
        response.setData(liste);
        response.setStatus(HttpStatus.OK.value());

        return response;
    }

    @Override
    public List<BaseResponse> personelListesiInKullanimiJpa(List<PersonelDtoIdIn> dtoIdIn) {
        List<BaseResponse> personelListesi = new ArrayList<>();

        List<Long> idList = new ArrayList<>();
        for (PersonelDtoIdIn personelDtoIdIn : dtoIdIn) {
            idList.add(personelDtoIdIn.getId());
        }
        List<Personel> response = personelJPARepository.personelListesiInKullanimiJpa(idList);
        BaseResponse response1 = new BaseResponse();
        response1.setData(response);
        response1.setStatus(HttpStatus.OK.value());
        personelListesi.add(response1);
        return personelListesi;
    }

    @Override
    public List<BaseResponse> personelListesiInCreateByJpa(List<PersonelDtoCreateByIn> dtoIn) {
        List<BaseResponse> personelListesi = new ArrayList<>();
        List<String> bosListe = new ArrayList<>();
        for (PersonelDtoCreateByIn str : dtoIn) {
            bosListe.add(str.getCreateBy());
        }

        List<Personel> liste = personelJPARepository.personelListesiInCreateByJpa(bosListe);
        BaseResponse response = new BaseResponse();
        response.setData(liste);
        response.setStatus(HttpStatus.OK.value());
        personelListesi.add(response);
        return personelListesi;
    }

    @Override
    public List<BaseResponse> personelListesiIsimVeSoyisimHaricDigerleriNull() {
        List<BaseResponse> personelListesi = new ArrayList<>();
        List<Personel> response = new ArrayList<>();
        List<Personel> islem = personelJPARepository.personelListesi();

        if (islem != null) {
            for (Personel personel : islem) {
                personel.setBirthDate(null);
                personel.setCreateBy(null);
                personel.setCreateDate(null);
                personel.setCreateBy(null);
                personel.setBolum(null);
                personel.setDescription(null);
                personel.setUserName(null);
                //    personel.setCityId(null);
                response.add(personel);

            }
        }
        BaseResponse response1 = new BaseResponse();
        response1.setData(response);
        response1.setStatus(HttpStatus.OK.value());
        personelListesi.add(response1);
        return personelListesi;
    }

    @Override
    public List<PersonelDtoAdSoyadBolum> personelListesiAdSoyadBolum() {
        List<PersonelDtoAdSoyadBolum> response = new ArrayList<>();

        List<Personel> personelList = personelJPARepository.personelListesiDogumGunuIsNotNullJpa();
        if (personelList != null) {
            for (Personel personel : personelList) {
                PersonelDtoAdSoyadBolum personelDto = new PersonelDtoAdSoyadBolum();
                personelDto.setAd(personel.getFirstName());
                personelDto.setSoyad(personel.getLastName());
                personelDto.setBolum(personel.getBolum());
                personelDto.setAdSoyad(personel.getFirstName() + " " + personel.getLastName());
                response.add(personelDto);
            }

        }
        return response;
    }

    @Override
    public List<PersonelDtoAdSoyadBolum> personelListesiAdSoyadBolumKucukEsit4() {
        List<PersonelDtoAdSoyadBolum> response = new ArrayList<>();

        List<Personel> personelList = personelJPARepository.personelListesiDogumGunuIsNotNullJpa();
        if (personelList != null) {
            for (Personel personel : personelList) {
                PersonelDtoAdSoyadBolum personelDto = new PersonelDtoAdSoyadBolum();
                if (personel.getFirstName().length() <= 4) {
                    personelDto.setAd(personel.getFirstName());
                    personelDto.setSoyad(personel.getLastName());
                    personelDto.setBolum(personel.getBolum());
                    personelDto.setAdSoyad(personel.getFirstName() + " " + personel.getLastName());
                    response.add(personelDto);
                }

            }

        }
        return response;
    }

    @Override
    public List<BaseResponse> personelListesiBirthDayNotOlanaSistemTarihiSetleme() {
        List<BaseResponse> responseList = new ArrayList<>();
        List<Personel> response = new ArrayList<>();
        List<Personel> personelListesi = personelJPARepository.personelListesi();
        if (personelListesi != null) {
            for (Personel personel : personelListesi) {
                if (personel.getBirthDate() == null) {
                    Date newDate = new Date();
                    personel.setBirthDate(newDate);
                }
                response.add(personel);
            }
        }
        BaseResponse response1 = new BaseResponse();
        response1.setData(response);
        response1.setStatus(HttpStatus.OK.value());
        responseList.add(response1);
        return responseList;

    }

    @Override
    public List<BaseResponse> personelListesiIkiTarihAraliginiDondurme(PersonelDtoTarihAraligi dto) throws ParseException {

        List<BaseResponse> personelListesi = new ArrayList<>();
        dF.setTimeZone(TimeZone.getTimeZone("America/New_York"));

        Date dateBas = dF.parse(dto.getDateBas());
        Date dateSon = dF.parse(dto.getDateSon());

        List<Personel> response = personelJPARepository.personelListesiIkiTarihAraliginiDondurme(dateBas, dateSon);
        BaseResponse response1 = new BaseResponse();
        response1.setData(response);
        response1.setStatus(HttpStatus.OK.value());
        personelListesi.add(response1);
        return personelListesi;

    }

    @Override
    public List<BaseResponse> personelListesiIkiTarihAraligindakiCreateDate(PersonelDtoTarihAraligi dto) throws ParseException {
        List<BaseResponse> personelListesi = new ArrayList<>();
        dF.setTimeZone(TimeZone.getTimeZone("America/New_York"));

        Date dateBas = dF.parse(dto.getDateBas());
        Date dateSon = dF.parse(dto.getDateSon());

        List<Personel> personelList = personelJPARepository.personelListesiIkiTarihAraligindakiCreateDate(dateBas, dateSon);
        BaseResponse response1 = new BaseResponse();
        response1.setData(personelList);
        response1.setStatus(HttpStatus.OK.value());
        personelListesi.add(response1);
        return personelListesi;


    }

    @Override
    public List<BaseResponse> personelListesiIkiTarihAraligindakiCreateDateWithSistemTarihi() throws ParseException {
        List<BaseResponse> personelListesi = new ArrayList<>();
        Date dateBas = new Date(); // SİSTEM TARİHİ ALMA
        int eklenecekGun = 14;

        calendar.setTime(dateBas);
        calendar.add(Calendar.DAY_OF_YEAR, eklenecekGun);
        Date dateSon = calendar.getTime();

        List<Personel> personelList = personelJPARepository.personelListesiIkiTarihAraligindakiCreateDate(dateBas, dateSon);
        BaseResponse response1 = new BaseResponse();
        response1.setData(personelList);
        response1.setStatus(HttpStatus.OK.value());
        personelListesi.add(response1);
        return personelListesi;
    }

    @Override
    public BaseResponse findPersonelCountByUnit() {
        BaseResponse response = new BaseResponse();
        List<PersonelUnitCountDto> personelUnitCount = new ArrayList<>();

        List<PersonelUnitCountDto> result = personelJPARepository.findPersonelCountByUnit();


        response.setData(result);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage(HttpStatus.OK.getReasonPhrase());
        return response;


    }

    @Override
    public BaseResponse findPersonelCountByCity() {
        BaseResponse response = new BaseResponse();
        List<PersonelCityCountDto> personelCityCount = new ArrayList<>();
        List<PersonelCityCountDto> result = personelJPARepository.findPersonelCountByCity();

        response.setData(result);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage(HttpStatus.OK.getReasonPhrase());
        return response;
    }


}
