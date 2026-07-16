package com.dlinteriorismo.sistema_interiorismo.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CotizacionRequest {

    private Integer idProyecto;
    private Integer idUsuario;
    private BigDecimal metraje;

}