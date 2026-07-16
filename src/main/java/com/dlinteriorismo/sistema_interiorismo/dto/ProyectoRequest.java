package com.dlinteriorismo.sistema_interiorismo.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProyectoRequest {

    private Integer idCliente;
    private Integer idTipoProyecto;

    private String nombreProyecto;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado;
    private String descripcion;
}