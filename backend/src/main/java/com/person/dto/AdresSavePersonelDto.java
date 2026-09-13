package com.person.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter

public class AdresSavePersonelDto {


    @Schema(example = "Ankara/Çankaya..")
    private String description;

}
