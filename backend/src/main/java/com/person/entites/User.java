package com.person.entites;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "USERS")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "CRE_BY")
    private String createBy;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "CRE_DATE")
    private Date createDate;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "CITY_ID", referencedColumnName = "ID")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private City city;

    @Column(name = "TC_NO", length = 11)
    private Long tcNo;

}
