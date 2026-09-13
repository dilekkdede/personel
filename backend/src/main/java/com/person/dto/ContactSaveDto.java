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

public class ContactSaveDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(example = "3")
    private Long personelId;

    @Schema(example = "0542../example@gmail...")
    private String contact;

    @Schema(example = "TELEFON/EMAİL")
    private String type;

}
