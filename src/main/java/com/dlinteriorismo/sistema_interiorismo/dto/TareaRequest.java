package com.dlinteriorismo.sistema_interiorismo.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TareaRequest {

    private Integer idProyecto;
    private Integer idEmpleado;
    private String descripcion;
    private LocalDate fechaLimite;
    private String estado;
}