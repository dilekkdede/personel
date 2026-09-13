package com.person.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnitIdDto {

    @Schema(example = "2")
    private Long id;

}
