package com.person.entites;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Data
@Entity
@Table(name = "adres")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Adres implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "personel_id")
    private Integer personelId;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private Integer status;

    @Column(name = "create_date")
    private Date createDate;


}
