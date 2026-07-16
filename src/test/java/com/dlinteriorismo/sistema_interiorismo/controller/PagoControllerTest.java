package com.dlinteriorismo.sistema_interiorismo.controller;

import com.dlinteriorismo.sistema_interiorismo.dto.PagoRequest;
import com.dlinteriorismo.sistema_interiorismo.model.Pago;
import com.dlinteriorismo.sistema_interiorismo.service.PagoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PagoControllerTest {

    private PagoService pagoService;
    private PagoController pagoController;

    @BeforeEach
    void setUp() {
        pagoService = Mockito.mock(PagoService.class);
        pagoController = new PagoController(pagoService);
    }

    @Test
    void listarDebeRetornarLista() {

        Pago pago = new Pago();

        when(pagoService.listar())
                .thenReturn(List.of(pago));

        List<Pago> resultado = pagoController.listar();

        assertEquals(1, resultado.size());

        verify(pagoService).listar();
    }

    @Test
    void guardarDebeLlamarAlServicio() {

        PagoRequest request = new PagoRequest();
        request.setIdFactura(1);
        request.setMetodoPago("Transferencia");
        request.setMonto(new BigDecimal("250.50"));
        request.setReferencia("PAGO001");

        Pago pago = new Pago();

        when(pagoService.guardar(request))
                .thenReturn(pago);

        Pago resultado = pagoController.guardar(request);

        assertNotNull(resultado);

        verify(pagoService).guardar(request);
    }

    @Test
    void eliminarDebeLlamarAlServicio() {

        doNothing().when(pagoService).eliminar(1);

        pagoController.eliminar(1);

        verify(pagoService).eliminar(1);
    }
}