package com.person.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserSaveDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(example = "Dilek")
    private String firstName;
    @Schema(example = "Dede")
    private String lastName;
    @Schema(example = "dilekdd")
    private String userName;
    @Schema(example = "12345678955")
    private Long tcNo;
    private CityIdDto city;

}
