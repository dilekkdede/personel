package com.person.dto;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
public class ContactDto implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long id;
    private Long personelId;
    private String contact;
    private Integer status;
    private Date createDate;
    private String type;


}
