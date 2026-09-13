package com.person.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdresdescriptionDto {


    @Schema(example = "İstanbul Bebek")
    private String description;

}
