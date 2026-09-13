package com.person.controller;

import com.person.dto.ContactSaveDto;
import com.person.dto.dtoBase.BaseResponse;
import com.person.services.IContactServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping("/rest/api/contact")
public class ContactController {

    @Autowired
    private IContactServices contactServices;

    @PostMapping(path = "/save")
    public BaseResponse save(@RequestBody ContactSaveDto dto) {
        return contactServices.save(dto);
    }

    @GetMapping(path = "/get-all")
    public BaseResponse findAll() {
        return contactServices.findAll();
    }

    @GetMapping(path = "/find-id/{id}")
    public BaseResponse findById(@PathVariable(name = "id") Long id) {
        return contactServices.findById(id);
    }

    @DeleteMapping(path = "/delete/{id}")
    BaseResponse deleteById(@PathVariable(name = "id") Long id) {
        return contactServices.deleteById(id);
    }

    @PutMapping(path = "/update/{id}")
    public BaseResponse update(@PathVariable(name = "id") Long id, @RequestBody ContactSaveDto dto) {
        return contactServices.update(id, dto
        );

    }

    @GetMapping(path = "/get-personel-ıd-contact/{id}")
    public BaseResponse findByIdPersonelContact(@PathVariable(name = "id") Long id) {
        return contactServices.findByIdPersonel(id);
    }


}
