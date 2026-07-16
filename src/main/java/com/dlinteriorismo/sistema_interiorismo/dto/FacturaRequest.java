package com.dlinteriorismo.sistema_interiorismo.dto;

import lombok.Data;

@Data
public class FacturaRequest {

    private Integer idCotizacion;
    private String numeroFactura;
    private String estado;

}