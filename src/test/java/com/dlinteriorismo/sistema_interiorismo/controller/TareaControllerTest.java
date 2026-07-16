package com.dlinteriorismo.sistema_interiorismo.controller;

import com.dlinteriorismo.sistema_interiorismo.dto.TareaRequest;
import com.dlinteriorismo.sistema_interiorismo.model.Tarea;
import com.dlinteriorismo.sistema_interiorismo.service.TareaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TareaControllerTest {

    private TareaService tareaService;
    private TareaController tareaController;

    @BeforeEach
    void setUp() {
        tareaService = Mockito.mock(TareaService.class);
        tareaController = new TareaController(tareaService);
    }

    @Test
    void listarDebeRetornarLista() {

        Tarea tarea = new Tarea();

        when(tareaService.listar())
                .thenReturn(List.of(tarea));

        List<Tarea> resultado = tareaController.listar();

        assertEquals(1, resultado.size());

        verify(tareaService).listar();
    }

    @Test
    void guardarDebeLlamarAlServicio() {

        TareaRequest request = new TareaRequest();
        request.setIdProyecto(1);
        request.setIdEmpleado(1);
        request.setDescripcion("Instalación de muebles");
        request.setFechaLimite(LocalDate.now().plusDays(7));
        request.setEstado("Pendiente");

        Tarea tarea = new Tarea();

        when(tareaService.guardar(request))
                .thenReturn(tarea);

        Tarea resultado = tareaController.guardar(request);

        assertNotNull(resultado);

        verify(tareaService).guardar(request);
    }

    @Test
    void eliminarDebeLlamarAlServicio() {

        doNothing().when(tareaService).eliminar(1);

        tareaController.eliminar(1);

        verify(tareaService).eliminar(1);
    }
}