package com.person.services.impl;

import com.person.dto.ContactDto;
import com.person.dto.ContactSaveDto;
import com.person.dto.dtoBase.BaseResponse;
import com.person.entites.Contact;
import com.person.enums.RecordStatus;
import com.person.repository.ContactRepository;
import com.person.services.IContactServices;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ContactServicesImpl implements IContactServices {

    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BaseResponse save(ContactSaveDto dto) {
        BaseResponse response = new BaseResponse();

        Contact contact = new Contact();
        contact.setPersonelId(dto.getPersonelId());
        contact.setType(dto.getType());
        contact.setContact(dto.getContact());
        contact.setStatus(RecordStatus.ACTIVE.getValue());
        contact.setCreateDate(new Date());

        Contact dbContact = contactRepository.save(contact);

        ContactDto contactDto = modelMapper.map(dbContact, ContactDto.class);
        response.setStatus(HttpStatus.CREATED.value());
        response.setData(contactDto);
        response.setMessage("Contact saved successfully");
        return response;


    }

    @Override
    public BaseResponse findAll() {
        BaseResponse response = new BaseResponse();
        List<Contact> contacts = contactRepository.findAll();
        List<ContactDto> dtoList = modelMapper.map(contacts, List.class);
        response.setStatus(HttpStatus.OK.value());
        response.setData(dtoList);
        response.setMessage("All contacts saved successfully");
        return response;
    }

    @Override
    public BaseResponse findById(Long id) {

        BaseResponse response = new BaseResponse();
        Optional<Contact> optionalContact = contactRepository.findById(id);
        if (optionalContact.isPresent()) {
            ContactDto contactDto = modelMapper.map(optionalContact.get(), ContactDto.class);
            response.setStatus(HttpStatus.OK.value());
            response.setData(contactDto);
            response.setMessage("Contact found successfully");

        }
        return response;

    }

    @Override
    public BaseResponse deleteById(Long id) {
        BaseResponse baseResponse = new BaseResponse();
        log.info("Contact silindi");
        contactRepository.deleteById(id);
        baseResponse.setStatus(HttpStatus.OK.value());
        baseResponse.setMessage("Contact başarılı bir şekilde silindi");
        return baseResponse;

    }

    @Override
    public BaseResponse update(Long id, ContactSaveDto dto) {

        BaseResponse response = new BaseResponse();
        Optional<Contact> findContact = contactRepository.findById(id);
        if (findContact.isPresent()) {
            findContact.get().setPersonelId(dto.getPersonelId());
            findContact.get().setType(dto.getType());
            findContact.get().setContact(dto.getContact());

            Contact dbContact = contactRepository.save(findContact.get());
            ContactDto contactDto = modelMapper.map(dbContact, ContactDto.class);
            response.setStatus(HttpStatus.OK.value());
            response.setData(contactDto);
            response.setMessage("Contact updated successfully");

        }

        return response;
    }



    @Override
    public BaseResponse findByIdPersonel(Long id) {
        BaseResponse response = new BaseResponse();

        List<Contact> findContact = contactRepository.findContactByPersonelId(id);

        response.setData(findContact);
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("ContactByPerosnelId found successfully");

        return response;
    }
}
