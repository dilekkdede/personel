package com.person.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdresSavePersonelDto {


    @Schema(example = "Ankara/Çankaya..")
    private String description;

}
