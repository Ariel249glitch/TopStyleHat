package com.TopStyleHat.DTO;

import java.util.List;

import lombok.Data;

@Data
public class ClienteDTO {
    private Integer id;
    private String nombre;
    private String comuna;
    private String region;
    private String direccion;
    private List<String> nombresBoletas;
}
