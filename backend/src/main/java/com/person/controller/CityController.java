package com.person.controller;

import com.person.dto.CitySaveDto;
import com.person.dto.dtoBase.BaseResponse;
import com.person.services.ICityServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/api/city")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class CityController {


    @Autowired
    private ICityServices cityService;


    @PostMapping(path = "/save")
    public BaseResponse save(@RequestBody CitySaveDto dto) {
        return cityService.save(dto);
    }

    @GetMapping(path = "/get-all")
    public BaseResponse findAll() {

        return cityService.findAll();
    }

    @GetMapping(path = "/find-id/{id}")
    public BaseResponse findById(@PathVariable(name = "id") Long id) {
        return cityService.findById(id);

    }

    @DeleteMapping(path = "/delete/{id}")
    BaseResponse deleteById(@PathVariable(name = "id") Long id) {
        return cityService.deleteById(id);
    }

    @PutMapping(path = "/update/{id}")
    public BaseResponse update(@PathVariable(name = "id") Long id, @RequestBody CitySaveDto dto) {
        return cityService.update(id, dto);
    }


}
