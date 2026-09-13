package com.person.services;

import com.person.dto.UnitSaveDto;
import com.person.dto.dtoBase.BaseResponse;

public interface IUnitServices {

    BaseResponse save(UnitSaveDto dto);

  BaseResponse findAll();

    BaseResponse findById(Long id);

    BaseResponse deleteById(Long id);

    BaseResponse update(Long id, UnitSaveDto dto);

    BaseResponse count();



}
