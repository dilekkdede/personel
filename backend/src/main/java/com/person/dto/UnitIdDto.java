package com.person.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UnitIdDto {

    @NotNull(message = "Birim id boş olamaz")
    @Schema(example = "2")
    private Long id;

}
