package com.person.services.impl;

import com.person.dto.UnitDto;
import com.person.dto.UnitSaveDto;
import com.person.dto.dtoBase.BaseResponse;
import com.person.entites.Unit;
import com.person.enums.RecordStatus;
import com.person.repository.UnitRepository;
import com.person.services.IUnitServices;
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
public class UnitServicesImpl implements IUnitServices {

    @Autowired
    private UnitRepository unitRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public BaseResponse save(UnitSaveDto dto) {
        BaseResponse response = new BaseResponse();

        Unit unit = new Unit();
        unit.setName(dto.getName());
        unit.setCode(dto.getCode());
        unit.setStatus(RecordStatus.ACTIVE.getValue());
        unit.setCreateDate(new Date());

        Unit dbUnit = unitRepository.save(unit);

        UnitDto dtoUnit = modelMapper.map(dbUnit, UnitDto.class);

        response.setStatus(HttpStatus.CREATED.value());
        response.setData(dtoUnit);
        response.setMessage("Unit saved Successfully");
        if (dbUnit.getId() > 7) {
            log.info("Gelen ID'ler atrık 7 den büyük!!");
        }
        log.info("Unit saved Successfully...");
        return response;
    }

    @Override
    public BaseResponse findAll() {

        BaseResponse response = new BaseResponse();

        List<Unit> units = unitRepository.findAll();

        List<UnitDto> dtoUnits = modelMapper.map(units, new TypeToken<List<UnitDto>>() {
        }.getType());

        response.setStatus(HttpStatus.OK.value());
        response.setData(dtoUnits);
        response.setMessage("All units found");
        log.info("Found all units size: {}", dtoUnits.size());
        return response;

    }

    @Override
    public BaseResponse findById(Long id) {
        BaseResponse response = new BaseResponse();
        Optional<Unit> findUnit = unitRepository.findById(id);
        if (findUnit.isPresent()) {
            UnitDto dtoUnit = modelMapper.map(findUnit.get(), UnitDto.class);
            response.setStatus(HttpStatus.OK.value());
            response.setData(dtoUnit);
            response.setMessage("Unit found Successfully");
        }
        log.info("unit findById: {}", id);
        return response;
    }

    @Override
    public BaseResponse deleteById(Long id) {

        BaseResponse baseResponse = new BaseResponse();
        log.info("Unit silindi");
        unitRepository.deleteById(id);

        baseResponse.setStatus(HttpStatus.OK.value());
        baseResponse.setMessage("Unit başarılı bir şekilde silindi");
        log.info("Unit silindi: {}", id);
        return baseResponse;
    }

    @Override
    public BaseResponse update(Long id, UnitSaveDto dto) {

        BaseResponse response = new BaseResponse();

        Optional<Unit> findUnit = unitRepository.findById(id);
        if (findUnit.isPresent()) {
            findUnit.get().setName(dto.getName());
            findUnit.get().setCode(dto.getCode());
        }

        Unit dbUnit = unitRepository.save(findUnit.get());
        UnitDto dtoUnit = modelMapper.map(dbUnit, UnitDto.class);
        response.setStatus(HttpStatus.OK.value());
        response.setData(dtoUnit);
        response.setMessage("Unit update Successfully");
        log.info("Unit update");
        return response;
    }

    @Override
    public BaseResponse count() {
        BaseResponse response = new BaseResponse();
        Integer count = unitRepository.unitListesiCount();

        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Count of units found");
        response.setData(count);

        return response;
    }


}
