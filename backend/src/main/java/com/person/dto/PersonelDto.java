package com.person.dto;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
public class PersonelDto implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String description;
    private Integer status;
    private Date createDate;
    private String bolum;
    private Date birthDate;
    private CityInfoDto city;
    private UnitInfoDto unit;
    private AdresInfoDto adres;


}
