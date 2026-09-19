package com.person.controller;

import com.person.dto.AdresSaveDto;
import com.person.dto.dtoBase.BaseResponse;
import com.person.services.IAdresServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/rest/api/adres")
public class AdresController {

    @Autowired
    private IAdresServices adresService;

    @PostMapping(path = "/save")
    public BaseResponse save(@Valid @RequestBody AdresSaveDto dto) {
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
    public BaseResponse updateAdres(@PathVariable(name = "id") Long id, @Valid @RequestBody AdresSaveDto dto) {
        return adresService.update(id, dto);
    }

    @GetMapping(path = "/get-personel-id/{id}")
    public BaseResponse findByIdPersonel(@PathVariable(name = "id") Integer id) {
        return adresService.findByIdPersonel(id);
    }
}
