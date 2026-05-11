package com.TopStyleHat.DTO;

import java.time.LocalDate;

import lombok.Data;

@Data
public class BoletaDTO {
    private Integer id;
    private String metodoPago;
    private String metodoEnvio;
    private LocalDate fecha;
    private String nombreCliente;
}
