package com.person.dto.dtoBase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse implements Serializable {

    //Buranın içerisinde tüm veriyi kapsayan tanımlamalar olur.

    private Object data; //Her türü kapsayan veriyi tanımladık
    private Integer status;
    private String message;

}
