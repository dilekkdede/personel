package com.person.services.impl;

import com.person.dto.AdresDto;
import com.person.dto.AdresSaveDto;
import com.person.dto.dtoBase.BaseResponse;
import com.person.entites.Adres;
import com.person.enums.RecordStatus;
import com.person.repository.AdresRepository;
import com.person.services.IAdresServices;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AdresServicesImpl implements IAdresServices {

    @Autowired
    private AdresRepository adresRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BaseResponse save(AdresSaveDto dto) {
        BaseResponse response = new BaseResponse();

        //Kontroller
        if (dto.getPersonelId() == null) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setMessage("Personel Id is required");
            response.setData(null);
            return response;
        }

        if (dto.getDescription() == null) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setMessage("Description is required");
            response.setData(null);
            return response;
        }

        Adres adres = new Adres();
        adres.setPersonelId(dto.getPersonelId());
        adres.setDescription(dto.getDescription());
        adres.setStatus(RecordStatus.ACTIVE.getValue());
        adres.setCreateDate(new Date());

        Adres dbAdres = adresRepository.save(adres);

        AdresDto adresDto = modelMapper.map(dbAdres, AdresDto.class);
        response.setStatus(HttpStatus.CREATED.value());
        response.setData(adresDto);
        response.setMessage("Adress saved sucessfully");
        return response;

    }

    @Override
    public BaseResponse findAll() {
        BaseResponse response = new BaseResponse();

        List<Adres> adresList = adresRepository.findAll();

        List<AdresDto> dtoList = modelMapper.map(adresList, new TypeToken<List<AdresDto>>() {
        }.getType());

        response.setStatus(HttpStatus.OK.value());
        response.setData(dtoList);
        response.setMessage("All adresses found");
        return response;


    }

    @Override
    public BaseResponse findById(Long id) {

        BaseResponse response = new BaseResponse();
        Optional<Adres> adresOptional = adresRepository.findById(id);

        if (adresOptional.isEmpty()) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("Adres not found");
            response.setData(null);
            response.setData(null);
        }

        if (adresOptional.isPresent()) {
            AdresDto adresDto = modelMapper.map(adresOptional.get(), AdresDto.class);
            response.setStatus(HttpStatus.OK.value());
            response.setData(adresDto);
            response.setMessage("Adress found sucessfully");
        }
        return response;

    }

    @Override
    public BaseResponse findByIdPersonel(Integer id) {
        BaseResponse response = new BaseResponse();

        List<Adres> findAdres = adresRepository.findAdresByPersonelId(id);

        response.setData(findAdres);
        response.setMessage("Arama başarılı");
        response.setStatus(HttpStatus.OK.value());
        log.info("findByIdPersonel id: " + id);
        return response;
    }


    @Override
    public BaseResponse deleteById(Long id) {


        BaseResponse baseResponse = new BaseResponse();
        Optional<Adres> adresOptional = adresRepository.findById(id);
        if (adresOptional.isEmpty()) {
            baseResponse.setStatus(HttpStatus.NOT_FOUND.value());
            baseResponse.setMessage("Adres  id not found");
            baseResponse.setData(null);
            return baseResponse;
        } else {
            adresRepository.deleteById(id);

            baseResponse.setStatus(HttpStatus.OK.value());
            baseResponse.setMessage("Adres başarılı bir şekilde silindi");
            return baseResponse;
        }


    }

    @Override
    public BaseResponse update(Long id, AdresSaveDto dto) {

        BaseResponse response = new BaseResponse();
        Optional<Adres> adresOptional = adresRepository.findById(id);

        if (adresOptional.isEmpty()) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setMessage("Adres not found");
            response.setData(null);
            return response;
        }

        Adres adres = adresOptional.get();
        if (dto.getPersonelId() != null) {
            adres.setPersonelId(dto.getPersonelId());
        }

        if (dto.getDescription() != null) {
            adres.setDescription(dto.getDescription());

        }

        Adres dbAdres = adresRepository.save(adres);
        AdresDto adresDto = modelMapper.map(dbAdres, AdresDto.class);
        response.setStatus(HttpStatus.OK.value());
        response.setData(adresDto);
        response.setMessage("Adress update sucessfully");


        return response;
    }


}
