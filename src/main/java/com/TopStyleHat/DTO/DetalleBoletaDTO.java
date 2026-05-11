package com.TopStyleHat.DTO;

import lombok.Data;

@Data
public class DetalleBoletaDTO {
    private Integer id;
    private String nombreGorro;
    private Integer cantidad;
    private Integer precioUnitario;
    private Integer subtotal;
}
