package com.dlinteriorismo.sistema_interiorismo.service;

import com.dlinteriorismo.sistema_interiorismo.dto.CotizacionRequest;
import com.dlinteriorismo.sistema_interiorismo.model.Cotizacion;
import com.dlinteriorismo.sistema_interiorismo.repository.CotizacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CotizacionServiceTest {

    @Mock
    private CotizacionRepository cotizacionRepository;

    @InjectMocks
    private CotizacionService cotizacionService;

    private CotizacionRequest request;

    @BeforeEach
    void setUp() {
        request = new CotizacionRequest();
        request.setIdProyecto(1);
        request.setIdUsuario(1);
        request.setMetraje(new BigDecimal("5"));
    }

    @Test
    void listar() {
        when(cotizacionRepository.findAll()).thenReturn(Collections.emptyList());

        assertNotNull(cotizacionService.listar());

        verify(cotizacionRepository).findAll();
    }

    @Test
    void guardarCorrectamente() {

        when(cotizacionRepository.save(any(Cotizacion.class)))
                .thenAnswer(i -> i.getArgument(0));

        Cotizacion resultado = cotizacionService.guardar(request);

        assertNotNull(resultado);
        assertEquals(request.getIdProyecto(), resultado.getIdProyecto());
        assertEquals("Pendiente", resultado.getEstado());

        verify(cotizacionRepository).save(any(Cotizacion.class));
    }

    @Test
    void proyectoNulo() {

        request.setIdProyecto(null);

        assertThrows(
                NullPointerException.class,
                () -> cotizacionService.guardar(request)
        );
    }

    @Test
    void metrajeNulo() {

        request.setMetraje(null);

        assertThrows(
                IllegalArgumentException.class,
                () -> cotizacionService.guardar(request)
        );
    }

    @Test
    void metrajeCero() {

        request.setMetraje(BigDecimal.ZERO);

        assertThrows(
                IllegalArgumentException.class,
                () -> cotizacionService.guardar(request)
        );
    }

    @Test
    void eliminar() {

        cotizacionService.eliminar(1);

        verify(cotizacionRepository).deleteById(1);
    }

}