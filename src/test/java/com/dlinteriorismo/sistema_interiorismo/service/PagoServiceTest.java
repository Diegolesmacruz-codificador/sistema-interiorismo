package com.dlinteriorismo.sistema_interiorismo.service;

import com.dlinteriorismo.sistema_interiorismo.dto.PagoRequest;
import com.dlinteriorismo.sistema_interiorismo.model.Pago;
import com.dlinteriorismo.sistema_interiorismo.repository.FacturaRepository;
import com.dlinteriorismo.sistema_interiorismo.repository.PagoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private FacturaRepository facturaRepository;

    @InjectMocks
    private PagoService pagoService;

    private PagoRequest request;

    @BeforeEach
    void setUp() {
        request = new PagoRequest();
        request.setIdFactura(1);
        request.setMetodoPago("Efectivo");
        request.setMonto(new BigDecimal("150.00"));
        request.setReferencia("REF001");
    }

    @Test
    void listarDebeRetornarListaDePagos() {

        when(pagoRepository.findAll()).thenReturn(List.of(new Pago()));

        List<Pago> lista = pagoService.listar();

        assertEquals(1, lista.size());

        verify(pagoRepository).findAll();
    }

    @Test
    void guardarDebeRegistrarPagoCorrectamente() {

        when(facturaRepository.existsById(1)).thenReturn(true);

        when(pagoRepository.save(any(Pago.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Pago pago = pagoService.guardar(request);

        assertNotNull(pago);

        assertEquals(1, pago.getIdFactura());

        assertEquals("Efectivo", pago.getMetodoPago());

        assertEquals(new BigDecimal("150.00"), pago.getMonto());

        assertNotNull(pago.getFechaPago());

        verify(pagoRepository).save(any(Pago.class));
    }

    @Test
    void guardarDebeFallarSiFacturaEsNula() {

        request.setIdFactura(null);

        Exception ex = assertThrows(
                NullPointerException.class,
                () -> pagoService.guardar(request)
        );

        assertTrue(ex.getMessage().contains("La factura es obligatoria"));
    }

    @Test
    void guardarDebeFallarSiFacturaNoExiste() {

        when(facturaRepository.existsById(1)).thenReturn(false);

        Exception ex = assertThrows(
                IllegalArgumentException.class,
                () -> pagoService.guardar(request)
        );

        assertTrue(ex.getMessage().contains("La factura no existe"));
    }

    @Test
    void guardarDebeFallarSiMontoEsNulo() {

        when(facturaRepository.existsById(1)).thenReturn(true);

        request.setMonto(null);

        Exception ex = assertThrows(
                IllegalArgumentException.class,
                () -> pagoService.guardar(request)
        );

        assertTrue(ex.getMessage().contains("El monto es obligatorio"));
    }

    @Test
    void guardarDebeFallarSiMontoEsCero() {

        when(facturaRepository.existsById(1)).thenReturn(true);

        request.setMonto(BigDecimal.ZERO);

        Exception ex = assertThrows(
                IllegalArgumentException.class,
                () -> pagoService.guardar(request)
        );

        assertTrue(ex.getMessage().contains("El monto debe ser mayor a 0"));
    }

    @Test
    void eliminarDebeInvocarRepositorio() {

        pagoService.eliminar(1);

        verify(pagoRepository).deleteById(1);
    }
}