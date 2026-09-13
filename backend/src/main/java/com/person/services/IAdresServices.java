package com.person.services;

import com.person.dto.AdresSaveDto;
import com.person.dto.dtoBase.BaseResponse;

public interface IAdresServices {

    BaseResponse save(AdresSaveDto dto);

    BaseResponse findAll();

    BaseResponse findById(Long id);

    BaseResponse deleteById(Long id);

    BaseResponse update(Long id, AdresSaveDto dto);

    BaseResponse findByIdPersonel(Integer id);
}
