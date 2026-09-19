package com.person.services.impl;

import com.person.dto.ContactDto;
import com.person.dto.ContactSaveDto;
import com.person.dto.dtoBase.BaseResponse;
import com.person.entites.Contact;
import com.person.entites.Personel;
import com.person.enums.RecordStatus;
import com.person.exception.ResourceNotFoundException;
import com.person.repository.ContactRepository;
import com.person.repository.PersonelRepository;
import com.person.services.IContactServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ContactServicesImpl implements IContactServices {

    private final ContactRepository contactRepository;
    private final PersonelRepository personelRepository;

    public ContactServicesImpl(ContactRepository contactRepository, PersonelRepository personelRepository) {
        this.contactRepository = contactRepository;
        this.personelRepository = personelRepository;
    }

    @Override
    public BaseResponse save(ContactSaveDto dto) {
        Personel personel = personelRepository.findById(dto.getPersonelId())
                .orElseThrow(() -> new ResourceNotFoundException("Personel bulunamadı"));
        Contact contact = new Contact();
        contact.setPersonel(personel);
        contact.setType(dto.getType());
        contact.setContact(dto.getContact());
        contact.setStatus(RecordStatus.ACTIVE.getValue());
        contact.setCreateDate(new Date());
        return ok(HttpStatus.CREATED, "İletişim kaydedildi", toDto(contactRepository.save(contact)));
    }

    @Override
    public BaseResponse findAll() {
        List<ContactDto> dtoList = contactRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
        return ok(HttpStatus.OK, "İletişim bilgileri listelendi", dtoList);
    }

    @Override
    public BaseResponse findById(Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("İletişim bulunamadı"));
        return ok(HttpStatus.OK, "İletişim bulundu", toDto(contact));
    }

    @Override
    public BaseResponse deleteById(Long id) {
        if (!contactRepository.existsById(id)) {
            throw new ResourceNotFoundException("İletişim bulunamadı");
        }
        contactRepository.deleteById(id);
        return ok(HttpStatus.OK, "İletişim silindi", null);
    }

    @Override
    public BaseResponse update(Long id, ContactSaveDto dto) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("İletişim bulunamadı"));
        if (dto.getPersonelId() != null) {
            Personel personel = personelRepository.findById(dto.getPersonelId())
                    .orElseThrow(() -> new ResourceNotFoundException("Personel bulunamadı"));
            contact.setPersonel(personel);
        }
        if (dto.getType() != null) {
            contact.setType(dto.getType());
        }
        if (dto.getContact() != null) {
            contact.setContact(dto.getContact());
        }
        return ok(HttpStatus.OK, "İletişim güncellendi", toDto(contactRepository.save(contact)));
    }

    @Override
    public BaseResponse findByIdPersonel(Long id) {
        List<ContactDto> dtoList = contactRepository.findByPersonel_Id(id)
                .stream().map(this::toDto).collect(Collectors.toList());
        return ok(HttpStatus.OK, "İletişim bilgileri bulundu", dtoList);
    }

    private ContactDto toDto(Contact contact) {
        ContactDto dto = new ContactDto();
        dto.setId(contact.getId());
        dto.setContact(contact.getContact());
        dto.setType(contact.getType());
        dto.setStatus(contact.getStatus());
        dto.setCreateDate(contact.getCreateDate());
        if (contact.getPersonel() != null) {
            dto.setPersonelId(contact.getPersonel().getId());
        }
        return dto;
    }

    private BaseResponse ok(HttpStatus status, String message, Object data) {
        BaseResponse response = new BaseResponse();
        response.setStatus(status.value());
        response.setMessage(message);
        response.setData(data);
        return response;
    }
}
