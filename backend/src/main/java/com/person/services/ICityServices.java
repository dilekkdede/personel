package com.person.services;

import com.person.dto.CitySaveDto;
import com.person.dto.dtoBase.BaseResponse;
import com.person.entites.City;

public interface ICityServices {

    BaseResponse save(CitySaveDto dto);

    BaseResponse findAll();

    BaseResponse findById(Long id);

    BaseResponse deleteById(Long id);

    BaseResponse update(Long id, CitySaveDto dto);
}
