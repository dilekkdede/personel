package com.person.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdresIdDto {

    @Schema(example = "2")
   private Long id;
    @Schema(example = "İstanbul Bebek")

    private String description;

}
