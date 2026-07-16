package com.dlinteriorismo.sistema_interiorismo.controller;

import com.dlinteriorismo.sistema_interiorismo.dto.EmpleadoRequest;
import com.dlinteriorismo.sistema_interiorismo.model.Empleado;
import com.dlinteriorismo.sistema_interiorismo.service.EmpleadoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmpleadoControllerTest {

    private EmpleadoService empleadoService;
    private EmpleadoController empleadoController;

    @BeforeEach
    void setUp() {
        empleadoService = Mockito.mock(EmpleadoService.class);
        empleadoController = new EmpleadoController(empleadoService);
    }

    @Test
    void listarDebeRetornarLista() {

        Empleado empleado = new Empleado();
        empleado.setNombre("Juan Pérez");

        when(empleadoService.listar())
                .thenReturn(List.of(empleado));

        List<Empleado> resultado = empleadoController.listar();

        assertEquals(1, resultado.size());
        assertEquals("Juan Pérez", resultado.get(0).getNombre());

        verify(empleadoService).listar();
    }

    @Test
    void guardarDebeLlamarAlServicio() {

        EmpleadoRequest request = new EmpleadoRequest();
        request.setNombre("Carlos López");
        request.setDni("12345678");
        request.setTelefono("999999999");
        request.setCorreo("carlos@correo.com");
        request.setCargo("Diseñador");

        Empleado empleado = new Empleado();
        empleado.setNombre("Carlos López");

        when(empleadoService.guardar(request))
                .thenReturn(empleado);

        Empleado resultado = empleadoController.guardar(request);

        assertNotNull(resultado);
        assertEquals("Carlos López", resultado.getNombre());

        verify(empleadoService).guardar(request);
    }

    @Test
    void eliminarDebeLlamarAlServicio() {

        doNothing().when(empleadoService).eliminar(1);

        empleadoController.eliminar(1);

        verify(empleadoService).eliminar(1);
    }
}