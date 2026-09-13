package com.person.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private Integer status;
    private Date createDate;
    private CityInfoDto city;

}
