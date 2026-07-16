package com.dlinteriorismo.sistema_interiorismo.service;

import com.dlinteriorismo.sistema_interiorismo.dto.DashboardRequest;
import com.dlinteriorismo.sistema_interiorismo.model.Proyecto;
import com.dlinteriorismo.sistema_interiorismo.model.Tarea;
import com.dlinteriorismo.sistema_interiorismo.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @Mock
    private ProyectoRepository proyectoRepository;

    @Mock
    private CotizacionRepository cotizacionRepository;

    @Mock
    private FacturaRepository facturaRepository;

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private TareaRepository tareaRepository;

    @InjectMocks
    private DashboardService dashboardService;

    @Test
    void obtenerResumen() {

        when(proyectoRepository.count()).thenReturn(10L);
        when(cotizacionRepository.count()).thenReturn(8L);
        when(facturaRepository.count()).thenReturn(6L);
        when(pagoRepository.count()).thenReturn(5L);
        when(tareaRepository.count()).thenReturn(15L);

        when(proyectoRepository.countByEstado("Pendiente")).thenReturn(3L);
        when(proyectoRepository.countByEstado("En Proceso")).thenReturn(4L);
        when(proyectoRepository.countByEstado("Finalizado")).thenReturn(2L);
        when(proyectoRepository.countByEstado("Cancelado")).thenReturn(1L);

        DashboardRequest dto = dashboardService.obtenerResumen();

        assertEquals(10L, dto.getTotalProyectos());
        assertEquals(8L, dto.getTotalCotizaciones());
        assertEquals(6L, dto.getTotalFacturas());
        assertEquals(5L, dto.getTotalPagos());
        assertEquals(15L, dto.getTotalTareas());

        assertEquals(3L, dto.getProyectosPendientes());
        assertEquals(4L, dto.getProyectosEnProceso());
        assertEquals(2L, dto.getProyectosCompletados());
        assertEquals(1L, dto.getProyectosCancelados());
    }

    @Test
    void obtenerProyectosRecientes() {

        List<Proyecto> lista = List.of(new Proyecto(), new Proyecto());

        when(proyectoRepository.findAllByOrderByIdProyectoDesc(any(Pageable.class)))
                .thenReturn(lista);

        List<Proyecto> resultado = dashboardService.obtenerProyectosRecientes();

        assertEquals(2, resultado.size());

        verify(proyectoRepository)
                .findAllByOrderByIdProyectoDesc(any(Pageable.class));
    }

    @Test
    void obtenerTareasPendientes() {

        List<Tarea> lista = List.of(new Tarea(), new Tarea(), new Tarea());

        when(tareaRepository.findAllByOrderByIdTareaDesc(any(Pageable.class)))
                .thenReturn(lista);

        List<Tarea> resultado = dashboardService.obtenerTareasPendientes();

        assertEquals(3, resultado.size());

        verify(tareaRepository)
                .findAllByOrderByIdTareaDesc(any(Pageable.class));
    }
}