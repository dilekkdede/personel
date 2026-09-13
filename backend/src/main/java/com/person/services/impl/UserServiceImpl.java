package com.person.services.impl;

import com.person.dto.UserDto;
import com.person.dto.UserSaveDto;
import com.person.dto.dtoBase.BaseResponse;
import com.person.entites.City;
import com.person.entites.User;
import com.person.enums.RecordStatus;
import com.person.repository.CityRepository;
import com.person.repository.UserRepository;
import com.person.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BaseResponse save(UserSaveDto dto) {
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUserName(dto.getUserName());
        user.setTcNo(dto.getTcNo());

        Optional<City> city = cityRepository.findById(dto.getCity().getId());
        if (city.isPresent()) {
            user.setCity(city.get());
        }

        user.setCreateBy("Dilek Dede");
        user.setCreateDate(new Date());
        user.setStatus(RecordStatus.ACTIVE.getValue());
        User newUser = userRepository.save(user);

        UserDto userDto = modelMapper.map(newUser, UserDto.class);

        BaseResponse response = new BaseResponse();
        response.setData(userDto);
        response.setStatus(201);

        log.info("User saved with id " + newUser.getId());
        return response;
    }

    @Override
    public BaseResponse update(Long id, UserSaveDto dto) {
        BaseResponse response = new BaseResponse();

        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            user.get().setFirstName(dto.getFirstName());
            user.get().setLastName(dto.getLastName());
            user.get().setUserName(dto.getUserName());

            Optional<City> city = cityRepository.findById(dto.getCity().getId());
            if (city.isPresent()) {
                user.get().setCity(city.get());
            }

            User newUser = userRepository.save(user.get());
            UserDto userDto = modelMapper.map(newUser, UserDto.class);

            response.setData(userDto);
            response.setStatus(200);
        }

        log.info("User updated with id " + id);
        return response;
    }

    @Override
    public BaseResponse findAll() {
        BaseResponse response = new BaseResponse();

        List<User> listUser = userRepository.findAll();
        List<UserDto> dtoList = modelMapper.map(listUser, new TypeToken<List<UserDto>>() {
        }.getType());

        response.setData(dtoList);
        response.setStatus(200);
        log.info("UserServiceImpl.findAll");
        return response;
    }
}
