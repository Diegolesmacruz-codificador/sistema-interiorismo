package com.dlinteriorismo.sistema_interiorismo.service;

import com.dlinteriorismo.sistema_interiorismo.dto.FacturaRequest;
import com.dlinteriorismo.sistema_interiorismo.model.Cotizacion;
import com.dlinteriorismo.sistema_interiorismo.model.Factura;
import com.dlinteriorismo.sistema_interiorismo.repository.CotizacionRepository;
import com.dlinteriorismo.sistema_interiorismo.repository.FacturaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FacturaServiceTest {

    @Mock
    private FacturaRepository facturaRepository;

    @Mock
    private CotizacionRepository cotizacionRepository;

    @InjectMocks
    private FacturaService facturaService;

    private FacturaRequest request;

    @BeforeEach
    void setUp() {

        request = new FacturaRequest();

        request.setIdCotizacion(1);
        request.setNumeroFactura("F-001");
        request.setEstado("Pendiente");
    }

    @Test
    void listarDebeRetornarLista() {

        when(facturaRepository.findAll())
                .thenReturn(List.of(new Factura()));

        List<Factura> lista = facturaService.listar();

        assertEquals(1, lista.size());

        verify(facturaRepository).findAll();
    }

    @Test
    void guardarDebeRegistrarFactura() {

        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setTotal(new BigDecimal("2500.00"));

        when(cotizacionRepository.findById(1))
                .thenReturn(Optional.of(cotizacion));

        when(facturaRepository.existsByIdCotizacion(1))
                .thenReturn(false);

        when(facturaRepository.save(any(Factura.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        Factura factura = facturaService.guardar(request);

        assertNotNull(factura);

        assertEquals(new BigDecimal("2500.00"), factura.getTotal());

        verify(facturaRepository).save(any(Factura.class));
    }

    @Test
    void guardarDebeFallarSiCotizacionEsNula() {

        request.setIdCotizacion(null);

        assertThrows(
                NullPointerException.class,
                () -> facturaService.guardar(request)
        );
    }

    @Test
    void guardarDebeFallarSiCotizacionNoExiste() {

        when(cotizacionRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> facturaService.guardar(request)
        );
    }

    @Test
    void guardarDebeGenerarNumeroAutomatico() {

        request.setNumeroFactura("");

        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setTotal(new BigDecimal("1000"));

        when(cotizacionRepository.findById(1))
                .thenReturn(Optional.of(cotizacion));

        when(facturaRepository.existsByIdCotizacion(1))
                .thenReturn(false);

        when(facturaRepository.save(any(Factura.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        Factura factura = facturaService.guardar(request);

        assertNotNull(factura.getNumeroFactura());

        assertTrue(factura.getNumeroFactura().startsWith("F-"));
    }

    @Test
    void guardarDebeAsignarEstadoPendiente() {

        request.setEstado("");

        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setTotal(new BigDecimal("1000"));

        when(cotizacionRepository.findById(1))
                .thenReturn(Optional.of(cotizacion));

        when(facturaRepository.existsByIdCotizacion(1))
                .thenReturn(false);

        when(facturaRepository.save(any(Factura.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        Factura factura = facturaService.guardar(request);

        assertEquals("Pendiente", factura.getEstado());
    }

    @Test
    void guardarDebeFallarSiFacturaYaExiste() {

        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setTotal(new BigDecimal("1000"));

        when(cotizacionRepository.findById(1))
                .thenReturn(Optional.of(cotizacion));

        when(facturaRepository.existsByIdCotizacion(1))
                .thenReturn(true);

        assertThrows(
                RuntimeException.class,
                () -> facturaService.guardar(request)
        );
    }

    @Test
    void eliminarDebeInvocarRepositorio() {

        facturaService.eliminar(1);

        verify(facturaRepository).deleteById(1);
    }
}