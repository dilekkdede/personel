package com.person.controller;


import com.person.dto.AdresSaveDto;
import com.person.dto.dtoBase.BaseResponse;
import com.person.services.IAdresServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;

@RestController
@RequestMapping("/rest/api/adres")
@CrossOrigin(allowedHeaders = "*", origins = "*")

public class AdresController {

    @Autowired
    private IAdresServices adresService;

    @PostMapping(path = "/save")
    public BaseResponse save(@RequestBody AdresSaveDto dto) {
        return adresService.save(dto);
    }

    @GetMapping(path = "get-all")
    public BaseResponse findAll() {
        return adresService.findAll();
    }

    @GetMapping(path = "/get-id/{id}")
    public BaseResponse findById(@PathVariable(name = "id") Long id) {
        return adresService.findById(id);
    }

    @DeleteMapping(path = "/delete/{id}")
    public BaseResponse deleteById(@PathVariable(name = "id") Long id) {

        return adresService.deleteById(id);
    }

    @PutMapping(path = "update/{id}")
    public BaseResponse updateAdres(@PathVariable(name = "id") Long id, @RequestBody AdresSaveDto dto) {
        BaseResponse baseResponse = adresService.update(id, dto);
        return baseResponse;

    }

    @GetMapping(path = "/get-personel-id/{id}")
    public BaseResponse findByIdPersonel(@PathVariable(name = "id") Integer id) {
        return adresService.findByIdPersonel(id);
    }

}
