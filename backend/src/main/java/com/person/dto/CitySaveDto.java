package com.person.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class CitySaveDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Şehir adı boş olamaz")
    @Schema(example = "Ankara")
    private String name;

    @NotBlank(message = "Şehir kodu boş olamaz")
    @Schema(example = "06")
    private String code;


}
