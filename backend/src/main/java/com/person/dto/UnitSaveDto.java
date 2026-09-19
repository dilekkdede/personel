package com.person.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class UnitSaveDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Birim adı boş olamaz")
    @Schema(example = "Van Su İşleri")
    private String name;

    @NotBlank(message = "Birim kodu boş olamaz")
    @Schema(example = "VASKİ")
    private String code;

}
