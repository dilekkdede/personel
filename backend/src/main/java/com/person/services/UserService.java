package com.person.services;

import com.person.dto.UserSaveDto;
import com.person.dto.dtoBase.BaseResponse;

public interface UserService {

    BaseResponse save(UserSaveDto dto);

    BaseResponse update(Long id, UserSaveDto dto);

    BaseResponse findAll();

}
