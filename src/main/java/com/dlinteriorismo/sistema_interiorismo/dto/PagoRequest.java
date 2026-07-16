package com.dlinteriorismo.sistema_interiorismo.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PagoRequest {

    private Integer idFactura;
    private String metodoPago;
    private BigDecimal monto;
    private String referencia;

}