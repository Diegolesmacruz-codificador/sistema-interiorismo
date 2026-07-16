package com.dlinteriorismo.sistema_interiorismo.controller;

import com.dlinteriorismo.sistema_interiorismo.dto.ProyectoRequest;
import com.dlinteriorismo.sistema_interiorismo.model.Proyecto;
import com.dlinteriorismo.sistema_interiorismo.service.ProyectoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProyectoController.class)
class ProyectoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProyectoService proyectoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listar() throws Exception {

        when(proyectoService.listar())
                .thenReturn(List.of(new Proyecto()));

        mockMvc.perform(get("/api/proyectos"))
                .andExpect(status().isOk());

        verify(proyectoService).listar();
    }

    @Test
    void guardar() throws Exception {

        ProyectoRequest request = new ProyectoRequest();

        request.setIdCliente(1);
        request.setIdTipoProyecto(1);
        request.setNombreProyecto("Closet Principal");
        request.setFechaInicio(LocalDate.now());
        request.setFechaFin(LocalDate.now().plusDays(30));
        request.setEstado("Pendiente");
        request.setDescripcion("Proyecto de prueba");

        when(proyectoService.guardar(any()))
                .thenReturn(new Proyecto());

        mockMvc.perform(post("/api/proyectos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(proyectoService).guardar(any());
    }

    @Test
    void eliminar() throws Exception {

        mockMvc.perform(delete("/api/proyectos/1"))
                .andExpect(status().isOk());

        verify(proyectoService).eliminar(1);
    }
}