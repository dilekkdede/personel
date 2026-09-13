package com.person.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonelSaveDto {


    @Schema(example = "Ali")
    private String firstName;

    @Schema(example = "Kaya")
    private String lastName;

    @Schema(example = "Ali_ky")
    private String userName;

    @Schema(example = "Öğretim Görevlisi")
    private String description;

    @Schema(example = "Bilgisayar Müh.")
    private String bolum;

    @Schema(example = "1994-01-19")
    private Date birthDate;


    private CityIdDto city;

    private UnitIdDto unit;

    private AdresSavePersonelDto adres;


}
