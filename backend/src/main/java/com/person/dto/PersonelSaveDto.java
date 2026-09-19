package com.person.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.person.enums.EmploymentStatus;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonelSaveDto {


    @NotBlank(message = "İsim boş olamaz")
    @Schema(example = "Ali")
    private String firstName;

    @NotBlank(message = "Soyisim boş olamaz")
    @Schema(example = "Kaya")
    private String lastName;

    @Schema(example = "Ali_ky")
    private String userName;

    @Schema(example = "Öğretim Görevlisi")
    private String description;

    @NotBlank(message = "Bölüm boş olamaz")
    @Schema(example = "Bilgisayar Müh.")
    private String bolum;

    @Schema(example = "1994-01-19")
    private Date birthDate;


    @NotNull(message = "Şehir seçilmelidir")
    @Valid
    private CityIdDto city;

    @NotNull(message = "Birim seçilmelidir")
    @Valid
    private UnitIdDto unit;

    private AdresSavePersonelDto adres;

    private EmploymentStatus employmentStatus;


}
