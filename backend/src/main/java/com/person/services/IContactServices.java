package com.person.services;

import com.person.dto.ContactSaveDto;
import com.person.dto.dtoBase.BaseResponse;

public interface IContactServices {

    BaseResponse save(ContactSaveDto dto);

    BaseResponse findAll();

    BaseResponse findById(Long id);

    BaseResponse deleteById(Long id);

    BaseResponse update(Long id, ContactSaveDto dto);


    BaseResponse findByIdPersonel(Long id);
}
