package com.person.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UnitDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String code;
    private Integer status;
    private Date createDate;


}
