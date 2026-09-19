package com.person.services.impl;

import com.person.dto.AdresDto;
import com.person.dto.AdresSaveDto;
import com.person.dto.dtoBase.BaseResponse;
import com.person.entites.Adres;
import com.person.entites.Personel;
import com.person.enums.RecordStatus;
import com.person.exception.ResourceNotFoundException;
import com.person.repository.AdresRepository;
import com.person.repository.PersonelRepository;
import com.person.services.IAdresServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdresServicesImpl implements IAdresServices {

    private final AdresRepository adresRepository;
    private final PersonelRepository personelRepository;

    public AdresServicesImpl(AdresRepository adresRepository, PersonelRepository personelRepository) {
        this.adresRepository = adresRepository;
        this.personelRepository = personelRepository;
    }

    @Override
    public BaseResponse save(AdresSaveDto dto) {
        Personel personel = personelRepository.findById(dto.getPersonelId())
                .orElseThrow(() -> new ResourceNotFoundException("Personel bulunamadı"));
        Adres adres = new Adres();
        adres.setPersonel(personel);
        adres.setDescription(dto.getDescription());
        adres.setStatus(RecordStatus.ACTIVE.getValue());
        adres.setCreateDate(new Date());
        return ok(HttpStatus.CREATED, "Adres kaydedildi", toDto(adresRepository.save(adres)));
    }

    @Override
    public BaseResponse findAll() {
        List<AdresDto> dtoList = adresRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
        return ok(HttpStatus.OK, "Adresler listelendi", dtoList);
    }

    @Override
    public BaseResponse findById(Long id) {
        Adres adres = adresRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Adres bulunamadı"));
        return ok(HttpStatus.OK, "Adres bulundu", toDto(adres));
    }

    @Override
    public BaseResponse findByIdPersonel(Integer id) {
        List<AdresDto> dtoList = adresRepository.findByPersonel_Id(id.longValue())
                .stream().map(this::toDto).collect(Collectors.toList());
        return ok(HttpStatus.OK, "Arama başarılı", dtoList);
    }

    @Override
    public BaseResponse deleteById(Long id) {
        if (!adresRepository.existsById(id)) {
            throw new ResourceNotFoundException("Adres bulunamadı");
        }
        adresRepository.deleteById(id);
        return ok(HttpStatus.OK, "Adres silindi", null);
    }

    @Override
    public BaseResponse update(Long id, AdresSaveDto dto) {
        Adres adres = adresRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Adres bulunamadı"));
        if (dto.getPersonelId() != null) {
            Personel personel = personelRepository.findById(dto.getPersonelId())
                    .orElseThrow(() -> new ResourceNotFoundException("Personel bulunamadı"));
            adres.setPersonel(personel);
        }
        if (dto.getDescription() != null) {
            adres.setDescription(dto.getDescription());
        }
        return ok(HttpStatus.OK, "Adres güncellendi", toDto(adresRepository.save(adres)));
    }

    private AdresDto toDto(Adres adres) {
        AdresDto dto = new AdresDto();
        dto.setId(adres.getId());
        dto.setDescription(adres.getDescription());
        dto.setStatus(adres.getStatus());
        dto.setCreateDate(adres.getCreateDate());
        if (adres.getPersonel() != null) {
            dto.setPersonelId(adres.getPersonel().getId().intValue());
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
