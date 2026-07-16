package com.dlinteriorismo.sistema_interiorismo.controller;

import com.dlinteriorismo.sistema_interiorismo.dto.DashboardRequest;
import com.dlinteriorismo.sistema_interiorismo.model.Proyecto;
import com.dlinteriorismo.sistema_interiorismo.model.Tarea;
import com.dlinteriorismo.sistema_interiorismo.service.DashboardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DashboardControllerTest {

    private DashboardService dashboardService;
    private DashboardController dashboardController;

    @BeforeEach
    void setUp() {
        dashboardService = Mockito.mock(DashboardService.class);
        dashboardController = new DashboardController(dashboardService);
    }

    @Test
    void obtenerResumenDebeRetornarDashboard() {

        DashboardRequest request = new DashboardRequest();
        request.setTotalProyectos(10L);

        when(dashboardService.obtenerResumen())
                .thenReturn(request);

        DashboardRequest resultado = dashboardController.obtenerResumen();

        assertNotNull(resultado);
        assertEquals(10L, resultado.getTotalProyectos());

        verify(dashboardService).obtenerResumen();
    }

    @Test
    void obtenerProyectosRecientesDebeRetornarLista() {

        Proyecto proyecto = new Proyecto();

        when(dashboardService.obtenerProyectosRecientes())
                .thenReturn(List.of(proyecto));

        List<Proyecto> resultado =
                dashboardController.obtenerProyectosRecientes();

        assertEquals(1, resultado.size());

        verify(dashboardService)
                .obtenerProyectosRecientes();
    }

    @Test
    void obtenerTareasPendientesDebeRetornarLista() {

        Tarea tarea = new Tarea();

        when(dashboardService.obtenerTareasPendientes())
                .thenReturn(List.of(tarea));

        List<Tarea> resultado =
                dashboardController.obtenerTareasPendientes();

        assertEquals(1, resultado.size());

        verify(dashboardService)
                .obtenerTareasPendientes();
    }
}