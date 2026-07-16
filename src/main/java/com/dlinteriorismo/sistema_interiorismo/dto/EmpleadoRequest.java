package com.dlinteriorismo.sistema_interiorismo.dto;

import lombok.Data;

@Data
public class EmpleadoRequest {

    private String nombre;
    private String dni;
    private String telefono;
    private String correo;
    private String cargo;
}