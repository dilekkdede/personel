package com.person.controller;

import com.person.dto.BulkStatusRequest;
import com.person.dto.PersonelSaveDto;
import com.person.dto.dtoBase.BaseResponse;
import com.person.services.IPersonelServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/rest/api/personel")
public class PersonelController {

    @Autowired
    private IPersonelServices personelServices;

    @PostMapping(path = "/save")
    public BaseResponse save(@Valid @RequestBody PersonelSaveDto personel) {
        return personelServices.save(personel);
    }

    @GetMapping(path = "/list")
    public BaseResponse findAll(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(required = false) String q,
                                @RequestParam(required = false) Long cityId,
                                @RequestParam(required = false) Long unitId,
                                @RequestParam(required = false) String bolum,
                                @RequestParam(required = false) String employmentStatus) {
        return personelServices.findAll(page, size, q, cityId, unitId, bolum, employmentStatus);
    }

    @GetMapping("/dashboard")
    public BaseResponse dashboard() {
        return personelServices.dashboard();
    }

    @PutMapping("/status/{id}")
    public BaseResponse changeStatus(@PathVariable Long id, @RequestParam String employmentStatus) {
        return personelServices.changeStatus(id, employmentStatus);
    }

    @PutMapping("/bulk-status")
    public BaseResponse bulkStatus(@Valid @RequestBody BulkStatusRequest request) {
        return personelServices.bulkStatus(request);
    }

    @GetMapping(path = "/get-id/{id}")
    public BaseResponse findById(@PathVariable(name = "id") Long id) {
        return personelServices.findById(id);
    }

    @DeleteMapping(path = "/delete/{id}")
    public BaseResponse deleteById(@PathVariable(name = "id") Long id) {
        return personelServices.deleteById(id);
    }

    @PutMapping(path = "/update/{id}")
    public BaseResponse updatePersonel(@PathVariable(name = "id") Long id, @Valid @RequestBody PersonelSaveDto personel) {
        return personelServices.update(id, personel);
    }

    @GetMapping("/countByUnit")
    public BaseResponse countByUnit() {
        return personelServices.findPersonelCountByUnit();
    }

    @GetMapping("/countByCity")
    public BaseResponse countByCity() {
        return personelServices.findPersonelCountByCity();
    }
}
