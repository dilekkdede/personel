package com.person.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ContactSaveDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "Personel id boş olamaz")
    @Schema(example = "3")
    private Long personelId;

    @NotBlank(message = "İletişim bilgisi boş olamaz")
    @Schema(example = "0542../example@gmail...")
    private String contact;

    @NotBlank(message = "Tip boş olamaz")
    @Schema(example = "TELEFON/EMAIL")
    private String type;
}
