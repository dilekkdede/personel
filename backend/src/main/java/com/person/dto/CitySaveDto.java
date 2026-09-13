package com.person.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
public class CitySaveDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(example = "Ankara")
    private String name;

    @Schema(example = "06")
    private String code;


}
