package com.person.controller;


import com.person.dto.UnitSaveDto;
import com.person.dto.dtoBase.BaseResponse;
import com.person.services.IUnitServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/api/unit")
@CrossOrigin(allowedHeaders = "*", origins = "*")

public class UnitController {

    @Autowired
    private IUnitServices unitServices;


    @PostMapping(path = "/save")
    public BaseResponse save(@RequestBody UnitSaveDto dto) {
        return unitServices.save(dto);
    }

    @GetMapping(path = "/get-all")
    public BaseResponse findAll() {
        return unitServices.findAll();
    }

    @GetMapping(path = "/find-id/{id}")
    public BaseResponse findById(@PathVariable(name = "id") Long id) {
        return unitServices.findById(id);

    }

    @DeleteMapping(path = "/delete/{id}")
    BaseResponse deleteById(@PathVariable(name = "id") Long id) {

        return unitServices.deleteById(id);
    }

    @PutMapping(path = "/update/{id}")
    public BaseResponse update(@PathVariable(name = "id") Long id, @RequestBody UnitSaveDto dto) {
        return unitServices.update(id, dto);
    }

    @GetMapping(path = "/count")
    public BaseResponse count() {
        return unitServices.count();
    }





}
