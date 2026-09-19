package com.person.dto;

import com.person.enums.EmploymentStatus;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class BulkStatusRequest {

    @NotEmpty(message = "Personel seçilmelidir")
    private List<Long> ids;

    @NotNull(message = "Durum seçilmelidir")
    private EmploymentStatus employmentStatus;
}
