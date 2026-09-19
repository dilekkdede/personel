package com.person.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AdresSaveDto {

    @NotNull(message = "Personel id boş olamaz")
    @Schema(example = "2")
    private Long personelId;

    @NotBlank(message = "Adres boş olamaz")
    @Schema(example = "Ankara/Çankaya..")
    private String description;
}
