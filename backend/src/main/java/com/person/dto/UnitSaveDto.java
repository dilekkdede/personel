package com.person.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Data

public class UnitSaveDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(example = "Van Su İşleri")
    private String name;

    @Schema(example = "VASKİ")
    private String code;

}
