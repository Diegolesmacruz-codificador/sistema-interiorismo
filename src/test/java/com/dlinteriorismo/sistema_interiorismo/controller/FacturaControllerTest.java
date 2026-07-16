package com.dlinteriorismo.sistema_interiorismo.controller;

import com.dlinteriorismo.sistema_interiorismo.dto.FacturaRequest;
import com.dlinteriorismo.sistema_interiorismo.model.Factura;
import com.dlinteriorismo.sistema_interiorismo.service.FacturaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FacturaControllerTest {

    private FacturaService facturaService;
    private FacturaController facturaController;

    @BeforeEach
    void setUp() {
        facturaService = Mockito.mock(FacturaService.class);
        facturaController = new FacturaController(facturaService);
    }

    @Test
    void listarDebeRetornarLista() {

        Factura factura = new Factura();

        when(facturaService.listar())
                .thenReturn(List.of(factura));

        List<Factura> resultado = facturaController.listar();

        assertEquals(1, resultado.size());

        verify(facturaService).listar();
    }

    @Test
    void guardarDebeLlamarAlServicio() {

        FacturaRequest request = new FacturaRequest();
        request.setIdCotizacion(1);
        request.setNumeroFactura("F-001");
        request.setEstado("Pendiente");

        Factura factura = new Factura();

        when(facturaService.guardar(request))
                .thenReturn(factura);

        Factura resultado = facturaController.guardar(request);

        assertNotNull(resultado);

        verify(facturaService).guardar(request);
    }

    @Test
    void eliminarDebeLlamarAlServicio() {

        doNothing().when(facturaService).eliminar(1);

        facturaController.eliminar(1);

        verify(facturaService).eliminar(1);
    }
}