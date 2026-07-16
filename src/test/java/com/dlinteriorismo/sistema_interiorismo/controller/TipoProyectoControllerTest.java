package com.dlinteriorismo.sistema_interiorismo.controller;

import com.dlinteriorismo.sistema_interiorismo.model.TipoProyecto;
import com.dlinteriorismo.sistema_interiorismo.repository.TipoProyectoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TipoProyectoControllerTest {

    private TipoProyectoRepository tipoProyectoRepository;
    private TipoProyectoController tipoProyectoController;

    @BeforeEach
    void setUp() {
        tipoProyectoRepository = Mockito.mock(TipoProyectoRepository.class);
        tipoProyectoController = new TipoProyectoController(tipoProyectoRepository);
    }

    @Test
    void listarDebeRetornarLista() {

        TipoProyecto tipo = new TipoProyecto();

        when(tipoProyectoRepository.findAll())
                .thenReturn(List.of(tipo));

        List<TipoProyecto> resultado = tipoProyectoController.listar();

        assertEquals(1, resultado.size());

        verify(tipoProyectoRepository).findAll();
    }
}