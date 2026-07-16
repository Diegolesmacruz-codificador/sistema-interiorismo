package com.dlinteriorismo.sistema_interiorismo.controller;

import com.dlinteriorismo.sistema_interiorismo.dto.CotizacionRequest;
import com.dlinteriorismo.sistema_interiorismo.model.Cotizacion;
import com.dlinteriorismo.sistema_interiorismo.service.CotizacionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CotizacionControllerTest {

    private CotizacionService cotizacionService;
    private CotizacionController cotizacionController;

    @BeforeEach
    void setUp() {
        cotizacionService = Mockito.mock(CotizacionService.class);
        cotizacionController = new CotizacionController(cotizacionService);
    }

    @Test
    void listarDebeRetornarLista() {

        Cotizacion cotizacion = new Cotizacion();

        when(cotizacionService.listar())
                .thenReturn(List.of(cotizacion));

        List<Cotizacion> resultado = cotizacionController.listar();

        assertEquals(1, resultado.size());

        verify(cotizacionService).listar();
    }

    @Test
    void guardarDebeLlamarAlServicio() {

        CotizacionRequest request = new CotizacionRequest();
        request.setIdProyecto(1);
        request.setIdUsuario(1);
        request.setMetraje(new BigDecimal("5.50"));

        Cotizacion cotizacion = new Cotizacion();

        when(cotizacionService.guardar(request))
                .thenReturn(cotizacion);

        Cotizacion resultado = cotizacionController.guardar(request);

        assertNotNull(resultado);

        verify(cotizacionService).guardar(request);
    }

    @Test
    void eliminarDebeLlamarAlServicio() {

        doNothing().when(cotizacionService).eliminar(1);

        cotizacionController.eliminar(1);

        verify(cotizacionService).eliminar(1);
    }
}