package com.person.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter

public class AdresSaveDto {


    @Schema(example = "2")
    private Integer personelId;

    @Schema(example = "Ankara/Çankaya..")
    private String description;

}
