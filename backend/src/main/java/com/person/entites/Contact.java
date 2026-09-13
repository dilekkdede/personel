package com.person.entites;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contact")
public class Contact {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "personel_id")
    private Long personelId;

    @Column(name = "contact")
    private String contact;

    @Column(name = "status")
    private Integer status;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "type")
    private String type;

}
